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

import android.content.Intent;

/**
 * This class provides the constants to use when sending an Intent to Barcode Scanner.
 * These strings are effectively API and cannot be changed.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class Intents {
  private Intents() {
  }
  public static final class Scan {
    /**
     * By default, sending this will decode all barcodes that we understand. However it
     * may be useful to limit scanning to certain formats. Use
     * {@link Intent#putExtra(String, String)} with one of the values below.
     * <p>
     * Setting this is effectively shorthand for setting explicit formats with {@link #FORMATS}.
     * It is overridden by that setting.
     */
    public static final String MODE = "SCAN_MODE";

    /**
     * Decode only UPC and EAN barcodes. This is the right choice for shopping apps which get
     * prices, reviews, etc. for products.
     */
    public static final String PRODUCT_MODE = "PRODUCT_MODE";

    /**
     * Decode only 1D barcodes.
     */
    public static final String ONE_D_MODE = "ONE_D_MODE";

    /**
     * Decode only QR codes.
     */
    public static final String QR_CODE_MODE = "QR_CODE_MODE";

    /**
     * Decode only Data Matrix codes.
     */
    public static final String DATA_MATRIX_MODE = "DATA_MATRIX_MODE";

    /**
     * Decode only Aztec.
     */
    public static final String AZTEC_MODE = "AZTEC_MODE";

    /**
     * Decode only PDF417.
     */
    public static final String PDF417_MODE = "PDF417_MODE";

    /**
     * Comma-separated list of formats to scan for. The values must match the names of
     * {@link com.google.zxing.BarcodeFormat}s, e.g. {@link com.google.zxing.BarcodeFormat#EAN_13}.
     * Example: "EAN_13,EAN_8,QR_CODE". This overrides {@link #MODE}.
     */
    public static final String FORMATS = "SCAN_FORMATS";
  }
}
