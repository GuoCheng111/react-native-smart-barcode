package com.reactnativecomponent.barcode.hwscan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.reactnativecomponent.barcode.CaptureView;

import java.util.Map;

public class RCTScanCodeManager extends ViewGroupManager<ScanCodeView> {

    public ScanCodeView scanCodeView;

    @NonNull
    @Override
    public String getName() {
        return "ScanCodeView";
    }

    @NonNull
    @Override
    protected ScanCodeView createViewInstance(@NonNull ThemedReactContext themedReactContext) {
        scanCodeView = new ScanCodeView(themedReactContext.getCurrentActivity(), themedReactContext);
        return scanCodeView;
    }

    @ReactProp(name = "scanType")
    public void setScanType(ScanCodeView scanCodeView, int scanType) {
        scanCodeView.setScanType(scanType);
    }

    @Override
    protected void addEventEmitters(@NonNull final ThemedReactContext reactContext, @NonNull final ScanCodeView view) {
        view.setOnScanResultListener(new ScanCodeView.OnScanResultListener() {
            @Override
            public void onScanResult(String scanResult) {
                reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(new ScanCodeResultEvent(view.getId(),scanCodeView.getScanType(),scanResult));
            }
        });
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String, Object>builder()
                .put("ScanCodeResult", MapBuilder.of("registrationName", "onBarCodeRead"))//registrationName 后的名字,RN中方法也要是这个名字否则不执行
                .build();
    }
}
