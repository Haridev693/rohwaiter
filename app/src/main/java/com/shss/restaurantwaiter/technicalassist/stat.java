package com.shss.restaurantwaiter.technicalassist;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Lenovo on 02/16/18.
 */

public class stat  {

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;


    private BluetoothAdapter mBluetoothAdapter;
    public static BluetoothService mbluetoothService;

//    public void stat() {
////        super.onStart();
//
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        // If Bluetooth is not on, request that it be enabled.
//        // setupChat() will then be called during onActivityResult
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(
//                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//            // Otherwise, setup the session
//        } else {
////            if (mbluetoothService == null)
////                mbluetoothService = new BluetoothService(this,);
//            //KeyListenerInit();//监听
//        }
//    }
//
//
//    private void SendDataString(String data) {
//
//        if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
//            Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
//        if (data.length() > 0) {
//            try {
//                mbluetoothService.write(data.getBytes());
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /*
//     *SendDataByte
//     */
//    private void SendDataByte(byte[] data) {
//
//        if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
//            Toast.makeText(getApplicationContext(), "Printer Not connected", Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
//        mbluetoothService.write(data);
//    }
}
