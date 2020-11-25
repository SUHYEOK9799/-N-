package com.googlecode.leptonica.android;

public class Enhance {
    public static final float DEFAULT_UNSHARP_FRACTION = 0.3f;
    public static final int DEFAULT_UNSHARP_HALFWIDTH = 1;

    private static native long nativeUnsharpMasking(long j, int i, float f);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix unsharpMasking(Pix pix) {
        return unsharpMasking(pix, 1, DEFAULT_UNSHARP_FRACTION);
    }

    public static Pix unsharpMasking(Pix pix, int i, float f) {
        if (pix != null) {
            pix = nativeUnsharpMasking(pix.getNativePix(), i, f);
            if (pix != 0) {
                return new Pix(pix);
            }
            throw new OutOfMemoryError();
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }
}
