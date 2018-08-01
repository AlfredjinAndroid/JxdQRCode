/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.syxrobot.jxdqrcodelib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.syxrobot.jxdqrcodelib.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 80L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;

    private CameraManager cameraManager;
    private final Paint paint;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int laserColor;
    private final int resultPointColor;
    private int scannerAlpha;
    private int rectPoint = 0;
    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        laserColor = JxdQRUtil.getInstance().getScanLineColor();
        resultPointColor = JxdQRUtil.getInstance().getResultPointColor();
        scannerAlpha = 0;
        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = null;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paint.setColor(JxdQRUtil.getInstance().getQrRectColor());
        paint.setStrokeWidth(JxdQRUtil.getInstance().getQrStrokeWidth());
        float lineWidth = JxdQRUtil.getInstance().getQrLineWidth();
        canvas.drawLine(frame.left - paint.getStrokeWidth() / 2, frame.top, frame.left + lineWidth, frame.top, paint);
        canvas.drawLine(frame.left, frame.top, frame.left, frame.top + lineWidth, paint);
        canvas.drawLine(frame.right + paint.getStrokeWidth() / 2, frame.top, frame.right - lineWidth, frame.top, paint);
        canvas.drawLine(frame.right, frame.top, frame.right, frame.top + lineWidth, paint);
        canvas.drawLine(frame.left - paint.getStrokeWidth() / 2, frame.bottom, frame.left + lineWidth, frame.bottom, paint);
        canvas.drawLine(frame.left, frame.bottom, frame.left, frame.bottom - lineWidth, paint);
        canvas.drawLine(frame.right + paint.getStrokeWidth() / 2, frame.bottom, frame.right - lineWidth, frame.bottom, paint);
        canvas.drawLine(frame.right, frame.bottom, frame.right, frame.bottom - lineWidth, paint);


        if (resultBitmap != null) {
//            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

            // Draw a red "laser scanner" line through the middle to show decoding is active
            paint.setColor(laserColor);
            if (JxdQRUtil.getInstance().isScanLineTwinkle()) {
                paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
                scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            } else {
                paint.setAlpha(JxdQRUtil.getInstance().getScanAlpha());
            }
            int offset = (int) (JxdQRUtil.getInstance().getQrStrokeWidth() / 2);
            rectPoint = rectPoint < frame.top + offset || rectPoint > frame.bottom - offset ? frame.top + offset : rectPoint + JxdQRUtil.getInstance().getScanSpeed();
            rectPoint = rectPoint > frame.bottom - offset - JxdQRUtil.getInstance().getScanLineWidth() ? (int) (frame.bottom - offset - JxdQRUtil.getInstance().getScanLineWidth()) : rectPoint;
            Log.e("ViewfinderView", "rectPoint == " + rectPoint);
            canvas.drawRect(frame.left + offset, rectPoint, frame.right - offset, rectPoint + JxdQRUtil.getInstance().getScanLineWidth(), paint);
            rectPoint = rectPoint >= frame.bottom - offset - JxdQRUtil.getInstance().getScanLineWidth() ? 0 : rectPoint;
            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                if (JxdQRUtil.getInstance().isShowPoint()) {
                    possibleResultPoints = new ArrayList<>(5);
                    lastPossibleResultPoints = currentPossible;
                    paint.setAlpha(CURRENT_POINT_OPACITY);
                    paint.setColor(resultPointColor);
                    synchronized (currentPossible) {
                        for (ResultPoint point : currentPossible) {
                            canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                                    frameTop + (int) (point.getY() * scaleY),
                                    POINT_SIZE, paint);
                        }
                    }
                }
            }
            if (currentLast != null && JxdQRUtil.getInstance().isShowPoint()) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                synchronized (currentLast) {
                    float radius = POINT_SIZE / 2.0f;
                    for (ResultPoint point : currentLast) {
                        canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                                frameTop + (int) (point.getY() * scaleY),
                                radius, paint);
                    }
                }
            }
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }

}
