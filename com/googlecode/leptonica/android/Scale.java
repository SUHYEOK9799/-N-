package com.googlecode.leptonica.android;

public class Scale {

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$googlecode$leptonica$android$Scale$ScaleType = new int[ScaleType.values().length];

        static {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r0 = com.googlecode.leptonica.android.Scale.ScaleType.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$googlecode$leptonica$android$Scale$ScaleType = r0;
            r0 = $SwitchMap$com$googlecode$leptonica$android$Scale$ScaleType;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.googlecode.leptonica.android.Scale.ScaleType.FILL;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$googlecode$leptonica$android$Scale$ScaleType;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.googlecode.leptonica.android.Scale.ScaleType.FIT;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;	 Catch:{ NoSuchFieldError -> 0x001f }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            r0 = $SwitchMap$com$googlecode$leptonica$android$Scale$ScaleType;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = com.googlecode.leptonica.android.Scale.ScaleType.FIT_SHRINK;	 Catch:{ NoSuchFieldError -> 0x002a }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x002a }
            r2 = 3;	 Catch:{ NoSuchFieldError -> 0x002a }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x002a }
        L_0x002a:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.googlecode.leptonica.android.Scale.1.<clinit>():void");
        }
    }

    public enum ScaleType {
        FILL,
        FIT,
        FIT_SHRINK
    }

    private static native long nativeScale(long j, float f, float f2);

    private static native long nativeScaleGeneral(long j, float f, float f2, float f3, int i);

    static {
        System.loadLibrary("jpgt");
        System.loadLibrary("pngt");
        System.loadLibrary("lept");
    }

    public static Pix scaleToSize(Pix pix, int i, int i2, ScaleType scaleType) {
        if (pix != null) {
            i = ((float) i) / ((float) pix.getWidth());
            i2 = ((float) i2) / ((float) pix.getHeight());
            scaleType = AnonymousClass1.$SwitchMap$com$googlecode$leptonica$android$Scale$ScaleType[scaleType.ordinal()];
            if (scaleType != 1) {
                if (scaleType == 2) {
                    i = Math.min(i, i2);
                } else if (scaleType == 3) {
                    i = Math.min(1.0f, Math.min(i, i2));
                }
                i2 = i;
            }
            return scale(pix, i, i2);
        }
        throw new IllegalArgumentException("Source pix must be non-null");
    }

    public static Pix scale(Pix pix, float f) {
        return scale(pix, f, f);
    }

    public static Pix scaleWithoutSharpening(Pix pix, float f) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (f > 0.0f) {
            return new Pix(nativeScaleGeneral(pix.getNativePix(), f, f, 0.0f, 0));
        } else {
            throw new IllegalArgumentException("Scaling factor must be positive");
        }
    }

    public static Pix scale(Pix pix, float f, float f2) {
        if (pix == null) {
            throw new IllegalArgumentException("Source pix must be non-null");
        } else if (f <= 0.0f) {
            throw new IllegalArgumentException("X scaling factor must be positive");
        } else if (f2 > 0.0f) {
            pix = nativeScale(pix.getNativePix(), f, f2);
            if (pix != 0) {
                return new Pix(pix);
            }
            throw new RuntimeException("Failed to natively scale pix");
        } else {
            throw new IllegalArgumentException("Y scaling factor must be positive");
        }
    }
}
