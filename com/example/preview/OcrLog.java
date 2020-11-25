package com.example.preview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/* compiled from: MainActivity */
class OcrLog {
    private Context context;

    public OcrLog(Context context) {
        this.context = context;
    }

    public void callFunction(final String str) {
        final Dialog dialog = new Dialog(this.context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.ocr_log);
        dialog.show();
        Button button = (Button) dialog.findViewById(R.id.okButton);
        Button button2 = (Button) dialog.findViewById(R.id.cancelButton);
        ((TextView) dialog.findViewById(R.id.total)).setText(str);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.OCR = null;
                MainActivity.finger = true;
                MainActivity.TotalNum = Integer.parseInt(str);
                Toast.makeText(OcrLog.this.context, "손가락을 촬영해 주세요 !", 1).show();
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.OCR = true;
                MainActivity.finger = null;
                dialog.dismiss();
            }
        });
    }
}
