package com.googlecode.tesseract.android;

import android.graphics.Rect;

public class PageIterator {
    private final long mNativePageIterator;

    private static native void nativeBegin(long j);

    private static native int[] nativeBoundingBox(long j, int i);

    private static native boolean nativeNext(long j, int i);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
        System.loadLibrary("tess");
    }

    PageIterator(long j) {
        this.mNativePageIterator = j;
    }

    public void begin() {
        nativeBegin(this.mNativePageIterator);
    }

    public boolean next(int i) {
        return nativeNext(this.mNativePageIterator, i);
    }

    public int[] getBoundingBox(int i) {
        return nativeBoundingBox(this.mNativePageIterator, i);
    }

    public Rect getBoundingRect(int i) {
        i = getBoundingBox(i);
        return new Rect(i[0], i[1], i[2], i[3]);
    }
}
