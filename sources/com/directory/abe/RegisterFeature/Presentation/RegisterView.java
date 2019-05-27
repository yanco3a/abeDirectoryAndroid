package com.directory.abe.RegisterFeature.Presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.directory.abe.BuildConfig;
import com.directory.abe.C0246R;
import com.directory.abe.CreateEntryFeature.Presentation.CreateEntryView;
import com.directory.abe.Developer;
import com.directory.abe.FeaturedEntryFeature.Presentation.MainView;
import com.directory.abe.FeedbackFeature.Presentation.FeedbackView;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedAdmin;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedUser;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.google.android.gms.analytics.Tracker;
import org.apache.http.protocol.HTTP;

public class RegisterView extends AppCompatActivity implements IRegisterView, OnClickListener {
    private static final String TAG = RegisterView.class.getSimpleName();
    private Activity activity;
    private Tracker appTracker;
    private Button btn_a_register;
    private Button btn_u_register;
    private Context context;
    private ProgressDialog dialog;
    private EditText et_error;
    private EditText et_register_email;
    private EditText et_register_password;
    private EditText et_register_username;
    private View layout_register;
    private SharedPreferences prefs;
    private RegisterPresenter presenter;
    private TextView tv_a_register_info;
    private TextView tv_admin_register_title;
    private TextView tv_switch_to_adm;
    private TextView tv_switch_to_admin;
    private TextView tv_switch_to_user;
    private TextView tv_switch_to_usr;
    private TextView tv_u_register_info;
    private TextView tv_user_register_title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.act_register);
        this.context = getBaseContext();
        this.activity = this;
        this.btn_u_register = (Button) findViewById(C0246R.id.btn_u_register);
        this.btn_a_register = (Button) findViewById(C0246R.id.btn_a_register);
        this.et_register_username = (EditText) findViewById(C0246R.id.et_register_username);
        this.et_register_email = (EditText) findViewById(C0246R.id.et_register_email);
        this.et_register_password = (EditText) findViewById(C0246R.id.et_register_password);
        this.tv_admin_register_title = (TextView) findViewById(C0246R.id.tv_admin_register_title);
        this.tv_switch_to_admin = (TextView) findViewById(C0246R.id.tv_switch_to_admin);
        this.tv_switch_to_user = (TextView) findViewById(C0246R.id.tv_switch_to_user);
        this.tv_user_register_title = (TextView) findViewById(C0246R.id.tv_user_register_title);
        this.tv_u_register_info = (TextView) findViewById(C0246R.id.tv_u_register_info);
        this.tv_a_register_info = (TextView) findViewById(C0246R.id.tv_a_register_info);
        this.tv_switch_to_usr = (TextView) findViewById(C0246R.id.tv_switch_to_usr);
        this.tv_switch_to_adm = (TextView) findViewById(C0246R.id.tv_switch_to_adm);
        this.layout_register = findViewById(C0246R.id.layout_register);
        this.et_error = (EditText) findViewById(C0246R.id.et_error);
        this.et_error.setKeyListener(null);
        this.presenter = new RegisterPresenter(this);
        this.btn_a_register.setOnClickListener(this);
        this.btn_u_register.setOnClickListener(this);
        this.tv_switch_to_admin.setOnClickListener(this);
        this.tv_switch_to_user.setOnClickListener(this);
        checkIfUserSelectedTypeFromLogin();
        this.presenter.setUpAnalytics(this.activity);
    }

    public void checkIfUserSelectedTypeFromLogin() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                Bundle bundle = new Bundle();
                if (intent.getExtras().getBundle("com.directory.abe.type") != null) {
                    String userSearchFromMainScreen = intent.getExtras().getBundle("com.directory.abe.type").getString("usertype");
                    if (userSearchFromMainScreen != null && userSearchFromMainScreen.equals("a")) {
                        switchToAdmin();
                    }
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_register, menu);
        return true;
    }

    protected void onResume() {
        super.onResume();
        try {
            this.presenter.resumeTracker(TAG);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case C0246R.id.tv_switch_to_admin:
                    switchToAdmin();
                    return;
                case C0246R.id.tv_switch_to_user:
                    this.tv_a_register_info.setVisibility(8);
                    this.tv_admin_register_title.setVisibility(8);
                    this.tv_switch_to_user.setVisibility(8);
                    this.tv_switch_to_usr.setVisibility(8);
                    this.btn_a_register.setVisibility(8);
                    this.tv_u_register_info.setVisibility(0);
                    this.layout_register.setBackgroundColor(Color.parseColor("#FF565270"));
                    this.tv_user_register_title.setVisibility(0);
                    this.tv_switch_to_admin.setVisibility(0);
                    this.tv_switch_to_adm.setVisibility(0);
                    this.btn_u_register.setVisibility(0);
                    this.et_register_username.setVisibility(0);
                    return;
                case C0246R.id.btn_u_register:
                    this.presenter.analyseScreen("register: user", "user registration occurring..", "Registration screen");
                    registerAttempt(C0246R.id.btn_u_register);
                    return;
                case C0246R.id.btn_a_register:
                    this.presenter.analyseScreen("register: admin", "admin registration occurring..", "Registration screen");
                    registerAttempt(C0246R.id.btn_a_register);
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        e.printStackTrace();
    }

    public void switchToAdmin() {
        this.tv_u_register_info.setVisibility(8);
        this.tv_user_register_title.setVisibility(8);
        this.tv_switch_to_admin.setVisibility(8);
        this.tv_switch_to_adm.setVisibility(8);
        this.btn_u_register.setVisibility(8);
        this.et_register_username.setVisibility(8);
        this.tv_a_register_info.setVisibility(0);
        this.layout_register.setBackgroundColor(Color.parseColor("#FF70526F"));
        this.tv_admin_register_title.setVisibility(0);
        this.tv_switch_to_user.setVisibility(0);
        this.tv_switch_to_usr.setVisibility(0);
        this.btn_a_register.setVisibility(0);
    }

    public void setErrorText(String s) {
        this.et_error.setError(s);
    }

    public void registerAttempt(int id) {
        try {
            if (this.presenter.isOnline(this.context).booleanValue()) {
                String u = this.et_register_username.getText().toString();
                String e = this.et_register_email.getText().toString();
                String p = this.et_register_password.getText().toString();
                if (validate(id, this.et_register_username, this.et_register_email, this.et_register_password)) {
                    this.et_error.setKeyListener(null);
                    this.presenter.attemptRegister(u, e, p, id);
                    return;
                }
                return;
            }
            this.presenter.sendLongMsgToUser("Abe doesn't have a good net connection right now");
        } catch (Exception e2) {
            e2.printStackTrace();
            e2.getMessage();
            this.presenter.sendLongMsgToUser("Abe isn't feeling so good right now");
        }
    }

    public boolean validate(int id, EditText et_register_username, EditText et_register_email, EditText et_register_password) {
        try {
            if (this.presenter.getRegistrationType(id).equals("user") && TextUtils.isEmpty(et_register_username.getText().toString())) {
                setErrorText("username is required!");
                return false;
            } else if (TextUtils.isEmpty(et_register_email.getText().toString()) && !Patterns.EMAIL_ADDRESS.matcher(et_register_email.getText().toString()).matches()) {
                setErrorText("valid email is required i.e. me@abe.com");
                return false;
            } else if (TextUtils.isEmpty(et_register_password.getText().toString())) {
                setErrorText("Password is required!");
                return false;
            } else if (et_register_password.length() >= 6) {
                return true;
            } else {
                setErrorText("Password must contain at least 6 characters");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        }
    }

    public void onRegisterAsuccess(String successMsg, int adminId, String adminUsername) {
        this.dialog.dismiss();
        Toast.makeText(this, successMsg, 1).show();
        Intent goToCreateView = new Intent(this, CreateEntryView.class);
        Bundle extras = new Bundle();
        extras.putInt("adminId", adminId);
        extras.putString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, adminUsername);
        extras.putString("usertype", "a");
        goToCreateView.putExtra("com.directory.abe.reg", extras);
        this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
        Editor editedPrefs = this.prefs.edit();
        editedPrefs.putInt("uId", 0);
        editedPrefs.putString(FeedUser.COLUMN_NAME_USER_USERNAME, null);
        editedPrefs.putString(FeedUser.COLUMN_NAME_USER_EMAIL, null);
        editedPrefs.putInt("aId", adminId);
        editedPrefs.putString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, adminUsername);
        editedPrefs.apply();
        startActivity(goToCreateView);
    }

    public void onRegisterUsuccess(String successMsg, int userId, String username, String userEmail) {
        this.dialog.dismiss();
        toastLMsg(successMsg);
        Intent goToSearchView = new Intent(this, SearchEntryView.class);
        Bundle extras = new Bundle();
        extras.putInt("userId", userId);
        goToSearchView.putExtras(extras);
        Editor editedPrefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0).edit();
        editedPrefs.putInt("aId", 0);
        editedPrefs.putString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, null);
        editedPrefs.putInt("uId", userId);
        editedPrefs.putString(FeedUser.COLUMN_NAME_USER_USERNAME, username);
        editedPrefs.putString(FeedUser.COLUMN_NAME_USER_EMAIL, userEmail);
        editedPrefs.apply();
        startActivity(goToSearchView);
    }

    public void onRegisterFailure(final String failureMsg) {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    RegisterView.this.dialog.dismiss();
                    Toast.makeText(RegisterView.this, failureMsg, 1).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void progessDialogMsg(String dialogMsg) {
        this.dialog = ProgressDialog.show(this, dialogMsg, null);
    }

    public void toastLMsg(String toastLMsg) {
        Toast.makeText(this, toastLMsg, 1).show();
    }

    public void toastSMsg(String toastSMsg) {
        Toast.makeText(this, toastSMsg, 0).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0246R.id.action_tester_feedback:
                startActivity(new Intent(this, FeedbackView.class));
                break;
            case C0246R.id.action_home:
                startActivity(new Intent(this, MainView.class));
                break;
            case C0246R.id.action_email_us:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{Developer.ADMIN_EMAIL});
                intent.putExtra("android.intent.extra.SUBJECT", "Abe directory query");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                startActivity(intent);
                break;
            case C0246R.id.action_search:
                startActivity(new Intent(this, SearchEntryView.class));
                break;
            case C0246R.id.action_login:
                startActivity(new Intent(this, LoginView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
