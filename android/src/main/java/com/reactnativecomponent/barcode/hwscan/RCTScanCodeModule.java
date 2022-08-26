package com.reactnativecomponent.barcode.hwscan;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RCTScanCodeModule extends ReactContextBaseJavaModule {

    private RCTScanCodeManager scanCodeManager;

    public RCTScanCodeModule(@NonNull ReactApplicationContext reactContext, RCTScanCodeManager scanCodeManager) {
        super(reactContext);
        this.scanCodeManager = scanCodeManager;
    }

    @NonNull
    @Override
    public String getName() {
        return "ScanCodeModule";
    }

    @ReactMethod
    public void startSession() {
        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scanCodeManager.scanCodeView.startScan();
            }
        });
    }

    @ReactMethod
    public void stopSession() {
        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scanCodeManager.scanCodeView.stopScan();
            }
        });
    }
}
