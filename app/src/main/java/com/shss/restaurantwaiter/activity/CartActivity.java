package com.shss.restaurantwaiter.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.adapter.CartAdapter;
import com.shss.restaurantwaiter.adapter.CartAdapter.UpdateQuantityListener;
import com.shss.restaurantwaiter.config.GlobalValue;
import com.shss.restaurantwaiter.json.util.ParserUtility;
import com.shss.restaurantwaiter.modelmanager.ErrorNetworkHandler;
import com.shss.restaurantwaiter.modelmanager.ModelManager;
import com.shss.restaurantwaiter.modelmanager.ModelManagerListener;
import com.shss.restaurantwaiter.object.CartInfo;
import com.shss.restaurantwaiter.object.Settings;
import com.shss.restaurantwaiter.object.TablesInfo;
import com.shss.restaurantwaiter.technicalassist.BluetoothService;
import com.shss.restaurantwaiter.technicalassist.sessionmanager;
import com.shss.restaurantwaiter.widget.AutoBgButton;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.shss.restaurantwaiter.technicalassist.stat.mbluetoothService;

public class CartActivity extends BaseActivity implements OnClickListener {


    /******************************************************************************************************/
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;
    /*******************************************************************************************************/
    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CHOSE_BMP = 3;
    private static final int REQUEST_CAMER = 4;

    private AutoBgButton btnDelete, btnOrder, btnBackCart, btnHome, btnAcc;
    private ListView lsvCart;
    private TextView lblTotal, lblTitleCart, lblnodata;
    private CartAdapter cartAdapter;
    private double TotalPrice;
    private String IDTable, Status;
    public static ArrayList<CartInfo> list;
    public static CartActivity self = null;
    private BluetoothAdapter mBluetoothAdapter;
    private sessionmanager session;
    private StringBuilder bl;
    private Settings setter;
    private int count;
    private AutoBgButton btnPrint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.page_cart);
        self = this;
        if (getIntent().hasExtra("IDTable")) {
            IDTable = getIntent().getStringExtra("IDTable");
        }
        if (getIntent().hasExtra("Status")) {
            Status = getIntent().getStringExtra("Status");
        }
        initUI();
        initControl();
        initListCart();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        session.get

//        if(!session.getPrinter()) {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            // If Bluetooth is not on, request that it be enabled.
//            // setupChat() will then be called during onActivityResult
//            if (!mBluetoothAdapter.isEnabled()) {
//                Intent enableIntent = new Intent(
//                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//                // Otherwise, setup the session
//            } else if (mbluetoothService == null) {
//                mbluetoothService = new BluetoothService(this, mHandler);
//                initPrinter();
//            } else
//                initPrinter();
//        }
    }


    private String mConnectedDeviceName;
    @SuppressLint("HandlerLeak")
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
                    Toast.makeText(CartActivity.this,
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(CartActivity.this,
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(CartActivity.this, "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(CartActivity.this, "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initUI() {
        btnBackCart = (AutoBgButton) findViewById(R.id.btnBackCart);
        lblTitleCart = (TextView) findViewById(R.id.lblTitleCart);
        lblTitleCart.setText(getString(R.string.table) + " " + IDTable);
        btnDelete = (AutoBgButton) findViewById(R.id.btnDelete);
        btnAcc = (AutoBgButton) findViewById(R.id.btnAccount);
        btnOrder = (AutoBgButton) findViewById(R.id.btnOrder);
        lsvCart = (ListView) findViewById(R.id.lsvCart);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        lblnodata = (TextView) findViewById(R.id.lbl_Cartnodata);
        btnHome = (AutoBgButton) findViewById(R.id.btn_home);
//        btnPrint = (AutoBgButton) findViewById(R.id.btnPrint);
    }

    private void initControl() {
        btnBackCart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        btnHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clearOtherActivities();

            }
        });

//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        session = new sessionmanager(this);

        btnDelete.setOnClickListener(this);
        btnAcc.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
//        btnPrint.setOnClickListener(this);
        bl = new StringBuilder();
        setter = new Settings();
        setter = session.getSetting();
        count=0;

    }

    private void clearOtherActivities() {
        if (CategoryActivity.self != null) {
            CategoryActivity.self.finish();
            CategoryActivity.self = null;
        }
        if (ProductActivity.self != null) {
            ProductActivity.self.finish();
            ProductActivity.self = null;
        }
        // clear current screen
        onBackPressed();
        CartActivity.self = null;
    }

    private void initListCart() {
        list = new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this, list,
                new UpdateQuantityListener() {
                    @Override
                    public void onUpdate(boolean isPlus, int index) {
                        if (isPlus) {
                            list.get(index).setNumberCart(
                                    list.get(index).getNumberCart() + 1);
                            GlobalValue.databaseUtility.updateCart(CartActivity.this, list
                                    .get(index).getNumberCart(), list
                                    .get(index).getId(), list.get(index)
                                    .getIdTable());

                        } else {
                            if (list.get(index).getNumberCart() > 0) {
                                list.get(index).setNumberCart(
                                        list.get(index).getNumberCart() - 1);
                                GlobalValue.databaseUtility.updateCart(CartActivity.this,
                                        list.get(index).getNumberCart(), list
                                                .get(index).getId(),
                                        list.get(index).getIdTable());
                            } else {
                                GlobalValue.databaseUtility.deleteCart(CartActivity.this,
                                        list.get(index).getNameCart());
                            }
                        }

                        cartAdapter.notifyDataSetChanged();
                        updateTotal();
                    }
                });
        lsvCart.setAdapter(cartAdapter);

    }

    private void initData() {
        getListCart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                onClickDelete();
                break;
            case R.id.btnAccount:
                Bundle b = new Bundle();
                b.putString("IDTable", IDTable);
                gotoActivity(CartActivity.this, PaymentActivity.class, b);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_right);
                break;

            case R.id.btnOrder:
                onClickOrder();
//                formReceipt();
                break;

//            case R.id.btnPrint:
//                formReceipt();
//                break;

        }
    }


    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        list.clear();
        getListCart();
        //Refresh your stuff here
    }





    private void onClickDelete() {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isTrue()) {
                    GlobalValue.databaseUtility.deleteCartId(CartActivity.this, list.get(i)
                            .getId(), IDTable);
                    list.remove(i);
                }
            }
            cartAdapter.notifyDataSetChanged();
            updateTotal();
        } else {
            Toast.makeText(self, "Your cart is empty!", Toast.LENGTH_LONG).show();
        }
    }

    private void onClickOrder() {
        if (list.size() == 0) {
            Toast.makeText(self, "Please select your menus!", Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTableId(IDTable);
        }
        final String json = ParserUtility.putJsonCart(list);
        Log.e("", "jsonput: " + json);

        ModelManager.putJsonCart(self, json, true, new ModelManagerListener() {

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onSuccess(Object object) {

                Log.e(TAG, "onSuccess: " + object.toString());

                ModelManager.UpdateTable(self, IDTable, TablesInfo.TABLE_USING + "", true,
                        new ModelManagerListener() {

                            @Override
                            public void onSuccess(Object object) {
                                // TODO Auto-generated method stub
//                                formReceipt();
//                                if(session.getSetting().EnablePrinter)
//                                    formReceipt();
//                                PrintBluetoothReceipt(json);

                                if(session.getSetting().EnablePrinter) {
                                    if (formReceipt()) {
                                        Toast.makeText(self, "Sending your order successfully!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(self, "Couldn't send the order, Please check availibilty of printer",
                                            Toast.LENGTH_SHORT).show();
                                }

                                GlobalValue.databaseUtility.deleteTableCart(self, IDTable);
                                clearOtherActivities();
                            }

                            @Override
                            public void onError(VolleyError volleyError) {
                                // TODO Auto-generated method stub
                                ErrorNetworkHandler.processError(CartActivity.this, volleyError);
                            }
                        });

            }
        });////////////////////

    }

    private Boolean formReceipt() {

        Boolean b = false;
        SimpleDateFormat timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        String Timestamp1 = timeStamp.format(Calendar.getInstance().getTime());
        bl = new StringBuilder();
        bl.append("Table Number: "+ list.get(0).getTableId()+ "\n");
        if(GlobalValue.preferences.getUserInfo()==null)
        {}
        else
        bl.append("Waiter: "+ GlobalValue.preferences.getUserInfo().getUserName()+"\n");
//        bl.append("Waiter:")
        bl.append(Timestamp1+"\n");
        bl.append("\n");
        bl.append("Name         Qty Note"+ "\n");
        bl.append("\n");
        for (CartInfo c: list) {
            if(c.getNote().equals("1")){c.setNote(" ");}
            bl.append(padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getNote(),12) +"\n");
        }


//        bl.append("*end of order*"+"\n");
//        bl.append("\n");
//        bl.append("\n");
//        bl.append("...");
//        bl.append(PrinterCommand.POS_Set_LF().toString());

        try
        {
//            if(session.getSetting().EnablePrinter) {

//                Socket sock = new Socket(session.getSetting().IPADDRESS, 9100);
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress(session.getSetting().IPADDRESS, 9100), 15000);
                PrintWriter oStream = new PrintWriter(sock.getOutputStream());
                oStream.write(bl.toString());
                oStream.println("\n");
                oStream.println("\n");
                oStream.close();
                sock.close();
                b = true;
//            }
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return b;
    }

//    private void PrintBluetoothReceipt(String JSON) {
//
////        Printersetting p = new Printersetting();
//        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
//        SendDataByte(Command.ESC_Init);
//        Command.ESC_Align[2] = 0x01;
//        SendDataByte(Command.ESC_Align);
//        Command.GS_ExclamationMark[2] = 0x10;
//        SendDataByte(Command.GS_ExclamationMark);
//
//
//        SendDataByte(("Table Number: "+ list.get(0).getTableId()+ "\n").getBytes());
//        Command.GS_ExclamationMark[2] = 0x00;
//        SendDataByte(Command.GS_ExclamationMark);
////        SendDataByte();
//
//        SendDataByte((timeStamp+"\n").getBytes());
////        SendDataByte()
////        SendDataByte()
//        Command.ESC_Align[2] = 0x00;
//        SendDataByte(Command.ESC_Align);
//
//
//        SendDataByte(("Name         Qty Note"+ "\n").getBytes());
//        SendDataByte(LF);
//        for (CartInfo c: list) {
//
//            bl.append(padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getNote(),12) +"\n");
//            SendDataByte((padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getNote(),12) +"\n").getBytes());
//        }
//
//
//        SendDataByte(LF);
////        bl.append("\n");
//        SendDataByte(LF);
////        bl.append("\n");
//        SendDataByte(LF);
////        Printersetting.SendDataByte()
//
////        SendDataByte(("Invoice Number:" + regis.getCurrentSale().getId() + "\n").getBytes());
////        SendDataByte(("Date : " + DateTimeStrategy.getCurrentTime() + "\n").getBytes());
////        SendDataByte(("Customer:" + buyerName + "\n").getBytes());
////        Printersetting.SendDataByte(JSON.getBytes());
//    }
//
    private String padTextProd(String product_name, int Maxnum) {

        StringBuffer padded = new StringBuffer(product_name);

//        Strings.padEnd("string", 10, ' ');
        while (padded.length() < Maxnum)
        {
            padded.append(" ");
        }

        padded.setLength(Maxnum);
        return padded.toString();

    }
//
//    public int SendDataByte(byte[] data) {
//
//        if(mbluetoothService==null){
//            return -1;
//        }
//        else {
//            if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
//                if(count<1) {
//                    Toast.makeText(getApplicationContext(), "Printer Not connected", Toast.LENGTH_SHORT)
//                            .show();
//                    count++;
//                }
//                return -1;
//            }
//            mbluetoothService.write(data);
//        }
////        }
//
//        return 0;
//    }


    private void initPrinter() {

//        Logger.i(this,TAG,"InitPrinter: Bluetooth Adapter is intializing");

        String BlueAddress="";
        if(session.getSetting()==null){}
        else
        {

//            BlueAddress = session.getSetting().BluetoothAddress;
        }

        if (BluetoothAdapter.checkBluetoothAddress(BlueAddress)) {
            BluetoothDevice device = mBluetoothAdapter
                    .getRemoteDevice(BlueAddress);
            if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
                // Attempt to connect to the device
//				mbluetoothService.stop();
                mbluetoothService.connect(device);
            }
        }
    }


    private void updateTotal() {
        TotalPrice = 0;
        for (CartInfo element : list) {
            TotalPrice += element.getNumberCart() * element.getPrice();
        }
        lblTotal.setText(getString(R.string.total) + TotalPrice + ""
                + getString(R.string.dola));
    }

    private void getListCart() {
        list.addAll(GlobalValue.databaseUtility.getListCart(CartActivity.this, IDTable));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTableId(IDTable);
        }
        if (list.size() > 0) {
            lblnodata.setVisibility(View.GONE);
            lsvCart.setVisibility(View.VISIBLE);
        } else {
            lblnodata.setVisibility(View.VISIBLE);
            lsvCart.setVisibility(View.GONE);
        }

        updateTotal();
        cartAdapter.notifyDataSetChanged();
    }

}
