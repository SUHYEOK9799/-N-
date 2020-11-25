package com.example.preview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/* compiled from: MainActivity */
class fingerLog {
    private Context context;

    public fingerLog(Context context) {
        this.context = context;
    }

    public void callFunction(final int i, final int i2) {
        final Dialog dialog = new Dialog(this.context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.finger_log);
        dialog.show();
        Button button = (Button) dialog.findViewById(R.id.okButton);
        Button button2 = (Button) dialog.findViewById(R.id.cancelButton);
        TextView textView = (TextView) dialog.findViewById(R.id.count);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i2);
        stringBuilder.append("명");
        textView.setText(stringBuilder.toString());
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.countPeople = i2;
                MainActivity.OCR = true;
                MainActivity.getInstance.last.callFunction(i, i2);
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
