    package com.shss.restaurantwaiter.activity;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.StrictMode;
    import android.util.Log;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.view.Window;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.android.volley.VolleyError;
    import com.shss.restaurantwaiter.BaseActivity;
    import com.shss.restaurantwaiter.R;
    import com.shss.restaurantwaiter.config.GlobalValue;
    import com.shss.restaurantwaiter.config.WebServiceConfig;
    import com.shss.restaurantwaiter.database.DatabaseUtility;
    import com.shss.restaurantwaiter.json.util.ParserUtility;
    import com.shss.restaurantwaiter.modelmanager.ErrorNetworkHandler;
    import com.shss.restaurantwaiter.modelmanager.ModelManager;
    import com.shss.restaurantwaiter.modelmanager.ModelManagerListener;
    import com.shss.restaurantwaiter.object.PickerListener;
    import com.shss.restaurantwaiter.object.UserInfo;
    import com.shss.restaurantwaiter.technicalassist.sessionmanager;
    import com.shss.restaurantwaiter.utility.DialogUtility;
    import com.shss.restaurantwaiter.utility.StringUtility;
    import com.shss.restaurantwaiter.widget.AutoBgButton;
    import com.shss.restaurantwaiter.widget.picker.view.SimpleDialogPicker;

    import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private AutoBgButton btnForward, btnImport, btnAbout;
    private EditText txtPassWord, txtUser;
    private ArrayList<UserInfo> arrUser;
    private String partenaireId;
    private AutoBgButton btnSettings;

    private sessionmanager session2;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        context = getApplicationContext();

        session2 = new sessionmanager(getApplicationContext());

        if(session2.getSetting()==null)
        {

        }
        else {
            WebServiceConfig.Serverbackendlink = session2.getSetting().ServerIP;
        }

        if(WebServiceConfig.Serverbackendlink.equals(""))
        {
            WebServiceConfig.Serverbackendlink = context.getString(R.string.server_backend_link);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GlobalValue.constructor(this);
        //  Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        initUI();
        initControl();
        if (GlobalValue.databaseUtility == null) {
            GlobalValue.databaseUtility = new DatabaseUtility(this);
            Log.e("Database", "database not null");
        }
    }

    private void initUI() {

        btnForward = (AutoBgButton) findViewById(R.id.btnForward);
        btnAbout = (AutoBgButton) findViewById(R.id.btnAbout);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassWord = (EditText) findViewById(R.id.txtPassWord);
        btnSettings = (AutoBgButton) findViewById(R.id.btnSettings);
    }

    private void initControl() {
        ModelManager.getLogin(self, true, new ModelManagerListener() {

            @Override
            public void onSuccess(Object json) {
                arrUser = ParserUtility.parseListLogin(json.toString());
            }

            @Override
            public void onError(VolleyError error) {

                ErrorNetworkHandler.processError(LoginActivity.this, error);
            }
        });
        btnForward.setOnClickListener(this);
        txtUser.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnAbout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                gotoActivity(LoginActivity.this, AboutActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnForward:
                onClickForward();
                break;
            case R.id.txtUser:
                if (arrUser != null) {
                    userPicker();
                } else {
                    Toast.makeText(this, getString(R.string.not_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSettings:
            {
                Intent i  = new Intent(LoginActivity.this, Printersetting.class);
                startActivity(i);
//                Intent i = new Inten
                break;
            }
        }
    }

    @SuppressLint("ShowToast")
    private void onClickForward() {
        if (StringUtility.isEmpty(txtUser)
                || StringUtility.isEmpty(txtPassWord)) {
            // User name or password is empty.
            showDialog(
                    R.string.message_please_input_your_username_or_password_before_login,
                    R.string.button_ok, null);
        } else {

            String UserName = txtUser.getText().toString().trim();
            String PassWord = txtPassWord.getText().toString().trim();
            boolean check = false;
            int ii = 0;
            for (int i = 0; i < arrUser.size(); i++) {
                if (arrUser.get(i).getUserPassword().equals(PassWord)
                        && arrUser.get(i).getUserName().equals(UserName)) {
                    check = true;
                    ii = i;
                }
            }
            if (check) {
                GlobalValue.preferences.setUserID(arrUser.get(ii).getUserId());
                GlobalValue.preferences.setUserInfo(arrUser.get(ii));
//                GlobalValue.preferences.getUserInfo().getUserName();
                Bundle b = new Bundle();
                b.putString("UserID", arrUser.get(ii).getUserId());
                gotoActivity(LoginActivity.this, TableActivity.class, b);
            } else {
                DialogUtility
                        .alert(self, R.string.message_invalid_account_info);
            }

        }

    }

    private void userPicker() {
        SimpleDialogPicker dialog = new SimpleDialogPicker(this, arrUser,
                txtUser.getText().toString(), "", new PickerListener() {

            @Override
            public void simplePicker(String simplePicker, String id) {
                txtUser.setText(simplePicker);
            }

            @Override
            public void dateTimePicker(int year, int month,
                                       String monthName, int day, String dayOfWeek,
                                       int hour, int min) {
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();

    }
}
