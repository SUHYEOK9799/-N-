package com.googlecode.leptonica.android;

import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.annotation.Size;

public class Pix {
    public static final int IFF_BMP = 1;
    public static final int IFF_DEFAULT = 18;
    public static final int IFF_GIF = 13;
    public static final int IFF_JFIF_JPEG = 2;
    public static final int IFF_JP2 = 14;
    public static final int IFF_LPDF = 16;
    public static final int IFF_PNG = 3;
    public static final int IFF_PNM = 11;
    public static final int IFF_PS = 12;
    public static final int IFF_SPIX = 19;
    public static final int IFF_TIFF = 4;
    public static final int IFF_TIFF_G3 = 7;
    public static final int IFF_TIFF_G4 = 8;
    public static final int IFF_TIFF_JPEG = 17;
    public static final int IFF_TIFF_LZW = 9;
    public static final int IFF_TIFF_PACKBITS = 5;
    public static final int IFF_TIFF_RLE = 6;
    public static final int IFF_TIFF_ZIP = 10;
    public static final int IFF_UNKNOWN = 0;
    public static final int IFF_WEBP = 15;
    public static final int INDEX_D = 2;
    public static final int INDEX_H = 1;
    public static final int INDEX_W = 0;
    private final long mNativePix;
    private boolean mRecycled;

    private static native long nativeClone(long j);

    private static native long nativeCopy(long j);

    private static native long nativeCreateFromData(byte[] bArr, int i, int i2, int i3);

    private static native long nativeCreatePix(int i, int i2, int i3);

    private static native void nativeDestroy(long j);

    private static native byte[] nativeGetData(long j);

    private static native int nativeGetDepth(long j);

    private static native boolean nativeGetDimensions(long j, int[] iArr);

    private static native int nativeGetHeight(long j);

    private static native int nativeGetInputFormat(long j);

    private static native int nativeGetPixel(long j, int i, int i2);

    private static native int nativeGetRefCount(long j);

    private static native int nativeGetSpp(long j);

    private static native int nativeGetWidth(long j);

    private static native boolean nativeInvert(long j);

    private static native void nativeSetPixel(long j, int i, int i2, int i3);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public Pix(long j) {
        this.mNativePix = j;
        this.mRecycled = 0;
    }

    public Pix(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException("Pix width and height must be > 0");
        }
        if (!(i3 == 1 || i3 == 2 || i3 == 4 || i3 == 8 || i3 == 16 || i3 == 24)) {
            if (i3 != 32) {
                throw new IllegalArgumentException("Depth must be one of 1, 2, 4, 8, 16, or 32");
            }
        }
        this.mNativePix = nativeCreatePix(i, i2, i3);
        this.mRecycled = false;
    }

    public long getNativePix() {
        if (!this.mRecycled) {
            return this.mNativePix;
        }
        throw new IllegalStateException();
    }

    public byte[] getData() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        byte[] nativeGetData = nativeGetData(this.mNativePix);
        if (nativeGetData != null) {
            return nativeGetData;
        }
        throw new RuntimeException("native getData failed");
    }

    public int[] getDimensions() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        int[] iArr = new int[3];
        return getDimensions(iArr) ? iArr : null;
    }

    public boolean getDimensions(@Size(min = 3) int[] iArr) {
        if (!this.mRecycled) {
            return nativeGetDimensions(this.mNativePix, iArr);
        }
        throw new IllegalStateException();
    }

    public Pix clone() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        long nativeClone = nativeClone(this.mNativePix);
        if (nativeClone != 0) {
            return new Pix(nativeClone);
        }
        throw new OutOfMemoryError();
    }

    public Pix copy() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        long nativeCopy = nativeCopy(this.mNativePix);
        if (nativeCopy != 0) {
            return new Pix(nativeCopy);
        }
        throw new OutOfMemoryError();
    }

    public boolean invert() {
        if (!this.mRecycled) {
            return nativeInvert(this.mNativePix);
        }
        throw new IllegalStateException();
    }

    public void recycle() {
        if (!this.mRecycled) {
            nativeDestroy(this.mNativePix);
            this.mRecycled = true;
        }
    }

    public static Pix createFromPix(byte[] bArr, int i, int i2, int i3) {
        bArr = nativeCreateFromData(bArr, i, i2, i3);
        if (bArr != 0) {
            return new Pix(bArr);
        }
        throw new OutOfMemoryError();
    }

    public Rect getRect() {
        return new Rect(0, 0, getWidth(), getHeight());
    }

    public int getWidth() {
        if (!this.mRecycled) {
            return nativeGetWidth(this.mNativePix);
        }
        throw new IllegalStateException();
    }

    public int getHeight() {
        if (!this.mRecycled) {
            return nativeGetHeight(this.mNativePix);
        }
        throw new IllegalStateException();
    }

    public int getDepth() {
        if (!this.mRecycled) {
            return nativeGetDepth(this.mNativePix);
        }
        throw new IllegalStateException();
    }

    public int getSpp() {
        if (!this.mRecycled) {
            return nativeGetSpp(this.mNativePix);
        }
        throw new IllegalStateException();
    }

    public int getImageFormat() {
        if (!this.mRecycled) {
            return nativeGetInputFormat(this.mNativePix);
        }
        throw new IllegalStateException();
    }

    public int getRefCount() {
        return nativeGetRefCount(this.mNativePix);
    }

    public int getPixel(int i, int i2) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        } else if (i < 0 || i >= getWidth()) {
            throw new IllegalArgumentException("Supplied x coordinate exceeds image bounds");
        } else if (i2 >= 0 && i2 < getHeight()) {
            return nativeGetPixel(this.mNativePix, i, i2);
        } else {
            throw new IllegalArgumentException("Supplied y coordinate exceeds image bounds");
        }
    }

    public void setPixel(int i, int i2, @ColorInt int i3) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        } else if (i < 0 || i >= getWidth()) {
            throw new IllegalArgumentException("Supplied x coordinate exceeds image bounds");
        } else if (i2 < 0 || i2 >= getHeight()) {
            throw new IllegalArgumentException("Supplied y coordinate exceeds image bounds");
        } else {
            nativeSetPixel(this.mNativePix, i, i2, i3);
        }
    }
}
