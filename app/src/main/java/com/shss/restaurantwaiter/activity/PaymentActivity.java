package com.shss.restaurantwaiter.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.shss.restaurantwaiter.BaseActivity;
import com.shss.restaurantwaiter.R;
import com.shss.restaurantwaiter.adapter.AccountAdapter;
import com.shss.restaurantwaiter.commands.Command;
import com.shss.restaurantwaiter.commands.PrinterCommand;
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
import com.shss.restaurantwaiter.utility.DialogUtility;
import com.shss.restaurantwaiter.utility.GlobalVariable;
import com.shss.restaurantwaiter.widget.AutoBgButton;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

public class PaymentActivity extends BaseActivity implements OnClickListener {
    private ListView lsvAccount;
    private TextView lblTitleAccount, lblSubTotal, lblPercent, lblTotal,
            lblTitleAcc;
    private String name, price, number;
    private AccountAdapter accountAdapter;
    private double Total = 0;
    private String IDTable;
    private AutoBgButton btnHomeAcc, btnBack, btnSendCart;
    private ArrayList<CartInfo> list;
    private sessionmanager sessionP;
    private StringBuilder bl;
    private BluetoothAdapter mBluetoothAdapter;
    private int count;
    private Settings setter;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private TextView txtIGST;
    private TextView txtCGST;
    private TextView lblCGST;
    private TextView lblIGST;
    private TextView txtSubTotal;
    private TextView txtTotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stubỎ
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_account);
        if (getIntent().hasExtra("IDTable")) {
            IDTable = getIntent().getStringExtra("IDTable");
        }
        initUI();
        initControl();
        initData();
    }

    private void initUI() {
        // initHeaderUI();
        // hideHeaderButton(false, true);
        lblTitleAcc = (TextView) findViewById(R.id.lblTittleAcc);
        btnSendCart = (AutoBgButton) findViewById(R.id.btnSendCart);
        lblTitleAcc.setText("Table" + " " + IDTable);
        lsvAccount = (ListView) findViewById(R.id.lsvAccount);
        lblTitleAccount = (TextView) findViewById(R.id.lblTitleAccount);
        lblSubTotal = (TextView) findViewById(R.id.lblSubTotal);
        btnBack = (AutoBgButton) findViewById(R.id.btnBackAcc);
        btnHomeAcc = (AutoBgButton) findViewById(R.id.btn_homeAcc);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        lblCGST = (TextView)findViewById(R.id.lblCGST);
        txtCGST = (TextView)findViewById(R.id.txtCGST);
        lblIGST = (TextView)findViewById(R.id.lblIGST);
        txtIGST = (TextView)findViewById(R.id.txtIGST);
        txtSubTotal = (TextView)findViewById(R.id.txtSubTotal);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
    }

    private void initControl() {
        btnBack.setOnClickListener(this);
        btnHomeAcc.setOnClickListener(this);
        list = new ArrayList<CartInfo>();
        btnSendCart.setOnClickListener(this);
        bl = new StringBuilder();
        sessionP = new sessionmanager(this);
        setter = new Settings();
        setter = sessionP.getSetting();
        count =0;
    }


    @Override
    protected void onStart() {
        super.onStart();
//        session.get
//
//        if(!sessionP.getPrinter()) {
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


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//		for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//			fragment.onActivityResult(requestCode, resultCode, data);
//		}

        //if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_ENABLE_BT: {
//                Logger.i(this,"MainActivity", "requestCode==REQUEST_ENABLE_BT");
                // When the request to enable Bluetooth returns
                if (resultCode == AppCompatActivity.RESULT_OK) {
//                    Logger.v(this, "MainActivity", "onActivityResult: resultCode==OK");
                    // Bluetooth is now enabled, so set up a chat session
//                    Logger.v(this,"MainActivity", "onActivityResult: starting setupComm()...");

                    mbluetoothService = new BluetoothService(this, mHandler);
                    initPrinter();
                }
            }
        }
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
                    Toast.makeText(PaymentActivity.this,
                            "Connected to " + mConnectedDeviceName,
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(PaymentActivity.this,
                            msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(PaymentActivity.this, "Device connection was lost",
                            Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(PaymentActivity.this, "Unable to connect device",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    private void initPrinter() {

//        Logger.i(this,TAG,"InitPrinter: Bluetooth Adapter is intializing");

//        String BlueAddress="";
//        if(sessionP.getSetting()==null){}
//        else
//        {
//            BlueAddress = sessionP.getSetting().BluetoothAddress;
//        }
//
//        if (BluetoothAdapter.checkBluetoothAddress(BlueAddress)) {
//            BluetoothDevice device = mBluetoothAdapter
//                    .getRemoteDevice(BlueAddress);
//            if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
//                // Attempt to connect to the device
////				mbluetoothService.stop();
//                mbluetoothService.connect(device);
//            }
//        }
    }

    private void initData() {
        ModelManager.getShowCart(PaymentActivity.this, IDTable, true,
                new ModelManagerListener() {

                    @Override
                    public void onSuccess(Object json) {
                        // TODO Auto-generated method stub
                        list = new ArrayList<CartInfo>();
                        list = ParserUtility.parseShowCart(json.toString());
                        for (CartInfo item : list) {
                            Total += item.getPrice();
                        }
                        if(GlobalVariable.taxAmount!=0)
                        {
                            double taxData = Total * GlobalVariable.taxAmount /100;
                            Log.w("UserTaxData",""+taxData);
                            double halfData= taxData /2;
                            txtCGST.setText(halfData+"");
                            txtIGST.setText(halfData+"");
//                            txtTotal.setText("Total:");
                            txtTotal.setText("");
                            txtTotal.setText( new DecimalFormat("##.##").format(Total + taxData)+"");

                            txtIGST.setVisibility(View.VISIBLE);
                            txtCGST.setVisibility(View.VISIBLE);
                            lblCGST.setVisibility(View.VISIBLE);
                            lblIGST.setVisibility(View.VISIBLE);

                        }
                        else
                        {
//                            txtTotal.setText("Total:");
                            txtTotal.setText("");
                            txtTotal.setText(Total+"");
                        }
                        txtSubTotal.setText(Total +"");
//                        lblPercent.setText("Discount 10% : " + Total / 10
////                                + getString(R.string.dola));
//                        lblTotal.setText(getString(R.string.total)
//                                .toUpperCase()
//                                + " "
//                                + ((Total) - (Total / 10))
//                                + getString(R.string.dola));

                        lblTotal.setText(getString(R.string.total)
                                .toUpperCase()
                                + " ");
//                                + (Total)
//                                + getString(R.string.dola));

                        accountAdapter = new AccountAdapter(
                                PaymentActivity.this, list);
                        accountAdapter.notifyDataSetChanged();
                        lsvAccount.setAdapter(accountAdapter);
                        Log.w("TaxAmount", GlobalVariable.taxAmount+"");
                    }

                    @Override
                    public void onError(VolleyError error) {
                        // TODO Auto-generated method stub
                        ErrorNetworkHandler.processError(PaymentActivity.this, error);
                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_homeAcc:
                onClickHome();
                break;

            case R.id.btnBackAcc:
                onBackPressed();
                break;

            case R.id.btnSendCart:
                onClickSendCart();
                break;
        }
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
        if (CartActivity.self != null) {
            CartActivity.self.finish();
            CartActivity.self = null;
        }
        // clear current screen
        onBackPressed();
    }

    private void onClickHome() {
        clearOtherActivities();
    }

    private void onClickSendCart() {
        if (list.size() > 0) {
            ModelManager.putBooking(self, IDTable, list.get(0).getCARTID(),
                    true, new ModelManagerListener() {

                        @Override
                        public void onSuccess(Object json) {
                            // TODO Auto-generated method stub

//                            formReceipt();
                            count =0;

                            if(GlobalVariable.restAddress.BillPrint)
                            {
                                printWIFIReceipt();
                            }
//                            if()
//                            if(!sessionP.getPrinter())
//                            printBluetoothReceipt();
                            GlobalValue.databaseUtility.deleteTableCart(
                                    PaymentActivity.this, IDTable);
                            alert(PaymentActivity.this,
                                    R.string.message_send_cart_success);
                            GlobalValue.timeStampForTable.remove(GlobalValue.preferences.getTableClick());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            // TODO Auto-generated method stub
                            ErrorNetworkHandler.processError(self, error);
                        }
                    });
        } else {
            Toast.makeText(self, "Your cart is empty. Please order menus.", Toast.LENGTH_LONG).show();
        }

    }

    private void printBluetoothReceipt() {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        SendDataByte(Command.ESC_Init);
        Command.ESC_Align[2] = 0x01;
        SendDataByte(Command.ESC_Align);
        Command.GS_ExclamationMark[2] = 0x10;
        SendDataByte(Command.GS_ExclamationMark);


        SendDataByte(PrinterCommand.POS_Set_UnderLine(2));
        SendDataByte((GlobalVariable.restAddress.Name+"").getBytes());
//        SendDataByte((setter.ShopName+"\n").getBytes());
        SendDataByte(PrinterCommand.POS_Set_UnderLine(0));
//        SendDataByte((setter.AddressLine1+"\n").getBytes());
        SendDataByte((GlobalVariable.restAddress.AddressLine1+"").getBytes());
        SendDataByte((GlobalVariable.restAddress.AddressLine2+"").getBytes());
//        SendDataByte((setter.AddressLine2+"\n").getBytes());
        Command.GS_ExclamationMark[2] = 0x00;
        SendDataByte(Command.GS_ExclamationMark);
        SendDataByte(("Table Number: "+ list.get(0).getTableId()+ "\n").getBytes());
        SendDataByte("\n".getBytes());

        Command.ESC_Align[2] = 0x00;
        SendDataByte(Command.ESC_Align);
        SendDataByte(("Name         Qty Price  Total"+ "\n").getBytes());
        SendDataByte("\n".getBytes());

        for (CartInfo c: list) {
            SendDataByte((padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getPrice()+"",7)+ padTextProd(String.valueOf(c.getNumberCart()*c.getPrice()),7) +"\n").getBytes());
        }

        SendDataByte("\n".getBytes());
        SendDataByte("\n".getBytes());

        SendDataByte(("            TOTAL :  "+ Total+"\n").getBytes());

        SendDataByte("\n".getBytes());
//        SendDataByte("\n".getBytes());
//        SendDataByte("\n".getBytes());

//        SendDataByte(setter.printerFooter.getBytes());
        SendDataByte("Please visit again".getBytes());

        SendDataByte("\n".getBytes());
        SendDataByte("\n".getBytes());

    }




    private void printWIFIReceipt() {

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
//        SendDataByte(Command.ESC_Init);
//        Command.ESC_Align[2] = 0x01;
//        SendDataByte(Command.ESC_Align);
//        Command.GS_ExclamationMark[2] = 0x10;
//        SendDataByte(Command.GS_ExclamationMark);

        bl = new StringBuilder();

//        SendDataByte(PrinterCommand.POS_Set_UnderLine(2));
        bl.append("      "+GlobalVariable.restAddress.Name+"\n");//.getBytes());
//        SendDataByte((setter.ShopName+"\n").getBytes());
//        SendDataByte(PrinterCommand.POS_Set_UnderLine(0));
//        SendDataByte((setter.AddressLine1+"\n").getBytes());
        bl.append(GlobalVariable.restAddress.AddressLine1+"\n");
        bl.append(GlobalVariable.restAddress.AddressLine2+"\n");//.getBytes());

        bl.append(timeStamp+"\n");
        bl.append("-------------------------"+"\n");
//        SendDataByte((setter.AddressLine2+"\n").getBytes());
//        Command.GS_ExclamationMark[2] = 0x00;
//        SendDataByte(Command.GS_ExclamationMark);
        bl.append("Table:"+ list.get(0).getTableId()+ " | Waiter:"+ GlobalValue.preferences.getUserInfo().getUserName()+"\n");//.getBytes());
        bl.append("\n");

//        Command.ESC_Align[2] = 0x00;
//        SendDataByte(Command.ESC_Align);
        bl.append("Name         Qty Price  Total"+ "\n");
        bl.append("\n");

        for (CartInfo c: list) {
            bl.append(padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getPrice()+"",7)+ padTextProd(String.valueOf(c.getNumberCart()*c.getPrice()),7) +"\n");
        }

        bl.append("\n");
        bl.append("SUB TOTAL : "+ txtSubTotal.getText()+"\n");

        bl.append("\n");
        if(GlobalVariable.taxAmount!=0) {
            bl.append("Tax" + "\n");
            bl.append("CGST      : " + txtCGST.getText().toString() + "\n");
            bl.append("SGST      : " + txtIGST.getText().toString() + "\n");
        }

        bl.append("TOTAL     : "+ txtTotal.getText().toString()+"\n");

        bl.append(GlobalVariable.restAddress.Footer1+"\n");
        bl.append(GlobalVariable.restAddress.Footer2+"\n");
        bl.append(GlobalVariable.restAddress.Footer3+"\n");

        try
        {
//            if(session.getSetting().EnablePrinter) {

//                Socket sock = new Socket(session.getSetting().IPADDRESS, 9100);
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress(GlobalVariable.restAddress.PrinterIP, 9100), 15000);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            oStream.write(bl.toString());
            oStream.println("\n");
            oStream.println("\n");
            oStream.close();
            sock.close();
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

//        return b;
//        SendDataByte("\n".getBytes());
//        SendDataByte("\n".getBytes());

//        SendDataByte(setter.printerFooter.getBytes());

    }


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

    public int SendDataByte(byte[] data) {


        if(mbluetoothService==null){
            return -1;
        }
        else {
            if (mbluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
                if(count<1) {
                    Toast.makeText(getApplicationContext(), "Printer Not connected", Toast.LENGTH_SHORT)
                            .show();
                    count++;
                }
                return -1;
            }
            mbluetoothService.write(data);
        }
//        }

        return 0;
    }

    private void formReceipt() {
        bl = new StringBuilder();

//        b1.

        for (CartInfo c: list)
        {
            bl.append(padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getPrice()+"",10) +"\n");
//            SendDataByte((padTextProd(c.getNameCart(),12) +" "+ padTextProd(c.getNumberCart()+"",3) + " "+ padTextProd(c.getPrice()+"",10) +"\n").getBytes());
        }
        bl.append(lblTotal.getText().toString()+"\n");

    }

    private void alert(final Context context, int messageId) {

        DialogUtility.showOkDialog(context, messageId, R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ModelManager.UpdateTable(PaymentActivity.this, IDTable, TablesInfo.TABLE_FREE + "", true,
                        new ModelManagerListener() {

                            @Override
                            public void onSuccess(Object json) {
                                // TODO Auto-generated method stub
                                clearOtherActivities();
                            }

                            @Override
                            public void onError(VolleyError error) {
                                // TODO Auto-generated method stub
                                ErrorNetworkHandler.processError(self, error);
                            }
                        });
            }
        });
    }
}
