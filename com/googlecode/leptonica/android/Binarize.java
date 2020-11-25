package com.googlecode.leptonica.android;

import androidx.annotation.FloatRange;

public class Binarize {
    public static final float OTSU_SCORE_FRACTION = 0.1f;
    public static final int OTSU_SIZE_X = 32;
    public static final int OTSU_SIZE_Y = 32;
    public static final int OTSU_SMOOTH_X = 2;
    public static final int OTSU_SMOOTH_Y = 2;
    public static final int SAUVOLA_DEFAULT_NUM_TILES_X = 1;
    public static final int SAUVOLA_DEFAULT_NUM_TILES_Y = 1;
    public static final float SAUVOLA_DEFAULT_REDUCTION_FACTOR = 0.35f;
    public static final int SAUVOLA_DEFAULT_WINDOW_HALFWIDTH = 8;

    private static native long nativeOtsuAdaptiveThreshold(long j, int i, int i2, int i3, int i4, float f);

    private static native long nativeSauvolaBinarizeTiled(long j, int i, float f, int i2, int i3);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix otsuAdaptiveThreshold(Pix pix) {
        return otsuAdaptiveThreshold(pix, 32, 32, 2, 2, OTSU_SCORE_FRACTION);
    }

    public static Pix otsuAdaptiveThreshold(Pix pix, int i, int i2, int i3, int i4, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (pix.getDepth() == 8) {
            pix = nativeOtsuAdaptiveThreshold(pix.getNativePix(), i, i2, i3, i4, f);
            if (pix != 0) {
                return new Pix(pix);
            }
            throw new RuntimeException("Failed to perform Otsu adaptive threshold on image");
        } else {
            throw new IllegalArgumentException("Source pix depth must be 8bpp");
        }
    }

    public static Pix sauvolaBinarizeTiled(Pix pix) {
        return sauvolaBinarizeTiled(pix, 8, SAUVOLA_DEFAULT_REDUCTION_FACTOR, 1, 1);
    }

    public static Pix sauvolaBinarizeTiled(Pix pix, int i, @FloatRange(from = 0.0d) float f, int i2, int i3) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (pix.getDepth() == 8) {
            pix = nativeSauvolaBinarizeTiled(pix.getNativePix(), i, f, i2, i3);
            if (pix != 0.0f) {
                return new Pix(pix);
            }
            throw new RuntimeException("Failed to perform Sauvola binarization on image");
        } else {
            throw new IllegalArgumentException("Source pix depth must be 8bpp");
        }
    }
}
