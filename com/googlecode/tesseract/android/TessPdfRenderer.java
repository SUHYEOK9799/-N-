package com.googlecode.tesseract.android;

public class TessPdfRenderer {
    private final long mNativePdfRenderer;
    private boolean mRecycled = null;

    private static native long nativeCreate(long j, String str);

    private static native void nativeRecycle(long j);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
        System.loadLibrary("tess");
    }

    public TessPdfRenderer(TessBaseAPI tessBaseAPI, String str) {
        this.mNativePdfRenderer = nativeCreate(tessBaseAPI.getNativeData(), str);
    }

    public long getNativePdfRenderer() {
        if (!this.mRecycled) {
            return this.mNativePdfRenderer;
        }
        throw new IllegalStateException();
    }

    public void recycle() {
        nativeRecycle(this.mNativePdfRenderer);
        this.mRecycled = true;
    }
}
