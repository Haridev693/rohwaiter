package com.shss.restaurantwaiter.technicalassist;

/**
 * Created by Lenovo on 11/23/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.shss.restaurantwaiter.config.WebServiceConfig;
import com.shss.restaurantwaiter.object.Settings;


/**
 * Created by Lenovo on 11/1/2017.
 */

public class sessionmanager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "swachm";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_USERID = "UserID";//USER_ID

    public static final String PrinterAddress = "PrinterAddr";

    public static final String EnablePrinter = "EnablePrint";
    public static final String IPAddress = "IPAddr";

    public static final String ServerIP = "ServerIP";
    public static final String DemoMode = "DemoMode";
//    ServerIP

    public static final String ShopName = "ShopName";

    public static final String AddressLine1 = "AddrLine1";

    public static final String AddressLine2 = "AddrLine2";

    public static final String PrintFooter = "PrintFooter";

    public static final String GSTNum = "GSTNumber";

    // Constructor
    public sessionmanager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email, String UserID){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_USERID, UserID);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
//    public void checkLogin(){
//        // Check login status
//        if(!this.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//        }
//
//    }


//    public void createChainSession(chainsettings c){
//        // Storing login value as TRUE
//        editor.putBoolean(Show_Reports, c.showReports);
//
//        // Storing email in pref
//        editor.putBoolean(Show_Tax, c.showTax);
//
//        editor.putBoolean(Show_Users, c.showUsers);
//
//        editor.putBoolean(DB_Backup, c.dbBackup);
//
//        editor.putBoolean(Enable_Printer, c.enablePrinter);
//
//        // commit changes
//        editor.commit();
//    }

    public Boolean setSettings(Settings s)
    {
//        editor.putString(PrinterAddress,s.BluetoothAddress);
//        editor.putBoolean(EnablePrinter,s.EnablePrinter);
//        editor.putString(IPAddress,s.IPADDRESS);
        editor.putString(ServerIP,s.ServerIP);
        editor.putBoolean(DemoMode,s.DemoMode);

//        if(s.ServerIP)
        WebServiceConfig.Serverbackendlink = WebServiceConfig.OrigIP + s.ServerIP;
//        WebServiceConfig.Serverbackendlink = WebServiceConfig.Serverbackendlink+"/"+ s.ServerIP;
//        WebServiceConfig.Serverbackendlink = ServerIP;

        if(s.DemoMode)
        {
            WebServiceConfig.Serverbackendlink= "http://shs2apicalls.pe.hu/backend";
            WebServiceConfig.Serverbackendlink = "http://192.168.1.101/rohadmin2_79";
        }
//        editor.putString(ShopName,s.ShopName);
//        editor.putString(AddressLine1,s.AddressLine1);
//        editor.putString(AddressLine2,s.AddressLine2);
//        editor.putString(GSTNum,s.GSTNumber);
//        editor.putString(PrintFooter,s.printerFooter);
//        editor.putString()
        return editor.commit();
//        return true;
    }

    public void writePrinter(String PrinterAddr, Boolean EnablePRint){

        editor.putString(PrinterAddress,PrinterAddr);
//      editor.putBoolean(EnablePrinter,EnablePRint);
//      editor.putInt("userpin", u.userpin);
        editor.commit();
    }


    public Settings getSetting()
    {
        Settings s = new Settings();

//        s.BluetoothAddress = pref.getString(PrinterAddress,"");
//        s.printerFooter = pref.getString(PrintFooter,"");
//        s.GSTNumber = pref.getString(GSTNum,"");
//        s.EnablePrinter = pref.getBoolean(EnablePrinter,false);
//        s.IPADDRESS = pref.getString(IPAddress,"");
        s.ServerIP = pref.getString(ServerIP,"");
        s.DemoMode = pref.getBoolean(DemoMode,false);

        WebServiceConfig.Serverbackendlink = WebServiceConfig.OrigIP + s.ServerIP;

        if(s.DemoMode)
        {
            WebServiceConfig.Serverbackendlink="http://shs2apicalls.pe.hu/backend";
                        WebServiceConfig.Serverbackendlink = "http://192.168.1.101/rohadmin2_79";
        }


//        s.AddressLine1 = pref.getString(AddressLine1,"");
//        s.AddressLine2 = pref.getString(AddressLine2,"");
//        s.ShopName = pref.getString(ShopName,"");

        return s;
    }

//    public Boolean getPrinter()
//    {
////        Boolean b = pref.getBoolean(DisablePrinter,false);
////        return b;
//    }



    public String getBluetooth()
    {
        return pref.getString("BluetoothAddress","");
    }


}

