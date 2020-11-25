package com.example.preview;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {
    static boolean OCR = true;
    static int TotalNum = 0;
    private static ImageButton btn = null;
    static int countPeople = 1;
    static boolean finger = false;
    public static MainActivity getInstance;
    private static Camera mCamera;
    private static CameraInfo mCameraInfo;
    private static mySurfaceView surfaceView;
    fingerLog Finger_Log = new fingerLog(this);
    ImageView OCRimg;
    OcrLog Ocr_Log = new OcrLog(this);
    private int RESULT_PERMISSIONS = 100;
    private SurfaceHolder holder;
    total_final last = new total_final(this);
    TessBaseAPI tessBaseAPI;
    TextView textView;

    private class AsyncTess extends AsyncTask<Bitmap, Integer, String> {
        private AsyncTess() {
        }

        protected String doInBackground(Bitmap... bitmapArr) {
            MainActivity.this.tessBaseAPI.setImage(bitmapArr[0]);
            return MainActivity.this.tessBaseAPI.getUTF8Text();
        }

        protected void onPostExecute(String str) {
            String str2 = "";
            str = str.replaceAll("[^가-힣xfe0-9a-zA-Z\\s]", " ").replaceAll("., ", str2).split("\n");
            int i = 0;
            while (i < str.length) {
                if (!str[i].contains("판매액") && !str[i].contains("계")) {
                    if (!str[i].contains("합")) {
                        i++;
                    }
                }
                str = str[i];
            }
            str = str2;
            if (str != str2) {
                str = str.replaceAll("[^0-9]", str2);
                MainActivity.this.textView.setText(str);
                MainActivity.this.Ocr_Log.callFunction(str);
            }
            MainActivity.btn.setEnabled(true);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        OpenCVLoader.initDebug();
        supportRequestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        requestPermissionCamera();
        this.textView = (TextView) findViewById(R.id.textView);
        this.OCRimg = (ImageView) findViewById(R.id.OCRimg);
        btn = (ImageButton) findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.OCR != null) {
                    MainActivity.this.captureOCR();
                } else if (MainActivity.finger != null) {
                    MainActivity.this.captureFinger();
                }
            }
        });
        this.tessBaseAPI = new TessBaseAPI();
        bundle = new StringBuilder();
        bundle.append(getFilesDir());
        bundle.append("/tesseract");
        bundle = bundle.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bundle);
        stringBuilder.append("/tessdata");
        if (checkLanguageFile(stringBuilder.toString())) {
            this.tessBaseAPI.init(bundle, "kor");
        }
    }

    private void captureOCR() {
        surfaceView.capture(new PictureCallback() {
            public void onPictureTaken(byte[] bArr, Camera camera) {
                new Options().inSampleSize = 8;
                bArr = MainActivity.GetRotatedBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length), 90);
                Bitmap createBitmap = Bitmap.createBitmap(bArr, 0, 0, bArr.getWidth(), bArr.getHeight(), null, true);
                Utils.bitmapToMat(bArr, new Mat());
                Mat mat = new Mat();
                Utils.bitmapToMat(bArr, mat);
                Mat mat2 = new Mat();
                Mat mat3 = new Mat();
                Imgproc.cvtColor(mat, mat2, 6);
                Mat mat4 = mat2;
                Mat mat5 = mat3;
                Imgproc.Canny(mat4, mat5, 10.0d, 100.0d, 3, true);
                Imgproc.threshold(mat4, mat5, 150.0d, 255.0d, 0);
                Utils.matToBitmap(mat3, Bitmap.createBitmap(mat3.cols(), mat3.rows(), Config.ARGB_8888));
                List arrayList = new ArrayList();
                mat4 = new Mat();
                Imgproc.findContours(mat3, arrayList, mat4, 0, 2);
                Bitmap bitmap = createBitmap;
                for (int i = 0; i >= 0; i = (int) mat4.get(0, i)[0]) {
                    Rect boundingRect = Imgproc.boundingRect((MatOfPoint) arrayList.get(i));
                    bitmap = Bitmap.createBitmap(bArr, (int) boundingRect.tl().x, (int) boundingRect.tl().y, boundingRect.width, boundingRect.height);
                    ((ImageView) MainActivity.this.findViewById(R.id.OCRimg)).setImageBitmap(bitmap);
                    bitmap = Bitmap.createBitmap(bitmap);
                }
                MainActivity.btn.setEnabled(false);
                new AsyncTess().execute(new Bitmap[]{bitmap});
                camera.startPreview();
            }
        });
    }

    private void captureFinger() {
        surfaceView.capture(new PictureCallback() {
            public void onPictureTaken(byte[] bArr, Camera camera) {
                List list;
                AnonymousClass3 anonymousClass3 = this;
                byte[] bArr2 = bArr;
                new Options().inSampleSize = 8;
                Bitmap GetRotatedBitmap = MainActivity.GetRotatedBitmap(BitmapFactory.decodeByteArray(bArr2, 0, bArr2.length), 90);
                Bitmap.createBitmap(GetRotatedBitmap, 0, 0, GetRotatedBitmap.getWidth(), GetRotatedBitmap.getHeight(), null, true);
                Mat mat = new Mat();
                Utils.bitmapToMat(GetRotatedBitmap, mat);
                Mat mat2 = new Mat(mat.rows(), mat.cols(), 0, new Scalar(3.0d));
                Imgproc.cvtColor(mat, mat2, 40, 3);
                Scalar scalar = new Scalar(0.0d, 48.0d, 80.0d);
                Scalar scalar2 = new Scalar(255.0d, 125.0d, 140.0d);
                Mat mat3 = new Mat(mat2.rows(), mat2.cols(), 0, new Scalar(3.0d));
                Core.inRange(mat2, scalar, scalar2, mat3);
                mat2 = Imgproc.getStructuringElement(0, new Size(3.0d, 3.0d));
                Mat mat4 = new Mat();
                Mat mat5 = new Mat();
                int i = 1;
                Imgproc.dilate(mat3, mat5, mat2, new Point(-1.0d, -1.0d), 1);
                Imgproc.erode(mat5, mat4, mat2, new Point(-1.0d, -1.0d), 1);
                Mat mat6 = new Mat();
                Imgproc.GaussianBlur(mat4, mat6, new Size(3.0d, 3.0d), 0.0d);
                mat2 = new Mat();
                Imgproc.threshold(mat6, mat2, 100.0d, 255.0d, 0);
                List<MatOfPoint> arrayList = new ArrayList();
                Imgproc.findContours(mat2, arrayList, new Mat(), 0, 2);
                GetRotatedBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Config.ARGB_8888);
                MatOfInt4 matOfInt4 = new MatOfInt4();
                Point[] pointArr = new Point[0];
                String str = "개";
                if (arrayList.size() != 0) {
                    int i2;
                    int i3;
                    Bitmap bitmap;
                    List arrayList2 = new ArrayList();
                    for (MatOfPoint matOfPoint : arrayList) {
                        MatOfInt matOfInt = new MatOfInt();
                        Imgproc.convexHull(matOfPoint, matOfInt);
                        Point[] toArray = matOfPoint.toArray();
                        Point[] pointArr2 = new Point[matOfInt.rows()];
                        List toList = matOfInt.toList();
                        for (i2 = 0; i2 < toList.size(); i2++) {
                            pointArr2[i2] = toArray[((Integer) toList.get(i2)).intValue()];
                        }
                        arrayList2.add(new MatOfPoint(pointArr2));
                        Imgproc.convexityDefects(matOfPoint, matOfInt, matOfInt4);
                    }
                    for (i3 = 0; i3 < arrayList.size(); i3++) {
                        Imgproc.drawContours(mat, arrayList2, i3, new Scalar(0.0d, 255.0d, 0.0d), 5);
                    }
                    if (matOfInt4.height() > 0) {
                        Bitmap bitmap2;
                        List list2;
                        PrintStream printStream = System.out;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("디펙트 = ");
                        stringBuilder.append(matOfInt4.size());
                        stringBuilder.append(str);
                        printStream.println(stringBuilder.toString());
                        i3 = 0;
                        i2 = 0;
                        int i4 = 0;
                        while (i3 < matOfInt4.height() - i) {
                            i2 = (int) matOfInt4.get(i3, 0)[0];
                            int i5 = (int) matOfInt4.get(i3, 0)[i];
                            int i6 = (int) matOfInt4.get(i3, 0)[2];
                            i3++;
                            Bitmap bitmap3 = GetRotatedBitmap;
                            bArr = mat;
                            int i7 = (int) matOfInt4.get(i3, 0)[0];
                            int i8 = (int) matOfInt4.get(i3, 0)[i];
                            i = (int) matOfInt4.get(i3, 0)[2];
                            bitmap2 = bitmap3;
                            int i9 = i3;
                            list = list2;
                            double sqrt = (double) ((int) Math.sqrt(Math.pow((double) (i5 - i2), 2.0d) + Math.pow((double) (i8 - i7), 2.0d)));
                            MatOfInt4 matOfInt42 = matOfInt4;
                            double sqrt2 = (double) ((int) Math.sqrt(Math.pow((double) (i6 - i2), 2.0d) + Math.pow((double) (i - i7), 2.0d)));
                            double sqrt3 = (double) ((int) Math.sqrt(Math.pow((double) (i5 - i6), 2.0d) + Math.pow((double) (i8 - i), 2.0d)));
                            double acos = ((double) (((int) Math.acos(((Math.pow(sqrt2, 2.0d) + Math.pow(sqrt3, 2.0d)) - Math.pow(sqrt, 2.0d)) / ((2.0d * sqrt2) * sqrt3))) * 180)) / 3.14d;
                            PrintStream printStream2 = System.out;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(sqrt);
                            String str2 = " /// ";
                            stringBuilder2.append(str2);
                            stringBuilder2.append(sqrt2);
                            stringBuilder2.append(str2);
                            stringBuilder2.append(sqrt3);
                            stringBuilder2.append(" /// 각도 = ");
                            stringBuilder2.append(acos);
                            printStream2.println(stringBuilder2.toString());
                            if (acos <= 90.0d) {
                                i4++;
                            }
                            mat = bArr;
                            i2 = i4;
                            matOfInt4 = matOfInt42;
                            GetRotatedBitmap = bitmap2;
                            list2 = list;
                            i3 = i9;
                            i = 1;
                        }
                        bitmap2 = GetRotatedBitmap;
                        bArr = mat;
                        list = list2;
                        MainActivity.this.Finger_Log.callFunction(MainActivity.TotalNum, i2 + 1);
                        mat2 = bArr;
                        bitmap = bitmap2;
                    } else {
                        list = arrayList;
                        Mat mat7 = mat;
                        bitmap = GetRotatedBitmap;
                        mat2 = mat7;
                    }
                    Utils.matToBitmap(mat2, bitmap);
                    MainActivity.this.OCRimg.setImageBitmap(bitmap);
                } else {
                    list = arrayList;
                }
                PrintStream printStream3 = System.out;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("컨투어 = ");
                stringBuilder3.append(list.size());
                stringBuilder3.append(str);
                printStream3.println(stringBuilder3.toString());
                camera.startPreview();
            }
        });
    }

    private void createFiles(String str) {
        try {
            InputStream open = getAssets().open("kor.traineddata");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("/kor.traineddata");
            OutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
            str = new byte[1024];
            while (true) {
                int read = open.read(str);
                if (read != -1) {
                    fileOutputStream.write(str, 0, read);
                } else {
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (String str2) {
            str2.printStackTrace();
        }
    }

    boolean checkLanguageFile(String str) {
        File file = new File(str);
        if (!file.exists() && file.mkdirs()) {
            createFiles(str);
        } else if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("/kor.traineddata");
            if (!new File(stringBuilder.toString()).exists()) {
                createFiles(str);
            }
        }
        return true;
    }

    public static synchronized Bitmap GetRotatedBitmap(Bitmap bitmap, int i) {
        synchronized (MainActivity.class) {
            if (!(i == 0 || bitmap == null)) {
                Matrix matrix = new Matrix();
                matrix.setRotate((float) i, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
                try {
                    i = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    if (bitmap != i) {
                        bitmap = i;
                    }
                } catch (int i2) {
                    i2.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public static Camera getCamera() {
        return mCamera;
    }

    private void setInit() {
        getInstance = this;
        mCamera = Camera.open();
        setContentView((int) R.layout.activity_main);
        surfaceView = (mySurfaceView) findViewById(R.id.preview);
        this.holder = surfaceView.getHolder();
        this.holder.addCallback(surfaceView);
        this.holder.setType(3);
    }

    public boolean requestPermissionCamera() {
        if (VERSION.SDK_INT >= 23) {
            String str = "android.permission.CAMERA";
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                ActivityCompat.requestPermissions(this, new String[]{str}, this.RESULT_PERMISSIONS);
            } else {
                setInit();
            }
            return true;
        }
        setInit();
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (this.RESULT_PERMISSIONS == i && iArr.length > 0 && iArr[0] == 0) {
            setInit();
        }
    }
}
