package android.support.v4.os;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.core.os.IResultReceiver;
import androidx.core.os.IResultReceiver.Stub;

@SuppressLint({"BanParcelableUsage"})
@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public class ResultReceiver implements Parcelable {
    public static final Creator<ResultReceiver> CREATOR = new Creator<ResultReceiver>() {
        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        public ResultReceiver[] newArray(int i) {
            return new ResultReceiver[i];
        }
    };
    final Handler mHandler;
    final boolean mLocal;
    IResultReceiver mReceiver;

    class MyRunnable implements Runnable {
        final int mResultCode;
        final Bundle mResultData;

        MyRunnable(int i, Bundle bundle) {
            this.mResultCode = i;
            this.mResultData = bundle;
        }

        public void run() {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

    class MyResultReceiver extends Stub {
        MyResultReceiver() {
        }

        public void send(int i, Bundle bundle) {
            if (ResultReceiver.this.mHandler != null) {
                ResultReceiver.this.mHandler.post(new MyRunnable(i, bundle));
            } else {
                ResultReceiver.this.onReceiveResult(i, bundle);
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    protected void onReceiveResult(int i, Bundle bundle) {
    }

    public ResultReceiver(Handler handler) {
        this.mLocal = true;
        this.mHandler = handler;
    }

    public void send(int r3, android.os.Bundle r4) {
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
        r2 = this;
        r0 = r2.mLocal;
        if (r0 == 0) goto L_0x0015;
    L_0x0004:
        r0 = r2.mHandler;
        if (r0 == 0) goto L_0x0011;
    L_0x0008:
        r1 = new android.support.v4.os.ResultReceiver$MyRunnable;
        r1.<init>(r3, r4);
        r0.post(r1);
        goto L_0x0014;
    L_0x0011:
        r2.onReceiveResult(r3, r4);
    L_0x0014:
        return;
    L_0x0015:
        r0 = r2.mReceiver;
        if (r0 == 0) goto L_0x001c;
    L_0x0019:
        r0.send(r3, r4);	 Catch:{ RemoteException -> 0x001c }
    L_0x001c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.os.ResultReceiver.send(int, android.os.Bundle):void");
    }

    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.mReceiver == 0) {
                this.mReceiver = new MyResultReceiver();
            }
            parcel.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    ResultReceiver(Parcel parcel) {
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = Stub.asInterface(parcel.readStrongBinder());
    }
}
