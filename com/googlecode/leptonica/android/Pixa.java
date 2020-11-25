package com.googlecode.leptonica.android;

import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.Size;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Pixa implements Iterable<Pix> {
    private static final String TAG = Pixa.class.getSimpleName();
    final int mHeight;
    private final long mNativePixa;
    private boolean mRecycled = 0;
    final int mWidth;

    private class PixIterator implements Iterator<Pix> {
        private int mIndex;

        private PixIterator() {
            this.mIndex = null;
        }

        public boolean hasNext() {
            int size = Pixa.this.size();
            return size > 0 && this.mIndex < size;
        }

        public Pix next() {
            Pixa pixa = Pixa.this;
            int i = this.mIndex;
            this.mIndex = i + 1;
            return pixa.getPix(i);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static native void nativeAdd(long j, long j2, long j3, int i);

    private static native void nativeAddBox(long j, long j2, int i);

    private static native void nativeAddPix(long j, long j2, int i);

    private static native long nativeCopy(long j);

    private static native long nativeCreate(int i);

    private static native void nativeDestroy(long j);

    private static native long nativeGetBox(long j, int i);

    private static native boolean nativeGetBoxGeometry(long j, int i, int[] iArr);

    private static native int nativeGetCount(long j);

    private static native long nativeGetPix(long j, int i);

    private static native boolean nativeJoin(long j, long j2);

    private static native void nativeMergeAndReplacePix(long j, int i, int i2);

    private static native void nativeReplacePix(long j, int i, long j2, long j3);

    private static native long nativeSort(long j, int i, int i2);

    private static native boolean nativeWriteToFileRandomCmap(long j, String str, int i, int i2);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pixa createPixa(int i) {
        return createPixa(i, 0, 0);
    }

    public static Pixa createPixa(int i, int i2, int i3) {
        long nativeCreate = nativeCreate(i);
        if (nativeCreate != 0) {
            return new Pixa(nativeCreate, i2, i3);
        }
        throw new OutOfMemoryError();
    }

    public Pixa(long j, int i, int i2) {
        this.mNativePixa = j;
        this.mWidth = i;
        this.mHeight = i2;
    }

    public long getNativePixa() {
        if (!this.mRecycled) {
            return this.mNativePixa;
        }
        throw new IllegalStateException();
    }

    public Pixa copy() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        long nativeCopy = nativeCopy(this.mNativePixa);
        if (nativeCopy != 0) {
            return new Pixa(nativeCopy, this.mWidth, this.mHeight);
        }
        throw new OutOfMemoryError();
    }

    public Pixa sort(int i, int i2) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        i = nativeSort(this.mNativePixa, i, i2);
        if (i != 0) {
            return new Pixa(i, this.mWidth, this.mHeight);
        }
        throw new OutOfMemoryError();
    }

    public int size() {
        if (!this.mRecycled) {
            return nativeGetCount(this.mNativePixa);
        }
        throw new IllegalStateException();
    }

    public synchronized void recycle() {
        if (!this.mRecycled) {
            nativeDestroy(this.mNativePixa);
            this.mRecycled = true;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (!this.mRecycled) {
                Log.w(TAG, "Pixa was not terminated using recycle()");
                recycle();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public boolean join(Pixa pixa) {
        if (!this.mRecycled) {
            return nativeJoin(this.mNativePixa, pixa.mNativePixa);
        }
        throw new IllegalStateException();
    }

    public void addPix(Pix pix, int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeAddPix(this.mNativePixa, pix.getNativePix(), i);
    }

    public void addBox(Box box, int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeAddBox(this.mNativePixa, box.getNativeBox(), i);
    }

    public void add(Pix pix, Box box, int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeAdd(this.mNativePixa, pix.getNativePix(), box.getNativeBox(), i);
    }

    public Box getBox(int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        long nativeGetBox = nativeGetBox(this.mNativePixa, i);
        if (nativeGetBox == 0) {
            return 0;
        }
        return new Box(nativeGetBox);
    }

    public Pix getPix(int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        long nativeGetPix = nativeGetPix(this.mNativePixa, i);
        if (nativeGetPix == 0) {
            return 0;
        }
        return new Pix(nativeGetPix);
    }

    public int getWidth() {
        if (!this.mRecycled) {
            return this.mWidth;
        }
        throw new IllegalStateException();
    }

    public int getHeight() {
        if (!this.mRecycled) {
            return this.mHeight;
        }
        throw new IllegalStateException();
    }

    public Rect getRect() {
        if (!this.mRecycled) {
            return new Rect(0, 0, this.mWidth, this.mHeight);
        }
        throw new IllegalStateException();
    }

    public int[] getBoxGeometry(int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        int[] iArr = new int[4];
        return getBoxGeometry(i, iArr) != 0 ? iArr : 0;
    }

    public boolean getBoxGeometry(int i, @Size(min = 4) int[] iArr) {
        if (!this.mRecycled) {
            return nativeGetBoxGeometry(this.mNativePixa, i, iArr);
        }
        throw new IllegalStateException();
    }

    public Rect getBoxRect(int i) {
        i = getBoxGeometry(i);
        if (i == 0) {
            return 0;
        }
        int i2 = i[0];
        int i3 = i[1];
        return new Rect(i2, i3, i[2] + i2, i[3] + i3);
    }

    public ArrayList<Rect> getBoxRects() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        int nativeGetCount = nativeGetCount(this.mNativePixa);
        int[] iArr = new int[4];
        ArrayList<Rect> arrayList = new ArrayList(nativeGetCount);
        for (int i = 0; i < nativeGetCount; i++) {
            getBoxGeometry(i, iArr);
            int i2 = iArr[0];
            int i3 = iArr[1];
            arrayList.add(new Rect(i2, i3, iArr[2] + i2, iArr[3] + i3));
        }
        return arrayList;
    }

    public void replacePix(int i, Pix pix, Box box) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeReplacePix(this.mNativePixa, i, pix.getNativePix(), box.getNativeBox());
    }

    public void mergeAndReplacePix(int i, int i2) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeMergeAndReplacePix(this.mNativePixa, i, i2);
    }

    public boolean writeToFileRandomCmap(File file) {
        if (!this.mRecycled) {
            return nativeWriteToFileRandomCmap(this.mNativePixa, file.getAbsolutePath(), this.mWidth, this.mHeight);
        }
        throw new IllegalStateException();
    }

    public Iterator<Pix> iterator() {
        return new PixIterator();
    }
}
