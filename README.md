基于Google ZXing的二维码扫描 
为了提升扫描速度和性能  
在源码基础上进行了一定的修改和封装  
方便以后自己使用


### 引用
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
dependencies {
		implementation 'com.github.AlfredjinAndroid:JxdQRCode:v1.0'
	}
```
### 使用

跳转识别
```
 Intent intent = new Intent(this, CaptureActivity.class);
 startActivityForResult(intent, JxdQRUtil.QR_REQUEST_CODE);
```

扫描结果处理
```
 if (requestCode == JxdQRUtil.QR_REQUEST_CODE) {
            if (data != null) {
                String extra = data.getStringExtra(JxdQRUtil.QR_SCAN_RESULT);
                //mTvShow.setText(extra);
                // TODO 处理自己的需求
            }
        }
```

个性设置
```
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

```



### License

	Copyright (C) 2018 ZXing authors, AlfredJin

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	    http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
