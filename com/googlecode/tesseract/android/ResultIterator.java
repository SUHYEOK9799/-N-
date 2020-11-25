package com.googlecode.tesseract.android;

public class ResultIterator extends PageIterator {
    private final long mNativeResultIterator;

    private static native float nativeConfidence(long j, int i);

    private static native void nativeDelete(long j);

    private static native String[] nativeGetSymbolChoices(long j);

    private static native String nativeGetUTF8Text(long j, int i);

    private static native boolean nativeIsAtBeginningOf(long j, int i);

    private static native boolean nativeIsAtFinalElement(long j, int i, int i2);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
        System.loadLibrary("tess");
    }

    ResultIterator(long j) {
        super(j);
        this.mNativeResultIterator = j;
    }

    public String getUTF8Text(int i) {
        return nativeGetUTF8Text(this.mNativeResultIterator, i);
    }

    public float confidence(int i) {
        return nativeConfidence(this.mNativeResultIterator, i);
    }

    public boolean isAtBeginningOf(int i) {
        return nativeIsAtBeginningOf(this.mNativeResultIterator, i);
    }

    public boolean isAtFinalElement(int i, int i2) {
        return nativeIsAtFinalElement(this.mNativeResultIterator, i, i2);
    }

    public java.util.List<android.util.Pair<java.lang.String, java.lang.Double>> getSymbolChoicesAndConfidence() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r11 = this;
        r0 = r11.mNativeResultIterator;
        r0 = nativeGetSymbolChoices(r0);
        r1 = new java.util.ArrayList;
        r1.<init>();
        r2 = r0.length;
        r3 = 0;
        r4 = 0;
    L_0x000e:
        if (r4 >= r2) goto L_0x0056;
    L_0x0010:
        r5 = r0[r4];
        r6 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        r6 = r5.lastIndexOf(r6);
        r7 = 0;
        r7 = java.lang.Double.valueOf(r7);
        if (r6 <= 0) goto L_0x004a;
    L_0x0020:
        r8 = r5.substring(r3, r6);
        r6 = r6 + 1;
        r6 = r5.substring(r6);	 Catch:{ NumberFormatException -> 0x0033 }
        r9 = java.lang.Double.parseDouble(r6);	 Catch:{ NumberFormatException -> 0x0033 }
        r7 = java.lang.Double.valueOf(r9);	 Catch:{ NumberFormatException -> 0x0033 }
        goto L_0x004b;
    L_0x0033:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r9 = "Invalid confidence level for ";
        r6.append(r9);
        r6.append(r5);
        r5 = r6.toString();
        r6 = "ResultIterator";
        android.util.Log.e(r6, r5);
        goto L_0x004b;
    L_0x004a:
        r8 = r5;
    L_0x004b:
        r5 = new android.util.Pair;
        r5.<init>(r8, r7);
        r1.add(r5);
        r4 = r4 + 1;
        goto L_0x000e;
    L_0x0056:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.googlecode.tesseract.android.ResultIterator.getSymbolChoicesAndConfidence():java.util.List<android.util.Pair<java.lang.String, java.lang.Double>>");
    }

    public void delete() {
        nativeDelete(this.mNativeResultIterator);
    }
}
