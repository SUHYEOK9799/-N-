package com.googlecode.tesseract.android;

import android.graphics.Bitmap;
import android.graphics.Rect;
import androidx.annotation.WorkerThread;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.Pixa;
import com.googlecode.leptonica.android.ReadFile;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TessBaseAPI {
    @Deprecated
    public static final int OEM_CUBE_ONLY = 1;
    public static final int OEM_DEFAULT = 3;
    @Deprecated
    public static final int OEM_TESSERACT_CUBE_COMBINED = 2;
    public static final int OEM_TESSERACT_ONLY = 0;
    public static final String VAR_CHAR_BLACKLIST = "tessedit_char_blacklist";
    public static final String VAR_CHAR_WHITELIST = "tessedit_char_whitelist";
    public static final String VAR_FALSE = "F";
    public static final String VAR_SAVE_BLOB_CHOICES = "save_blob_choices";
    public static final String VAR_TRUE = "T";
    private long mNativeData;
    private boolean mRecycled;
    private ProgressNotifier progressNotifier;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OcrEngineMode {
    }

    public static final class PageIteratorLevel {
        public static final int RIL_BLOCK = 0;
        public static final int RIL_PARA = 1;
        public static final int RIL_SYMBOL = 4;
        public static final int RIL_TEXTLINE = 2;
        public static final int RIL_WORD = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Level {
        }
    }

    public static final class PageSegMode {
        public static final int PSM_AUTO = 3;
        public static final int PSM_AUTO_ONLY = 2;
        public static final int PSM_AUTO_OSD = 1;
        public static final int PSM_CIRCLE_WORD = 9;
        public static final int PSM_OSD_ONLY = 0;
        public static final int PSM_RAW_LINE = 13;
        public static final int PSM_SINGLE_BLOCK = 6;
        public static final int PSM_SINGLE_BLOCK_VERT_TEXT = 5;
        public static final int PSM_SINGLE_CHAR = 10;
        public static final int PSM_SINGLE_COLUMN = 4;
        public static final int PSM_SINGLE_LINE = 7;
        public static final int PSM_SINGLE_WORD = 8;
        public static final int PSM_SPARSE_TEXT = 11;
        public static final int PSM_SPARSE_TEXT_OSD = 12;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Mode {
        }
    }

    public interface ProgressNotifier {
        void onProgressValues(ProgressValues progressValues);
    }

    public class ProgressValues {
        private final int percent;
        private final Rect textRect;
        private final Rect wordRect;

        public ProgressValues(int i, Rect rect, Rect rect2) {
            this.percent = i;
            this.wordRect = rect;
            this.textRect = rect2;
        }

        public int getPercent() {
            return this.percent;
        }

        public Rect getCurrentWordRect() {
            return this.wordRect;
        }

        public Rect getCurrentRect() {
            return this.textRect;
        }
    }

    private native boolean nativeAddPageToDocument(long j, long j2, String str, long j3);

    private native boolean nativeBeginDocument(long j, String str);

    private static native void nativeClassInit();

    private native void nativeClear(long j);

    private native long nativeConstruct();

    private native void nativeEnd(long j);

    private native boolean nativeEndDocument(long j);

    private native String nativeGetBoxText(long j, int i);

    private native long nativeGetConnectedComponents(long j);

    private native String nativeGetHOCRText(long j, int i);

    private native String nativeGetInitLanguagesAsString(long j);

    private native int nativeGetPageSegMode(long j);

    private native long nativeGetRegions(long j);

    private native long nativeGetResultIterator(long j);

    private native long nativeGetStrips(long j);

    private native long nativeGetTextlines(long j);

    private native long nativeGetThresholdedImage(long j);

    private native String nativeGetUTF8Text(long j);

    private native String nativeGetVersion(long j);

    private native long nativeGetWords(long j);

    private native boolean nativeInit(long j, String str, String str2);

    private native boolean nativeInitOem(long j, String str, String str2, int i);

    private native int nativeMeanConfidence(long j);

    private native void nativeReadConfigFile(long j, String str);

    private native void nativeSetDebug(long j, boolean z);

    private native void nativeSetImageBytes(long j, byte[] bArr, int i, int i2, int i3, int i4);

    private native void nativeSetImagePix(long j, long j2);

    private native void nativeSetInputName(long j, String str);

    private native void nativeSetOutputName(long j, String str);

    private native void nativeSetPageSegMode(long j, int i);

    private native void nativeSetRectangle(long j, int i, int i2, int i3, int i4);

    private native boolean nativeSetVariable(long j, String str, String str2);

    private native void nativeStop(long j);

    private native int[] nativeWordConfidences(long j);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
        System.loadLibrary("tess");
        nativeClassInit();
    }

    public TessBaseAPI() {
        this.mNativeData = nativeConstruct();
        if (this.mNativeData != 0) {
            this.mRecycled = false;
            return;
        }
        throw new RuntimeException("Can't create TessBaseApi object");
    }

    public TessBaseAPI(ProgressNotifier progressNotifier) {
        this();
        this.progressNotifier = progressNotifier;
    }

    public boolean init(String str, String str2) {
        return init(str, str2, 3);
    }

    public boolean init(String str, String str2, int i) {
        if (str != null) {
            StringBuilder stringBuilder;
            if (!str.endsWith(File.separator)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(File.separator);
                str = stringBuilder.toString();
            }
            String str3 = str;
            if (new File(str3).exists() != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str3);
                stringBuilder.append("tessdata");
                str = new File(stringBuilder.toString());
                if (str.exists() && str.isDirectory()) {
                    if (i != 1) {
                        for (String str4 : str2.split("\\+")) {
                            if (!str4.startsWith("~")) {
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append(str);
                                stringBuilder2.append(File.separator);
                                stringBuilder2.append(str4);
                                stringBuilder2.append(".traineddata");
                                File file = new File(stringBuilder2.toString());
                                if (!file.exists()) {
                                    str2 = new StringBuilder();
                                    str2.append("Data file not found at ");
                                    str2.append(file);
                                    throw new IllegalArgumentException(str2.toString());
                                } else if (str4.equals("ara") || (str4.equals("hin") && i == 3)) {
                                    stringBuilder2 = new StringBuilder();
                                    stringBuilder2.append(str);
                                    stringBuilder2.append(File.separator);
                                    stringBuilder2.append(str4);
                                    stringBuilder2.append(".cube.params");
                                    if (!new File(stringBuilder2.toString()).exists()) {
                                        throw new IllegalArgumentException("Cube data files not found. See https://github.com/rmtheis/tess-two/issues/239");
                                    }
                                }
                            }
                        }
                    }
                    str = nativeInitOem(this.mNativeData, str3, str2, i);
                    if (str != null) {
                        this.mRecycled = false;
                    }
                    return str;
                }
                throw new IllegalArgumentException("Data path must contain subfolder tessdata!");
            }
            throw new IllegalArgumentException("Data path does not exist!");
        }
        throw new IllegalArgumentException("Data path must not be null!");
    }

    public String getInitLanguagesAsString() {
        if (!this.mRecycled) {
            return nativeGetInitLanguagesAsString(this.mNativeData);
        }
        throw new IllegalStateException();
    }

    public void clear() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeClear(this.mNativeData);
    }

    public void end() {
        if (!this.mRecycled) {
            nativeEnd(this.mNativeData);
            this.mRecycled = true;
        }
    }

    public boolean setVariable(String str, String str2) {
        if (!this.mRecycled) {
            return nativeSetVariable(this.mNativeData, str, str2);
        }
        throw new IllegalStateException();
    }

    public int getPageSegMode() {
        if (!this.mRecycled) {
            return nativeGetPageSegMode(this.mNativeData);
        }
        throw new IllegalStateException();
    }

    public void setPageSegMode(int i) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetPageSegMode(this.mNativeData, i);
    }

    public void setDebug(boolean z) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetDebug(this.mNativeData, z);
    }

    public void setRectangle(Rect rect) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        setRectangle(rect.left, rect.top, rect.width(), rect.height());
    }

    public void setRectangle(int i, int i2, int i3, int i4) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetRectangle(this.mNativeData, i, i2, i3, i4);
    }

    @WorkerThread
    public void setImage(File file) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        file = ReadFile.readFile(file);
        if (file != null) {
            nativeSetImagePix(this.mNativeData, file.getNativePix());
            file.recycle();
            return;
        }
        throw new RuntimeException("Failed to read image file");
    }

    @WorkerThread
    public void setImage(Bitmap bitmap) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        bitmap = ReadFile.readBitmap(bitmap);
        if (bitmap != null) {
            nativeSetImagePix(this.mNativeData, bitmap.getNativePix());
            bitmap.recycle();
            return;
        }
        throw new RuntimeException("Failed to read bitmap");
    }

    @WorkerThread
    public void setImage(Pix pix) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetImagePix(this.mNativeData, pix.getNativePix());
    }

    @WorkerThread
    public void setImage(byte[] bArr, int i, int i2, int i3, int i4) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetImageBytes(this.mNativeData, bArr, i, i2, i3, i4);
    }

    @WorkerThread
    public String getUTF8Text() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        String nativeGetUTF8Text = nativeGetUTF8Text(this.mNativeData);
        if (nativeGetUTF8Text != null) {
            return nativeGetUTF8Text.trim();
        }
        return null;
    }

    public int meanConfidence() {
        if (!this.mRecycled) {
            return nativeMeanConfidence(this.mNativeData);
        }
        throw new IllegalStateException();
    }

    public int[] wordConfidences() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        int[] nativeWordConfidences = nativeWordConfidences(this.mNativeData);
        if (nativeWordConfidences == null) {
            return new int[0];
        }
        return nativeWordConfidences;
    }

    public Pix getThresholdedImage() {
        if (!this.mRecycled) {
            return new Pix(nativeGetThresholdedImage(this.mNativeData));
        }
        throw new IllegalStateException();
    }

    public Pixa getRegions() {
        if (!this.mRecycled) {
            return new Pixa(nativeGetRegions(this.mNativeData), 0, 0);
        }
        throw new IllegalStateException();
    }

    public Pixa getTextlines() {
        if (!this.mRecycled) {
            return new Pixa(nativeGetTextlines(this.mNativeData), 0, 0);
        }
        throw new IllegalStateException();
    }

    public Pixa getStrips() {
        if (!this.mRecycled) {
            return new Pixa(nativeGetStrips(this.mNativeData), 0, 0);
        }
        throw new IllegalStateException();
    }

    public Pixa getWords() {
        if (!this.mRecycled) {
            return new Pixa(nativeGetWords(this.mNativeData), 0, 0);
        }
        throw new IllegalStateException();
    }

    public Pixa getConnectedComponents() {
        if (!this.mRecycled) {
            return new Pixa(nativeGetConnectedComponents(this.mNativeData), 0, 0);
        }
        throw new IllegalStateException();
    }

    public ResultIterator getResultIterator() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        long nativeGetResultIterator = nativeGetResultIterator(this.mNativeData);
        if (nativeGetResultIterator == 0) {
            return null;
        }
        return new ResultIterator(nativeGetResultIterator);
    }

    @WorkerThread
    public String getHOCRText(int i) {
        if (!this.mRecycled) {
            return nativeGetHOCRText(this.mNativeData, i);
        }
        throw new IllegalStateException();
    }

    public void setInputName(String str) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetInputName(this.mNativeData, str);
    }

    public void setOutputName(String str) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeSetOutputName(this.mNativeData, str);
    }

    public void readConfigFile(String str) {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeReadConfigFile(this.mNativeData, str);
    }

    public String getBoxText(int i) {
        if (!this.mRecycled) {
            return nativeGetBoxText(this.mNativeData, i);
        }
        throw new IllegalStateException();
    }

    public String getVersion() {
        return nativeGetVersion(this.mNativeData);
    }

    public void stop() {
        if (this.mRecycled) {
            throw new IllegalStateException();
        }
        nativeStop(this.mNativeData);
    }

    protected void onProgressValues(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        if (this.progressNotifier != null) {
            this.progressNotifier.onProgressValues(new ProgressValues(i, new Rect(i2, i8 - i4, i3, i8 - i5), new Rect(i6, i9, i7, i8)));
        }
    }

    public boolean beginDocument(TessPdfRenderer tessPdfRenderer, String str) {
        return nativeBeginDocument(tessPdfRenderer.getNativePdfRenderer(), str);
    }

    public boolean beginDocument(TessPdfRenderer tessPdfRenderer) {
        return nativeBeginDocument(tessPdfRenderer.getNativePdfRenderer(), "");
    }

    public boolean endDocument(TessPdfRenderer tessPdfRenderer) {
        return nativeEndDocument(tessPdfRenderer.getNativePdfRenderer());
    }

    public boolean addPageToDocument(Pix pix, String str, TessPdfRenderer tessPdfRenderer) {
        return nativeAddPageToDocument(this.mNativeData, pix.getNativePix(), str, tessPdfRenderer.getNativePdfRenderer());
    }

    long getNativeData() {
        return this.mNativeData;
    }
}
