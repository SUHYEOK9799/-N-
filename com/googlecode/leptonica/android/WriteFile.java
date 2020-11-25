package com.googlecode.leptonica.android;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import java.io.File;

public class WriteFile {
    private static native boolean nativeWriteBitmap(long j, Bitmap bitmap);

    private static native int nativeWriteBytes8(long j, byte[] bArr);

    private static native boolean nativeWriteImpliedFormat(long j, String str);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static byte[] writeBytes8(Pix pix) {
        if (pix != null) {
            byte[] bArr = new byte[(pix.getWidth() * pix.getHeight())];
            if (pix.getDepth() != 8) {
                pix = Convert.convertTo8(pix);
                writeBytes8(pix, bArr);
                pix.recycle();
            } else {
                writeBytes8(pix, bArr);
            }
            return bArr;
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }

    public static int writeBytes8(Pix pix, byte[] bArr) {
        if (pix != null) {
            if (bArr.length >= pix.getWidth() * pix.getHeight()) {
                return nativeWriteBytes8(pix.getNativePix(), bArr);
            }
            throw new IllegalArgumentException("Data array must be large enough to hold image bytes");
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }

    public static boolean writeImpliedFormat(Pix pix, File file) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (file != null) {
            return nativeWriteImpliedFormat(pix.getNativePix(), file.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("File must be non-null");
        }
    }

    public static Bitmap writeBitmap(Pix pix) {
        if (pix != null) {
            int[] dimensions = pix.getDimensions();
            if (dimensions != null) {
                Bitmap createBitmap = Bitmap.createBitmap(dimensions[0], dimensions[1], Config.ARGB_8888);
                if (nativeWriteBitmap(pix.getNativePix(), createBitmap) != null) {
                    return createBitmap;
                }
                createBitmap.recycle();
            }
            return null;
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }
}
