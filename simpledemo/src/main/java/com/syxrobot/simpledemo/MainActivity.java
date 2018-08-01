package com.syxrobot.simpledemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.syxrobot.jxdqrcodelib.CaptureActivity;
import com.syxrobot.jxdqrcodelib.JxdQRUtil;

public class MainActivity extends AppCompatActivity {

    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvShow = findViewById(R.id.mTvShow);
        //是否蜂鸣
        JxdQRUtil.getInstance().setBeep(true);
        //是否显示扫描点
        JxdQRUtil.getInstance().setShowPoint(false);
        //扫描线速度
        JxdQRUtil.getInstance().setScanSpeed(8);
        //设置扫描边框线长度
        JxdQRUtil.getInstance().setQrLineWidth(80f);
        //扫描线颜色
        JxdQRUtil.getInstance().setScanLineColor(Color.parseColor("#4CE15D"));
        //扫描边框线宽度
        JxdQRUtil.getInstance().setQrStrokeWidth(8);
        //设置扫描边框颜色
//        JxdQRUtil.getInstance().setQrRectColor();
        //扫描点颜色
//        JxdQRUtil.getInstance().setResultPointColor();
        //扫描线是否闪烁
//        JxdQRUtil.getInstance().setScanLineTwinkle();
        //扫描线透明度
//        JxdQRUtil.getInstance().setScanAlpha();

    }

    public void scan(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, JxdQRUtil.QR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == JxdQRUtil.QR_REQUEST_CODE) {
            if (data != null) {
                String extra = data.getStringExtra(JxdQRUtil.QR_SCAN_RESULT);
                mTvShow.setText(extra);
            }
        }
    }
}
