package com.googlecode.leptonica.android;

public class GrayQuant {
    private static native long nativePixThresholdToBinary(long j, int i);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix pixThresholdToBinary(Pix pix, int i) {
        if (pix != null) {
            int depth = pix.getDepth();
            if (depth != 4) {
                if (depth != 8) {
                    throw new IllegalArgumentException("Source pix depth must be 4 or 8 bpp");
                }
            }
            if (depth == 4) {
                if (i > 16) {
                    throw new IllegalArgumentException("4 bpp thresh not in {0-16}");
                }
            }
            if (depth == 8) {
                if (i > 256) {
                    throw new IllegalArgumentException("8 bpp thresh not in {0-256}");
                }
            }
            pix = nativePixThresholdToBinary(pix.getNativePix(), i);
            if (pix != 0) {
                return new Pix(pix);
            }
            throw new RuntimeException("Failed to perform binarization");
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }
}
