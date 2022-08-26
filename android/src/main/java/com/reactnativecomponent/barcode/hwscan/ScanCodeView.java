package com.reactnativecomponent.barcode.hwscan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.huawei.hms.hmsscankit.OnErrorCallback;
import com.huawei.hms.hmsscankit.OnResultCallback;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.ml.scan.HmsScan;
import com.reactnativecomponent.barcode.R;

@SuppressLint("ViewConstructor")
public class ScanCodeView extends FrameLayout {

    private final String TAG = getClass().getSimpleName();

    private Activity mActivity;

    private int scanType = 0;

    private RemoteView remoteView;

    private ScanMaskView scanMaskView;

    private Rect mScanRect;

    private OnScanResultListener onScanResultListener;

    public ScanCodeView(Activity activity, @NonNull Context context) {
        super(context);
        Log.e(TAG,"ScanCodeView");
        mActivity = activity;
    }

    public void setScanType(int scanType){
        Log.e(TAG,"scanType = " + scanType);
        this.scanType = scanType;
    }

    public int getScanType(){
        return scanType;
    }

    @Override
    protected void onAttachedToWindow() {
        Log.e(TAG,"onAttachedToWindow");
        initScanParams(mActivity);
        initMaskParams();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG,"onDetachedFromWindow");
        if (remoteView != null){
            remoteView.onPause();
            remoteView.onStop();
            remoteView.onDestroy();
        }
    }

    private void initScanParams(Activity activity){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        int mScreenWidth = displayMetrics.widthPixels;
        int mScreenHeight = (int) (displayMetrics.heightPixels - ((displayMetrics.heightPixels / 2210.0F) * 500));
        int scanFrameSize = (int) (240 * density);
        mScanRect = new Rect();
        mScanRect.left = mScreenWidth / 2 - scanFrameSize / 2;
        mScanRect.right = mScreenWidth / 2 + scanFrameSize / 2;
        mScanRect.top = mScreenHeight / 2 - scanFrameSize / 2;
        mScanRect.bottom = mScreenHeight / 2 + scanFrameSize / 2;
        Log.e(TAG,"density = " + density + " ,mScreenWidth = " + mScreenWidth + " ,mScreenHeight = " + mScreenHeight + " ,scanFrameSize = " + scanFrameSize);
        Log.e(TAG,"mScanRect = " + mScanRect.toString());
        if (scanType == 1){
            //条形码
            remoteView = new RemoteView.Builder().setContext(activity).setBoundingBox(mScanRect).setFormat(HmsScan.CODE128_SCAN_TYPE,HmsScan.CODE39_SCAN_TYPE,HmsScan.CODE93_SCAN_TYPE
                    ,HmsScan.CODABAR_SCAN_TYPE,HmsScan.EAN13_SCAN_TYPE,HmsScan.EAN8_SCAN_TYPE,HmsScan.ITF14_SCAN_TYPE
                    ,HmsScan.UPCCODE_A_SCAN_TYPE,HmsScan.UPCCODE_E_SCAN_TYPE).build();
        }else if (scanType == 2){
            //二维码
            remoteView = new RemoteView.Builder().setContext(activity).setBoundingBox(mScanRect).setFormat(HmsScan.QRCODE_SCAN_TYPE,HmsScan.DATAMATRIX_SCAN_TYPE,HmsScan.PDF417_SCAN_TYPE
                    ,HmsScan.AZTEC_SCAN_TYPE).build();
        }else {
            //全部类型
            remoteView = new RemoteView.Builder().setContext(activity).setBoundingBox(mScanRect).build();
        }
        //扫码结果
        remoteView.setOnResultCallback(new OnResultCallback() {
            @Override
            public void onResult(HmsScan[] hmsScans) {
                if (hmsScans != null && hmsScans.length > 0 && hmsScans[0] != null){
                    String scanResult = hmsScans[0].getOriginalValue();
                    Log.e(TAG,"scanResult = " + scanResult);
                    if (onScanResultListener != null){
                        onScanResultListener.onScanResult(scanResult);
                    }
                }else {
                    Log.e(TAG,"scanResult Error");
                    if (onScanResultListener != null){
                        onScanResultListener.onScanResult("");
                    }
                }
            }
        });
        //扫码失败
        remoteView.setOnErrorCallback(new OnErrorCallback() {
            @Override
            public void onError(int i) {
                Log.e(TAG,"scanError = " + i);
                if (onScanResultListener != null){
                    onScanResultListener.onScanResult("");
                }
            }
        });
        remoteView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        remoteView.getLayoutParams().width = mScreenWidth;
//        remoteView.getLayoutParams().height = mScreenHeight;
        remoteView.onCreate(null);
        addView(remoteView);
        remoteView.onStart();
        remoteView.onResume();
    }

    private void initMaskParams() {
        scanMaskView = new ScanMaskView(getContext(),2000, Color.GREEN, mScanRect);
        scanMaskView.ShowText = scanType == 1 ? "扫描条形码" : "扫描二维码";
        scanMaskView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scanMaskView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
        addView(scanMaskView);
    }

    public void startScan(){
        Log.e(TAG,"startScan");
    }

    public void stopScan(){
        Log.e(TAG,"stopScan");
        remoteView.setOnResultCallback(null);
        remoteView.setOnErrorCallback(null);
        removeAllViews();
    }

    public interface OnScanResultListener{
        void onScanResult(String scanResult);
    }

    public void setOnScanResultListener(OnScanResultListener onScanResultListener){
        this.onScanResultListener = onScanResultListener;
    }
}
