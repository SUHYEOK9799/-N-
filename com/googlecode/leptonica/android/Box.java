package com.googlecode.leptonica.android;

import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.Size;

public class Box {
    public static final int INDEX_H = 3;
    public static final int INDEX_W = 2;
    public static final int INDEX_X = 0;
    public static final int INDEX_Y = 1;
    private static final String TAG = Box.class.getSimpleName();
    private final long mNativeBox;
    private boolean mRecycled = false;

    private static native long nativeCreate(int i, int i2, int i3, int i4);

    private static native void nativeDestroy(long j);

    private static native boolean nativeGetGeometry(long j, int[] iArr);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetWidth(long j);

    private static native int nativeGetX(long j);

    private static native int nativeGetY(long j);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    Box(long j) {
        this.mNativeBox = j;
        this.mRecycled = false;
    }

    public Box(int i, int i2, int i3, int i4) {
        if (i < 0 || i2 < 0 || i3 < 0 || i4 < 0) {
            throw new IllegalArgumentException("All box dimensions must be non-negative");
        }
        i = nativeCreate(i, i2, i3, i4);
        if (i != 0) {
            this.mNativeBox = i;
            this.mRecycled = false;
            return;
        }
        throw new OutOfMemoryError();
    }

    public long getNativeBox() {
        if (!this.mRecycled) {
            return this.mNativeBox;
        }
        throw new IllegalStateException();
    }

    public int getX() {
        if (!this.mRecycled) {
            return nativeGetX(this.mNativeBox);
        }
        throw new IllegalStateException();
    }

    public int getY() {
        if (!this.mRecycled) {
            return nativeGetY(this.mNativeBox);
        }
        throw new IllegalStateException();
    }

    public int getWidth() {
        if (!this.mRecycled) {
            return nativeGetWidth(this.mNativeBox);
        }
        throw new IllegalStateException();
    }

    public int getHeight() {
        if (!this.mRecycled) {
            return nativeGetHeight(this.mNativeBox);
        }
        throw new IllegalStateException();
    }

    public Rect getRect() {
        int[] geometry = getGeometry();
        int i = geometry[0];
        int i2 = geometry[1];
        return new Rect(i, i2, geometry[2] + i, geometry[3] + i2);
    }

    public int[] getGeometry() {
        int[] iArr = new int[4];
        return getGeometry(iArr) ? iArr : null;
    }

    public boolean getGeometry(@Size(min = 4) int[] iArr) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        } else if (iArr.length >= 4) {
            return nativeGetGeometry(this.mNativeBox, iArr);
        } else {
            throw new IllegalArgumentException("Geometry array must be at least 4 elements long");
        }
    }

    public void recycle() {
        if (!this.mRecycled) {
            nativeDestroy(this.mNativeBox);
            this.mRecycled = true;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.mRecycled) {
                Log.w(TAG, "Box was not terminated using recycle()");
                recycle();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }
}
