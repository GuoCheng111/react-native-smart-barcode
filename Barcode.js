/*
 * A smart barcode scanner for react-native apps
 * https://github.com/react-native-component/react-native-smart-barcode/
 * Released under the MIT license
 * Copyright (c) 2016 react-native-component <moonsunfall@aliyun.com>
 */


import React, {
    Component,
} from 'react'
import PropTypes from 'prop-types';
import {
    View,
    requireNativeComponent,
    NativeModules,
    AppState,
    Platform,
} from 'react-native'

const BarcodeManager = Platform.OS == 'ios' ? NativeModules.Barcode : NativeModules.ScanCodeModule


export default class Barcode extends Component {

    static defaultProps = {
        barCodeTypes: Platform.OS === 'ios' ?  Object.values(BarcodeManager.barCodeTypes) : [],
        scannerRectWidth: 255,
        scannerRectHeight: 255,
        scannerRectTop: 0,
        scannerRectLeft: 0,
        scannerLineInterval: 3000,
        scannerRectCornerColor: `#09BB0D`,
		scanType:0
    }

    static propTypes = {
        ...View.propTypes,
        onBarCodeRead: PropTypes.func.isRequired,
        barCodeTypes: PropTypes.array,
        scannerRectWidth: PropTypes.number,
        scannerRectHeight: PropTypes.number,
        scannerRectTop: PropTypes.number,
        scannerRectLeft: PropTypes.number,
        scannerLineInterval: PropTypes.number,
        scannerRectCornerColor: PropTypes.string,
		scanType:PropTypes.number
    }

    render() {
        return (
            <NativeBarCode
                {...this.props}
            />
        )
    }

    componentDidMount() {
        AppState.addEventListener('change', this._handleAppStateChange);
    }
    componentWillUnmount() {
        AppState.removeEventListener('change', this._handleAppStateChange);
    }

    startScan() {
		BarcodeManager.startSession()
    }

    stopScan() {
		BarcodeManager.stopSession()
    }

    _handleAppStateChange = (currentAppState) => {
        if(currentAppState !== 'active' ) {
            this.stopScan()
        }
        else {
            this.startScan()
        }
    }
}

const NativeBarCode = requireNativeComponent(Platform.OS == 'ios' ? 'RCTBarcode' : 'ScanCodeView', Barcode)
