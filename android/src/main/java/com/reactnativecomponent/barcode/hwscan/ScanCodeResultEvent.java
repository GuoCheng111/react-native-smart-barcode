package com.reactnativecomponent.barcode.hwscan;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class ScanCodeResultEvent extends Event<ScanCodeResultEvent> {

    private String scanResult;

    private int scanType;

    public ScanCodeResultEvent(int viewTag, int scanType, String scanResult){
        super(viewTag);
        this.scanType = scanType;
        this.scanResult = scanResult;
    }

    @Override
    public String getEventName() {
        return "ScanCodeResult";
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(),getEventName(),serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        WritableMap data = Arguments.createMap();
        data.putString("code", scanResult);
        data.putInt("type", scanType);
        eventData.putMap("data",data);
        return eventData;
    }
}
