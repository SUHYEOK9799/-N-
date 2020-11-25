package com.googlecode.leptonica.android;

import android.graphics.Bitmap.CompressFormat;
import androidx.annotation.IntRange;
import java.io.ByteArrayOutputStream;

public class JpegIO {
    public static final boolean DEFAULT_PROGRESSIVE = false;
    public static final int DEFAULT_QUALITY = 85;

    private static native byte[] nativeCompressToJpeg(long j, int i, boolean z);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static byte[] compressToJpeg(Pix pix) {
        return compressToJpeg(pix, 85, false);
    }

    public static byte[] compressToJpeg(Pix pix, @IntRange(from = 0, to = 100) int i, boolean z) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (i < 0 || i > true) {
            throw new IllegalArgumentException("Quality must be between 0 and 100 (inclusive)");
        } else {
            z = new ByteArrayOutputStream();
            pix = WriteFile.writeBitmap(pix);
            pix.compress(CompressFormat.JPEG, i, z);
            pix.recycle();
            pix = z.toByteArray();
            try {
                z.close();
            } catch (int i2) {
                i2.printStackTrace();
            }
            return pix;
        }
    }
}
