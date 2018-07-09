package com.shss.restaurantwaiter.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.object.Settings;
import com.shss.restaurantwaiter.technicalassist.BluetoothService;
import com.shss.restaurantwaiter.technicalassist.sessionmanager;

import static com.shss.restaurantwaiter.activity.CartActivity.DEVICE_NAME;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_CONNECTION_LOST;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_DEVICE_NAME;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_READ;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_STATE_CHANGE;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_TOAST;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_UNABLE_CONNECT;
import static com.shss.restaurantwaiter.activity.CartActivity.MESSAGE_WRITE;
import static com.shss.restaurantwaiter.activity.CartActivity.TOAST;
import static com.shss.restaurantwaiter.technicalassist.stat.mbluetoothService;

public class  Printersetting extends BaseActivity implements View.OnClickListener{

//    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothService mService;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;


    private static BluetoothAdapter mBluetoothAdapter;
//    private static BluetoothService mbluetoothService;
    private String TAG= "printerSetting";
    private CheckBox check;

    private Button bluetoothScan, btnSave, btnCancel;
    private static String text;
    private EditText Shopname,Addressline2,Addressline1,printerFooter, GSTNum;
    private TextView txtview;
    private sessionmanager session;
    private Settings setter;
    private Resources res;
    private EditText ServerIP;
    private TextInputLayout printerLayout;
    private CheckBox DemoMode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        setContentView(R.layout.activity_printersetting);
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        session = new sessionmanager(this);
        setter = new Settings();

        setter = session.getSetting();
        InitView();
    }

    private void InitView() {

        check = (CheckBox) findViewById(R.id.CheckEnablePrinter);
        txtview = (EditText) findViewById(R.id.PrinterIP);
        ServerIP = (EditText) findViewById(R.id.ServerIP);
        printerLayout = (TextInputLayout) findViewById(R.id.printerLayout);
        DemoMode = (CheckBox)findViewById(R.id.CheckEnableDemo);

//        bluetoothScan = (Button) findViewById(R.id.btnBluetoothscan);
        btnCancel = (Button) findViewById(R.id.btnCancel);
//        txtview = (TextView) findViewById(R.id.bluetoothAdress);
        btnSave = (Button) findViewById(R.id.btnSaveSettings);
//        Shopname = (EditText) findViewById(R.id.Shopname);
//        Addressline1 = (EditText) findViewById(R.id.Addressline1);
//        Addressline2 = (EditText) findViewById(R.id.Addressline2);
//        printerFooter = (EditText) findViewById(R.id.printerFooter);
//        GSTNum = (EditText) findViewById(R.id.GSTNum);

        if(setter==null){}
        else {
            check.setChecked(setter.EnablePrinter);
            txtview.setText(setter.IPADDRESS);
            ServerIP.setText(setter.ServerIP);
            DemoMode.setChecked(setter.DemoMode);
            if(check.isChecked())
            {
              printerLayout.setVisibility(View.VISIBLE);

            }

//            txtview.setText(setter.BluetoothAddress);
//            Shopname.setText(setter.ShopName);
//            Addressline1.setText(setter.AddressLine1);
//            Addressline2.setText(setter.AddressLine2);
//            GSTNum.setText(setter.GSTNumber);
//            printerFooter.setText(setter.printerFooter);
        }

        check.setOnClickListener(this);
        btnSave.setOnClickListener(this);
//        bluetoothScan.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();

//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableIntent = new Intent(
//                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
////            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//            // Otherwise, setup the session
//        } else {
//            if (mbluetoothService == null)
//                mbluetoothService = new BluetoothService(this, mHandler);
//            //KeyListenerInit();//监听
//        }
    }


    private static int SendDataString(String data) {


        if(mbluetoothService ==null)
        {
            if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
//            Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT)
//                    .show();
                return -1;
            }
            if (data.length() > 0) {
                try {
                    mbluetoothService.write(data.getBytes());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else
        {
//            mbluetoothService.
        }

        return 0;
    }

    /*
     *SendDataByte
     */
    public int SendDataByte(byte[] data) {

        if(mbluetoothService == null) {

            if(!session.getBluetooth().equals("")) {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(session.getBluetooth());
                mbluetoothService.connect(device);
                mbluetoothService.write(data);
            }
            return -1;
        }
        else
        {
            if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
                Toast.makeText(getApplicationContext(), "Printer Not connected", Toast.LENGTH_SHORT)
                        .show();
                return -1;
            }
            mbluetoothService.write(data);
        }

        return 0;
    }


    private String mConnectedDeviceName;
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
//                    if (DEBUG)
//                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
//                            mTitle.setText(R.string.title_connected_to);
//                            mTitle.append(mConnectedDeviceName);
//                            btnScanButton.setText(getText(R.string.Connecting));
                            break;
                        case BluetoothService.STATE_CONNECTING:
//                            mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
//                            mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:

                    break;
                case MESSAGE_READ:

                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(),
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(),
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(getApplicationContext(), "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(getApplicationContext(), "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (DEBUG)
//            Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE: {
                // When DeviceListActivity returns with a device to connect
                if (resultCode == this.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(
                            DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    if (BluetoothAdapter.checkBluetoothAddress(address)) {
//                        mBluetoothAdapter.getBondedDevices()
                        BluetoothDevice device = mBluetoothAdapter
                                .getRemoteDevice(address);
                        txtview.setText(address);
//                        mOrderSession.saveBluetoothAddress(address);
                        // Attempt to connect to the device
                        mbluetoothService.connect(device);

                    }
                }
                break;
            }

            case REQUEST_ENABLE_BT:{
                // When the request to enable Bluetooth returns
                if (resultCode == this.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session
                    KeyListenerInit();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Leaving as Bluetooth Not enabled",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            }
        }
    }

    private void KeyListenerInit() {
        mbluetoothService = new BluetoothService(this, mHandler);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
//            case R.id.btnBluetoothscan: {
//                Intent serverIntent = new Intent(Printersetting.this, DeviceListActivity.class);
//                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
//                break;
//            }

            case R.id.btnSaveSettings:
            {
                Settings s = new Settings();
                s.EnablePrinter = check.isChecked();
                s.IPADDRESS = txtview.getText().toString();
                s.ServerIP = ServerIP.getText().toString();
                s.DemoMode = DemoMode.isChecked();


//                if(setter==null){
//                    s.ShopName = Shopname.getText().toString();
//                    s.AddressLine1 = Addressline1.getText().toString();
//                    s.AddressLine2 = Addressline2.getText().toString();
//                    s.BluetoothAddress = txtview.getText().toString();
//                    s.DisablePrinter = check.isChecked();
//                    s.GSTNumber = GSTNum.getText().toString();
//                    s.printerFooter = printerFooter.getText().toString();
                Boolean b = session.setSettings(s);
                if(b)
                {
                    Toast.makeText(this,"Successfully saved settings",Toast.LENGTH_SHORT).show();
                }
                break;

            }

            case R.id.btnCancel:{
                openQuitDialog();
                break;
            }

            case R.id.CheckEnablePrinter:{
                if(check.isChecked())
                {
                    printerLayout.setVisibility(View.VISIBLE);
//                    txtview.setVisibility(View.VISIBLE);
                }
            }
        }

    }


//         */
    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                Printersetting.this);
        quitDialog.setTitle(res.getString(R.string.dialog_quit));
        quitDialog.setPositiveButton(res.getString(R.string.quit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton(res.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        quitDialog.show();
    }
}
