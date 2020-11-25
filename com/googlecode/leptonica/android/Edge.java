package com.googlecode.leptonica.android;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Edge {
    public static final int L_ALL_EDGES = 2;
    public static final int L_HORIZONTAL_EDGES = 0;
    public static final int L_VERTICAL_EDGES = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EdgeOrientationFlag {
    }

    private static native long nativePixSobelEdgeFilter(long j, int i);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix pixSobelEdgeFilter(Pix pix, int i) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (pix.getDepth() != 8) {
            throw new IllegalArgumentException("Source pix depth must be 8bpp");
        } else if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Invalid orientation flag");
        } else {
            pix = nativePixSobelEdgeFilter(pix.getNativePix(), i);
            if (pix != 0) {
                return new Pix(pix);
            }
            throw new RuntimeException("Failed to perform Sobel edge filter on image");
        }
    }
}
