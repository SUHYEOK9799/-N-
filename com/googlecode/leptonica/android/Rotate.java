package com.googlecode.leptonica.android;

import androidx.annotation.IntRange;

public class Rotate {
    public static final boolean ROTATE_QUALITY = true;

    private static native long nativeRotate(long j, float f, boolean z, boolean z2);

    private static native long nativeRotateOrth(long j, int i);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix rotate(Pix pix, float f) {
        return rotate(pix, f, false);
    }

    public static Pix rotate(Pix pix, float f, boolean z) {
        return rotate(pix, f, z, true);
    }

    public static Pix rotate(Pix pix, float f, boolean z, boolean z2) {
        if (pix != null) {
            pix = nativeRotate(pix.getNativePix(), f, z, z2);
            if (pix != false) {
                return new Pix(pix);
            }
            return null;
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }

    public static Pix rotateOrth(Pix pix, @IntRange(from = 0, to = 3) int i) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (i < 0 || i > 3) {
            throw new IllegalArgumentException("quads not in {0,1,2,3}");
        } else {
            pix = nativeRotateOrth(pix.getNativePix(), i);
            if (pix == 0) {
                return null;
            }
            return new Pix(pix);
        }
    }
}
