package com.reactnativecomponent.barcode;

import android.app.Activity;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.reactnativecomponent.barcode.hwscan.RCTScanCodeManager;
import com.reactnativecomponent.barcode.hwscan.RCTScanCodeModule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RCTCapturePackage implements ReactPackage {
//    Activity activity;
//    RCTCaptureModule mModuleInstance;
//    RCTCaptureManager captureManager;

    RCTScanCodeModule scanCodeModule;
    RCTScanCodeManager scanCodeManager;

//    RCTLinearGradientViewManager linearGradientViewManager;

//   public RCTCapturePackage(Activity activity) {
//            this.activity = activity;
//        captureManager = new RCTCaptureManager(activity);
////        linearGradientViewManager = new RCTLinearGradientViewManager(activity);
//    }

    public RCTCapturePackage() {
//        captureManager = new RCTCaptureManager();
        scanCodeManager = new RCTScanCodeManager();
    }


    @Override
        public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
//            mModuleInstance = new RCTCaptureModule(reactApplicationContext,captureManager);
            scanCodeModule = new RCTScanCodeModule(reactApplicationContext,scanCodeManager);
            return Arrays.<NativeModule>asList(
                    scanCodeModule
            );
        }

        // @Override
        // public List<Class<? extends JavaScriptModule>> createJSModules() {
        //     return Collections.emptyList();
        // }

        @Override
        public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
            //noinspection ArraysAsListWithZeroOrOneArgument

//            return Arrays.<ViewManager>asList(captureManager,linearGradientViewManager);
            return Arrays.<ViewManager>asList(scanCodeManager);
        }

    }
