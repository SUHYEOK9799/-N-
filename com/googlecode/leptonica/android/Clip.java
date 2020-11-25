package com.googlecode.leptonica.android;

public class Clip {
    private static native long nativeClipRectangle(long j, long j2);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix clipRectangle(Pix pix, Box box) {
        pix = nativeClipRectangle(pix.getNativePix(), box.getNativeBox());
        return pix != 0 ? new Pix(pix) : null;
    }
}
