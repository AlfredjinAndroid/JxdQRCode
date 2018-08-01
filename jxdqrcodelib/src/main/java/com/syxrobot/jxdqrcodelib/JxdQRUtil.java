package com.syxrobot.jxdqrcodelib;

import android.graphics.Color;

/**
 * @author:JinXuDong
 * @date:2018/8/1
 */
public class JxdQRUtil {
    private boolean showPoint = false;
    private boolean isBeep = false;
    private boolean isScanLineTwinkle = false;
    private float qrRectWidth = 10f;
    private float scanWidth = 2f;
    private float qrLineWidth = 100f;
    private int scanSpeed = 10;
    private int scanAlpha = 255;
    public static final int QR_REQUEST_CODE = 0x1;
    public static final int QR_RESULT_CODE = 0x2;
    public static final String QR_SCAN_RESULT = "result";
    private int scanLineColor = Color.parseColor("#ffcc0000");
    private int resultPointColor = Color.parseColor("#c0ffbd21");
    private int qrRectColor = Color.parseColor("#c099cc00");

    private JxdQRUtil() {
    }

    private static class Holder {
        private static JxdQRUtil instance = new JxdQRUtil();
    }

    public static JxdQRUtil getInstance() {
        return Holder.instance;
    }

    public void setShowPoint(boolean isShowPoint) {
        showPoint = isShowPoint;
    }

    public boolean isShowPoint() {
        return showPoint;
    }

    public void setBeep(boolean isBeep) {
        this.isBeep = isBeep;
    }

    public boolean isBeep() {
        return isBeep;
    }

    public boolean isScanLineTwinkle() {
        return isScanLineTwinkle;
    }

    public void setScanLineTwinkle(boolean scanLineTwinkle) {
        isScanLineTwinkle = scanLineTwinkle;
    }

    public float getQrStrokeWidth() {
        return qrRectWidth;
    }

    public void setQrStrokeWidth(float qrRectWidth) {
        this.qrRectWidth = qrRectWidth;
    }

    public float getScanLineWidth() {
        return scanWidth;
    }

    public void setScanLineWidth(float scanWidth) {
        this.scanWidth = scanWidth;
    }

    public float getQrLineWidth() {
        return qrLineWidth;
    }

    public void setQrLineWidth(float qrLineWidth) {
        this.qrLineWidth = qrLineWidth;
    }

    public int getScanSpeed() {
        return scanSpeed;
    }

    public void setScanSpeed(int scanSpeed) {
        this.scanSpeed = scanSpeed;
    }

    public int getScanAlpha() {
        return scanAlpha;
    }

    public void setScanAlpha(int scanAlpha) {
        scanAlpha = scanAlpha > 255 ? 255 : scanAlpha < 0 ? 0 : scanAlpha;
        this.scanAlpha = scanAlpha;
    }

    public int getScanLineColor() {
        return scanLineColor;
    }

    public void setScanLineColor(int scanLineColor) {
        this.scanLineColor = scanLineColor;
    }

    public int getResultPointColor() {
        return resultPointColor;
    }

    public void setResultPointColor(int resultPointColor) {
        this.resultPointColor = resultPointColor;
    }

    public int getQrRectColor() {
        return qrRectColor;
    }

    public void setQrRectColor(int qrRectColor) {
        this.qrRectColor = qrRectColor;
    }
}
