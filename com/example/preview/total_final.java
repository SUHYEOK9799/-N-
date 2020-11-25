package com.example.preview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/* compiled from: MainActivity */
class total_final {
    private Context context;

    public total_final(Context context) {
        this.context = context;
    }

    public void callFunction(int i, int i2) {
        final Dialog dialog = new Dialog(this.context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.total_log);
        dialog.show();
        Button button = (Button) dialog.findViewById(R.id.okButton);
        TextView textView = (TextView) dialog.findViewById(R.id.people);
        TextView textView2 = (TextView) dialog.findViewById(R.id.sum);
        TextView textView3 = (TextView) dialog.findViewById(R.id.total_num);
        int i3 = i / i2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i2);
        i2 = "";
        stringBuilder.append(i2);
        textView.setText(stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(i);
        stringBuilder2.append(i2);
        textView2.setText(stringBuilder2.toString());
        i = new StringBuilder();
        i.append(i3);
        i.append(i2);
        textView3.setText(i.toString());
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
