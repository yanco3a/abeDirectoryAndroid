package com.directory.abe.LoginFeature.Presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.directory.abe.RegisterFeature.Presentation.RegisterView;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedAdmin;
import com.directory.abe.SQLiteDatabase.SQLiteModel.FRContract.FeedUser;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.google.android.gms.analytics.Tracker;
import org.apache.http.protocol.HTTP;

public class LoginView extends AppCompatActivity implements ILoginView, OnClickListener {
    private static final String TAG = LoginView.class.getSimpleName();
    private Activity activity;
    private Tracker appTracker;
    private Button btn_a_login;
    private Button btn_u_login;
    private Context context;
    private ProgressDialog dialog;
    private EditText et_error;
    private EditText et_login_email;
    private EditText et_login_password;
    private View layout_login;
    private LoginPresenter presenter;
    private TextView tv_a_switch_register;
    private TextView tv_admin_login_title;
    private TextView tv_switch_to_admin;
    private TextView tv_switch_to_admn;
    private TextView tv_switch_to_user;
    private TextView tv_switch_to_usr;
    private TextView tv_u_switch_register;
    private TextView tv_user_login_title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.act_login);
        this.context = getBaseContext();
        this.activity = this;
        this.btn_u_login = (Button) findViewById(C0246R.id.btn_u_login);
        this.btn_a_login = (Button) findViewById(C0246R.id.btn_a_login);
        this.et_login_email = (EditText) findViewById(C0246R.id.et_login_email);
        this.et_login_password = (EditText) findViewById(C0246R.id.et_login_password);
        this.tv_admin_login_title = (TextView) findViewById(C0246R.id.tv_admin_login_title);
        this.tv_user_login_title = (TextView) findViewById(C0246R.id.tv_user_login_title);
        this.tv_switch_to_admin = (TextView) findViewById(C0246R.id.tv_switch_to_admin);
        this.tv_switch_to_user = (TextView) findViewById(C0246R.id.tv_switch_to_user);
        this.tv_a_switch_register = (TextView) findViewById(C0246R.id.tv_a_switch_register);
        this.tv_u_switch_register = (TextView) findViewById(C0246R.id.tv_u_switch_register);
        this.tv_switch_to_admn = (TextView) findViewById(C0246R.id.tv_switch_to_admn);
        this.tv_switch_to_usr = (TextView) findViewById(C0246R.id.tv_switch_to_usr);
        this.layout_login = findViewById(C0246R.id.layout_login);
        this.et_error = (EditText) findViewById(C0246R.id.et_error);
        this.et_error.setKeyListener(null);
        this.presenter = new LoginPresenter(this);
        this.presenter.setUpAnalytics(this.activity);
        this.btn_a_login.setOnClickListener(this);
        this.btn_u_login.setOnClickListener(this);
        this.tv_switch_to_admin.setOnClickListener(this);
        this.tv_switch_to_user.setOnClickListener(this);
        this.tv_a_switch_register.setOnClickListener(this);
        this.tv_u_switch_register.setOnClickListener(this);
    }

    public void checkUserTypeFromRegistration() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                Bundle bundle = new Bundle();
                if (intent.getExtras().getBundle("com.directory.abe.reg") != null) {
                    String userSearchFromRegScreen = intent.getExtras().getBundle("com.directory.abe.reg").getString("usertype");
                    if (userSearchFromRegScreen != null && userSearchFromRegScreen.equals("a")) {
                        switchToAdmin();
                    }
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_login, menu);
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
        switch (v.getId()) {
            case C0246R.id.btn_u_login:
                this.presenter.analyseScreen("login: user", "user login occurring..", "Login screen");
                loginAttempt(C0246R.id.btn_u_login);
                return;
            case C0246R.id.btn_a_login:
                this.presenter.analyseScreen("login: admin", "admin login occurring..", "Login screen");
                loginAttempt(C0246R.id.btn_a_login);
                return;
            case C0246R.id.tv_u_switch_register:
                goToRegistration("u");
                return;
            case C0246R.id.tv_a_switch_register:
                goToRegistration("a");
                return;
            case C0246R.id.tv_switch_to_admin:
                switchToAdmin();
                return;
            case C0246R.id.tv_switch_to_user:
                this.tv_admin_login_title.setVisibility(8);
                this.btn_a_login.setVisibility(8);
                this.tv_a_switch_register.setVisibility(8);
                this.tv_switch_to_user.setVisibility(8);
                this.tv_switch_to_usr.setVisibility(8);
                this.layout_login.setBackgroundColor(Color.parseColor("#FF565270"));
                this.tv_user_login_title.setVisibility(0);
                this.btn_u_login.setVisibility(0);
                this.tv_u_switch_register.setVisibility(0);
                this.tv_switch_to_admin.setVisibility(0);
                this.tv_switch_to_admn.setVisibility(0);
                return;
            default:
                return;
        }
    }

    public void switchToAdmin() {
        this.tv_user_login_title.setVisibility(8);
        this.btn_u_login.setVisibility(8);
        this.tv_u_switch_register.setVisibility(8);
        this.tv_switch_to_admin.setVisibility(8);
        this.tv_switch_to_admn.setVisibility(8);
        this.layout_login.setBackgroundColor(Color.parseColor("#FF70526F"));
        this.tv_admin_login_title.setVisibility(0);
        this.btn_a_login.setVisibility(0);
        this.tv_a_switch_register.setVisibility(0);
        this.tv_switch_to_user.setVisibility(0);
        this.tv_switch_to_usr.setVisibility(0);
    }

    public void setErrorText(String s) {
        this.et_error.setError(s);
    }

    public void goToRegistration(String type) {
        Intent intent = new Intent(this, RegisterView.class);
        if (type.equals("a")) {
            Bundle extras = new Bundle();
            extras.putString("usertype", type);
            intent.putExtra("com.directory.abe.type", extras);
        }
        startActivity(intent);
    }

    public void loginAttempt(int id) {
        try {
            if (validate(this.et_login_email, this.et_login_password)) {
                this.et_error.setKeyListener(null);
                String u = this.et_login_email.getText().toString();
                String p = this.et_login_password.getText().toString();
                if (this.presenter.isOnline(this.context).booleanValue()) {
                    this.presenter.attemptLogin(u, p, id);
                } else {
                    this.presenter.sendLongMsgToUser("Abe doesn't have a good net connection right now");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public boolean validate(EditText et_login_email, EditText et_login_password) {
        if (et_login_email.length() == 0) {
            setErrorText("Email address is required!");
            return false;
        } else if (et_login_password.length() != 0) {
            return true;
        } else {
            setErrorText("Password is required!");
            return false;
        }
    }

    public void onLoginAsuccess(String successMsg, int adminId, String adminUsername) {
        try {
            if (this.dialog != null) {
                this.dialog.dismiss();
            }
            Toast.makeText(this, successMsg, 1).show();
            Intent goToCreateView = new Intent(this, CreateEntryView.class);
            Bundle extras = new Bundle();
            extras.putInt("adminId", adminId);
            extras.putString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, adminUsername);
            goToCreateView.putExtras(extras);
            Editor editedPrefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0).edit();
            editedPrefs.putInt("uId", 0);
            editedPrefs.putString(FeedUser.COLUMN_NAME_USER_USERNAME, null);
            editedPrefs.putString(FeedUser.COLUMN_NAME_USER_EMAIL, null);
            editedPrefs.putInt("aId", adminId);
            editedPrefs.putString(FeedAdmin.COLUMN_NAME_ADMIN_USERNAME, adminUsername);
            editedPrefs.commit();
            startActivity(goToCreateView);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onLoginUsuccess(String successMsg, int userId, String username, String userEmail) {
        try {
            if (this.dialog != null) {
                this.dialog.dismiss();
            }
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
            editedPrefs.commit();
            startActivity(goToSearchView);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void onLoginFailure(String failureMsg) {
        this.dialog.dismiss();
        Toast.makeText(this, failureMsg, 1).show();
    }

    public void progressDialogMsg(String dialogMsg) {
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
            case C0246R.id.action_register:
                startActivity(new Intent(this, RegisterView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
