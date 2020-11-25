package com.googlecode.leptonica.android;

public class Skew {
    public static final float SEARCH_MIN_DELTA = 0.01f;
    public static final int SEARCH_REDUCTION = 4;
    public static final float SWEEP_DELTA = 5.0f;
    public static final float SWEEP_RANGE = 30.0f;
    public static final int SWEEP_REDUCTION = 8;

    private static native float nativeFindSkew(long j, float f, float f2, int i, int i2, float f3);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static float findSkew(Pix pix) {
        return findSkew(pix, SWEEP_RANGE, SWEEP_DELTA, 8, 4, SEARCH_MIN_DELTA);
    }

    public static float findSkew(Pix pix, float f, float f2, int i, int i2, float f3) {
        if (pix != null) {
            return nativeFindSkew(pix.getNativePix(), f, f2, i, i2, f3);
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }
}
