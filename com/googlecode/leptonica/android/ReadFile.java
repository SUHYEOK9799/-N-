package com.googlecode.leptonica.android;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import java.io.File;

public class ReadFile {
    private static final String LOG_TAG = ReadFile.class.getSimpleName();

    private static native long nativeReadBitmap(Bitmap bitmap);

    private static native long nativeReadBytes8(byte[] bArr, int i, int i2);

    private static native long nativeReadFile(String str);

    private static native long nativeReadMem(byte[] bArr, int i);

    private static native boolean nativeReplaceBytes8(long j, byte[] bArr, int i, int i2);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix readMem(byte[] bArr) {
        if (bArr == null) {
            Log.e(LOG_TAG, "Image data byte array must be non-null");
            return null;
        }
        Options options = new Options();
        options.inPreferredConfig = Config.ARGB_8888;
        bArr = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        Pix readBitmap = readBitmap(bArr);
        bArr.recycle();
        return readBitmap;
    }

    public static Pix readBytes8(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("Byte array must be non-null");
        } else if (i <= 0) {
            throw new IllegalArgumentException("Image width must be greater than 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("Image height must be greater than 0");
        } else if (bArr.length >= i * i2) {
            bArr = nativeReadBytes8(bArr, i, i2);
            if (bArr != 0) {
                return new Pix(bArr);
            }
            throw new RuntimeException("Failed to read pix from memory");
        } else {
            throw new IllegalArgumentException("Array length does not match dimensions");
        }
    }

    public static boolean replaceBytes8(Pix pix, byte[] bArr, int i, int i2) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (bArr == null) {
            throw new IllegalArgumentException("Byte array must be non-null");
        } else if (i <= 0) {
            throw new IllegalArgumentException("Image width must be greater than 0");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("Image height must be greater than 0");
        } else if (bArr.length < i * i2) {
            throw new IllegalArgumentException("Array length does not match dimensions");
        } else if (pix.getWidth() != i) {
            throw new IllegalArgumentException("Source pix width does not match image width");
        } else if (pix.getHeight() == i2) {
            return nativeReplaceBytes8(pix.getNativePix(), bArr, i, i2);
        } else {
            throw new IllegalArgumentException("Source pix height does not match image height");
        }
    }

    public static Pix readFile(File file) {
        if (file == null) {
            Log.e(LOG_TAG, "File must be non-null");
            return null;
        } else if (!file.exists()) {
            Log.e(LOG_TAG, "File does not exist");
            return null;
        } else if (file.canRead()) {
            long nativeReadFile = nativeReadFile(file.getAbsolutePath());
            if (nativeReadFile != 0) {
                return new Pix(nativeReadFile);
            }
            Options options = new Options();
            options.inPreferredConfig = Config.ARGB_8888;
            file = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            if (file == null) {
                Log.e(LOG_TAG, "Cannot decode bitmap");
                return null;
            }
            Pix readBitmap = readBitmap(file);
            file.recycle();
            return readBitmap;
        } else {
            Log.e(LOG_TAG, "Cannot read file");
            return null;
        }
    }

    public static Pix readBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(LOG_TAG, "Bitmap must be non-null");
            return null;
        } else if (bitmap.getConfig() != Config.ARGB_8888) {
            Log.e(LOG_TAG, "Bitmap config must be ARGB_8888");
            return null;
        } else {
            long nativeReadBitmap = nativeReadBitmap(bitmap);
            if (nativeReadBitmap != 0) {
                return new Pix(nativeReadBitmap);
            }
            Log.e(LOG_TAG, "Failed to read pix from bitmap");
            return null;
        }
    }
}
