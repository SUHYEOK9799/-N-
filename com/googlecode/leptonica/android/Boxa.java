package com.googlecode.leptonica.android;

import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.Size;

public class Boxa {
    private static final String TAG = Boxa.class.getSimpleName();
    private final long mNativeBoxa;
    private boolean mRecycled = false;

    private static native void nativeDestroy(long j);

    private static native int nativeGetCount(long j);

    private static native boolean nativeGetGeometry(long j, int i, int[] iArr);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public Boxa(long j) {
        this.mNativeBoxa = j;
        this.mRecycled = false;
    }

    public long getNativeBoxa() {
        if (!this.mRecycled) {
            return this.mNativeBoxa;
        }
        throw new IllegalStateException();
    }

    public int getCount() {
        if (!this.mRecycled) {
            return nativeGetCount(this.mNativeBoxa);
        }
        throw new IllegalStateException();
    }

    public Rect getRect(int i) {
        i = getGeometry(i);
        int i2 = i[0];
        int i3 = i[1];
        return new Rect(i2, i3, i[2] + i2, i[3] + i3);
    }

    public int[] getGeometry(int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        int[] iArr = new int[4];
        return getGeometry(i, iArr) != 0 ? iArr : 0;
    }

    public boolean getGeometry(int i, @Size(min = 4) int[] iArr) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        } else if (iArr.length >= 4) {
            return nativeGetGeometry(this.mNativeBoxa, i, iArr);
        } else {
            throw new IllegalArgumentException("Geometry array must be at least 4 elements long");
        }
    }

    public synchronized void recycle() {
        if (!this.mRecycled) {
            nativeDestroy(this.mNativeBoxa);
            this.mRecycled = true;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.mRecycled) {
                Log.w(TAG, "Boxa was not terminated using recycle()");
                recycle();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }
}
