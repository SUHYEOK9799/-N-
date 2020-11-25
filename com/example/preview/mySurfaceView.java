package com.example.preview;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.util.List;

public class mySurfaceView extends SurfaceView implements Callback {
    private Context context;
    public List<Size> listPreviewSizes;
    private Camera mCamera = MainActivity.getCamera();
    private Size previewSize;

    public mySurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        if (this.mCamera == null) {
            this.mCamera = Camera.open();
        }
        this.listPreviewSizes = this.mCamera.getParameters().getSupportedPreviewSizes();
    }

    public boolean capture(PictureCallback pictureCallback) {
        Camera camera = this.mCamera;
        if (camera == null) {
            return null;
        }
        camera.takePicture(null, null, pictureCallback);
        return true;
    }

    public void surfaceCreated(android.view.SurfaceHolder r5) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = this;
        r0 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        if (r0 != 0) goto L_0x000a;	 Catch:{ IOException -> 0x0056 }
    L_0x0004:
        r0 = android.hardware.Camera.open();	 Catch:{ IOException -> 0x0056 }
        r4.mCamera = r0;	 Catch:{ IOException -> 0x0056 }
    L_0x000a:
        r0 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r0 = r0.getParameters();	 Catch:{ IOException -> 0x0056 }
        r1 = r4.getResources();	 Catch:{ IOException -> 0x0056 }
        r1 = r1.getConfiguration();	 Catch:{ IOException -> 0x0056 }
        r1 = r1.orientation;	 Catch:{ IOException -> 0x0056 }
        r2 = 2;
        r3 = "orientation";
        if (r1 == r2) goto L_0x002f;
    L_0x001f:
        r1 = "portrait";	 Catch:{ IOException -> 0x0056 }
        r0.set(r3, r1);	 Catch:{ IOException -> 0x0056 }
        r1 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r2 = 90;	 Catch:{ IOException -> 0x0056 }
        r1.setDisplayOrientation(r2);	 Catch:{ IOException -> 0x0056 }
        r0.setRotation(r2);	 Catch:{ IOException -> 0x0056 }
        goto L_0x003d;	 Catch:{ IOException -> 0x0056 }
    L_0x002f:
        r1 = "landscape";	 Catch:{ IOException -> 0x0056 }
        r0.set(r3, r1);	 Catch:{ IOException -> 0x0056 }
        r1 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r2 = 0;	 Catch:{ IOException -> 0x0056 }
        r1.setDisplayOrientation(r2);	 Catch:{ IOException -> 0x0056 }
        r0.setRotation(r2);	 Catch:{ IOException -> 0x0056 }
    L_0x003d:
        r1 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r1.setParameters(r0);	 Catch:{ IOException -> 0x0056 }
        r0 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r0.setPreviewDisplay(r5);	 Catch:{ IOException -> 0x0056 }
        r5 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r5.startPreview();	 Catch:{ IOException -> 0x0056 }
        r5 = r4.mCamera;	 Catch:{ IOException -> 0x0056 }
        r0 = new com.example.preview.mySurfaceView$1;	 Catch:{ IOException -> 0x0056 }
        r0.<init>();	 Catch:{ IOException -> 0x0056 }
        r5.autoFocus(r0);	 Catch:{ IOException -> 0x0056 }
    L_0x0056:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.preview.mySurfaceView.surfaceCreated(android.view.SurfaceHolder):void");
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (surfaceHolder.getSurface() != 0) {
            try {
                this.mCamera.stopPreview();
                i = this.mCamera.getParameters();
                i2 = MainActivity.getInstance.getWindowManager().getDefaultDisplay().getRotation();
                if (i2 == 0) {
                    this.mCamera.setDisplayOrientation(90);
                    i.setRotation(90);
                } else if (i2 == 1) {
                    this.mCamera.setDisplayOrientation(0);
                    i.setRotation(0);
                } else if (i2 == 2) {
                    this.mCamera.setDisplayOrientation(270);
                    i.setRotation(270);
                } else {
                    this.mCamera.setDisplayOrientation(180);
                    i.setRotation(180);
                }
                i.setPreviewSize(this.previewSize.width, this.previewSize.height);
                this.mCamera.setParameters(i);
                this.mCamera.setPreviewDisplay(surfaceHolder);
                this.mCamera.startPreview();
            } catch (SurfaceHolder surfaceHolder2) {
                surfaceHolder2.printStackTrace();
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        surfaceHolder = this.mCamera;
        if (surfaceHolder != null) {
            surfaceHolder.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    protected void onMeasure(int i, int i2) {
        i = resolveSize(getSuggestedMinimumWidth(), i);
        i2 = resolveSize(getSuggestedMinimumHeight(), i2);
        setMeasuredDimension(i, i2);
        List list = this.listPreviewSizes;
        if (list != null) {
            this.previewSize = getPreviewSize(list, i, i2);
        }
    }

    public Size getPreviewSize(List<Size> list, int i, int i2) {
        int i3 = i2;
        double d = ((double) i3) / ((double) i);
        Size size = null;
        if (list == null) {
            return null;
        }
        double d2 = Double.MAX_VALUE;
        double d3 = Double.MAX_VALUE;
        for (Size size2 : list) {
            if (Math.abs((((double) size2.width) / ((double) size2.height)) - d) <= 0.1d) {
                if (((double) Math.abs(size2.height - i3)) < d3) {
                    d3 = (double) Math.abs(size2.height - i3);
                    size = size2;
                }
            }
        }
        if (size == null) {
            for (Size size3 : list) {
                if (((double) Math.abs(size3.height - i3)) < d2) {
                    d2 = (double) Math.abs(size3.height - i3);
                    size = size3;
                }
            }
        }
        return size;
    }
}
