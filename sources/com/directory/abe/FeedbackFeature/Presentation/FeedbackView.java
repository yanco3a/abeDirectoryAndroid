package com.directory.abe.FeedbackFeature.Presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.directory.abe.C0246R;
import com.directory.abe.Developer;
import com.directory.abe.FeaturedEntryFeature.Presentation.MainView;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.RegisterFeature.Presentation.RegisterView;
import com.directory.abe.SearchEntryFeature.Presentation.SearchEntryView;
import com.google.android.gms.analytics.Tracker;
import org.apache.http.protocol.HTTP;

public class FeedbackView extends AppCompatActivity implements IFeedbackView, OnClickListener {
    private static final String TAG = FeedbackView.class.getSimpleName();
    private Activity activity;
    private Tracker appTracker;
    private Button btn_submit;
    private Context context;
    private ProgressDialog dialog;
    private EditText et_improve_feedback;
    private EditText et_tester_email;
    private String feedback;
    private FeedbackPresenter presenter;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q4a;
    private String q5;
    private String q6;
    private String q7;
    private String q8;
    private String q9;
    private String testerEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.act_feedback);
        this.context = getBaseContext();
        this.activity = this;
        this.q5 = "";
        this.btn_submit = (Button) findViewById(C0246R.id.btn_tester_feedback);
        this.et_improve_feedback = (EditText) findViewById(C0246R.id.et_improve_feedback);
        this.et_tester_email = (EditText) findViewById(C0246R.id.et_tester_email);
        this.btn_submit.setOnClickListener(this);
        this.presenter = new FeedbackPresenter(this);
        this.presenter.setUpAnalytics(this.activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_feedback, menu);
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
            if (v.getId() == C0246R.id.btn_tester_feedback) {
                if (this.presenter.isOnline(this.context).booleanValue()) {
                    this.presenter.attemptFeedback(this.et_tester_email.getText().toString(), this.et_improve_feedback.getText().toString());
                } else {
                    this.presenter.sendLongMsgToUser("Abe doesn't have a good net connection right now");
                }
            }
            this.presenter.analyseScreen("feedback attempt:", "feedback attempt occurring..", "Feedback screen");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void setTesterEmail(String e) {
        this.testerEmail = e;
    }

    public void setErrorText(String e) {
        if (this.et_tester_email != null) {
            this.et_tester_email.setError("please provide a valid email for us to contact you");
        }
    }

    public void sendLongMsgToUser(String msg) {
        if (msg != null) {
            Toast.makeText(this.context, msg, 1).show();
        }
    }

    public String getQ1() {
        return this.q1;
    }

    public String getQ2() {
        return this.q2;
    }

    public String getQ3() {
        return this.q3;
    }

    public String getQ4() {
        return this.q4;
    }

    public String getQ4a() {
        return this.q4a;
    }

    public String getQ5() {
        return this.q5;
    }

    public String getQ6() {
        return this.q6;
    }

    public String getQ7() {
        return this.q7;
    }

    public String getQ8() {
        return this.q8;
    }

    public String getQ9() {
        return this.q9;
    }

    public String getEmail() {
        return this.testerEmail;
    }

    public void progessDialogMsg(String dialogMsg) {
        this.dialog = ProgressDialog.show(this, dialogMsg, null);
    }

    public void isBusiness(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String testerType = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (testerType.hashCode()) {
            case 3521:
                if (testerType.equals("no")) {
                    obj = 1;
                    break;
                }
                break;
            case 119527:
                if (testerType.equals("yes")) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q1 = "Business tester";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q1 = "Regular tester";
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void testerIsBasedIn(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String region = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (region.hashCode()) {
            case -2125317159:
                if (region.equals("Bexley/Bromley/Greenwich")) {
                    obj = 11;
                    break;
                }
                break;
            case -1591824406:
                if (region.equals("Croydon")) {
                    obj = 10;
                    break;
                }
                break;
            case -1464064299:
                if (region.equals("Brent/Harrow")) {
                    obj = 1;
                    break;
                }
                break;
            case -1455059749:
                if (region.equals("Kensington & Chelsea/Westminster")) {
                    obj = 5;
                    break;
                }
                break;
            case -1291582101:
                if (region.equals("East London & City of London")) {
                    obj = 6;
                    break;
                }
                break;
            case -946738128:
                if (region.equals("Ealing/Hammersmith/Hounslow")) {
                    obj = 3;
                    break;
                }
                break;
            case -904314857:
                if (region.equals("Kingston & Richmond")) {
                    obj = 8;
                    break;
                }
                break;
            case -688351717:
                if (region.equals("Redbridge & Waltham Forest")) {
                    obj = 13;
                    break;
                }
                break;
            case -232896952:
                if (region.equals("Barking & Havering")) {
                    obj = 12;
                    break;
                }
                break;
            case 76517104:
                if (region.equals("Other")) {
                    obj = 14;
                    break;
                }
                break;
            case 121298528:
                if (region.equals("Barnet/Enfield/Haringey")) {
                    obj = null;
                    break;
                }
                break;
            case 834936331:
                if (region.equals("Lambeth/Southwark/Lewisham")) {
                    obj = 7;
                    break;
                }
                break;
            case 1013788627:
                if (region.equals("Merton/Sutton/Wandsworth")) {
                    obj = 9;
                    break;
                }
                break;
            case 1438009975:
                if (region.equals("Camden & Islington")) {
                    obj = 4;
                    break;
                }
                break;
            case 1648660418:
                if (region.equals("Hillingdon")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q2 = "Barnet/Enfield/Haringey";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q2 = "Brent/Harrow";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q2 = "Hillingdon";
                    return;
                }
                return;
            case 3:
                if (checked) {
                    this.q2 = "Ealing/Hammersmith/Hounslow";
                    return;
                }
                return;
            case 4:
                if (checked) {
                    this.q2 = "Camden & Islington";
                    return;
                }
                return;
            case 5:
                if (checked) {
                    this.q2 = "Kensington & Chelsea/Westminster";
                    return;
                }
                return;
            case 6:
                if (checked) {
                    this.q2 = "East London & City of London";
                    return;
                }
                return;
            case 7:
                if (checked) {
                    this.q2 = "Lambeth/Southwark/Lewisham";
                    return;
                }
                return;
            case 8:
                if (checked) {
                    this.q2 = "Kingston & Richmond";
                    return;
                }
                return;
            case 9:
                if (checked) {
                    this.q2 = "Merton/Sutton/Wandsworth";
                    return;
                }
                return;
            case 10:
                if (checked) {
                    this.q2 = "Croydon";
                    return;
                }
                return;
            case 11:
                if (checked) {
                    this.q2 = "Bexley/Bromley/Greenwich";
                    return;
                }
                return;
            case 12:
                if (checked) {
                    this.q2 = "Barking & Havering";
                    return;
                }
                return;
            case 13:
                if (checked) {
                    this.q2 = "Redbridge & Waltham Forest";
                    return;
                }
                return;
            case 14:
                if (checked) {
                    this.q2 = "Other";
                    return;
                }
                return;
            default:
                this.q2 = "Other";
                return;
        }
    }

    public void testerShopsIn(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String region = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (region.hashCode()) {
            case -2125317159:
                if (region.equals("Bexley/Bromley/Greenwich")) {
                    obj = 11;
                    break;
                }
                break;
            case -1591824406:
                if (region.equals("Croydon")) {
                    obj = 10;
                    break;
                }
                break;
            case -1464064299:
                if (region.equals("Brent/Harrow")) {
                    obj = 1;
                    break;
                }
                break;
            case -1455059749:
                if (region.equals("Kensington & Chelsea/Westminster")) {
                    obj = 5;
                    break;
                }
                break;
            case -1291582101:
                if (region.equals("East London & City of London")) {
                    obj = 6;
                    break;
                }
                break;
            case -946738128:
                if (region.equals("Ealing/Hammersmith/Hounslow")) {
                    obj = 3;
                    break;
                }
                break;
            case -904314857:
                if (region.equals("Kingston & Richmond")) {
                    obj = 8;
                    break;
                }
                break;
            case -688351717:
                if (region.equals("Redbridge & Waltham Forest")) {
                    obj = 13;
                    break;
                }
                break;
            case -232896952:
                if (region.equals("Barking & Havering")) {
                    obj = 12;
                    break;
                }
                break;
            case 76517104:
                if (region.equals("Other")) {
                    obj = 14;
                    break;
                }
                break;
            case 121298528:
                if (region.equals("Barnet/Enfield/Haringey")) {
                    obj = null;
                    break;
                }
                break;
            case 834936331:
                if (region.equals("Lambeth/Southwark/Lewisham")) {
                    obj = 7;
                    break;
                }
                break;
            case 1013788627:
                if (region.equals("Merton/Sutton/Wandsworth")) {
                    obj = 9;
                    break;
                }
                break;
            case 1438009975:
                if (region.equals("Camden & Islington")) {
                    obj = 4;
                    break;
                }
                break;
            case 1648660418:
                if (region.equals("Hillingdon")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q3 = "Barnet/Enfield/Haringey";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q3 = "Brent/Harrow";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q3 = "Hillingdon";
                    return;
                }
                return;
            case 3:
                if (checked) {
                    this.q3 = "Ealing/Hammersmith/Hounslow";
                    return;
                }
                return;
            case 4:
                if (checked) {
                    this.q3 = "Camden & Islington";
                    return;
                }
                return;
            case 5:
                if (checked) {
                    this.q3 = "Kensington & Chelsea/Westminster";
                    return;
                }
                return;
            case 6:
                if (checked) {
                    this.q3 = "East London & City of London";
                    return;
                }
                return;
            case 7:
                if (checked) {
                    this.q3 = "Lambeth/Southwark/Lewisham";
                    return;
                }
                return;
            case 8:
                if (checked) {
                    this.q3 = "Kingston & Richmond";
                    return;
                }
                return;
            case 9:
                if (checked) {
                    this.q3 = "Merton/Sutton/Wandsworth";
                    return;
                }
                return;
            case 10:
                if (checked) {
                    this.q3 = "Croydon";
                    return;
                }
                return;
            case 11:
                if (checked) {
                    this.q3 = "Bexley/Bromley/Greenwich";
                    return;
                }
                return;
            case 12:
                if (checked) {
                    this.q3 = "Barking & Havering";
                    return;
                }
                return;
            case 13:
                if (checked) {
                    this.q3 = "Redbridge & Waltham Forest";
                    return;
                }
                return;
            case 14:
                if (checked) {
                    this.q3 = "Other";
                    return;
                }
                return;
            default:
                this.q3 = "Other";
                return;
        }
    }

    public void logRegSuccess(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String logRegSuccess = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (logRegSuccess.hashCode()) {
            case -93445941:
                if (logRegSuccess.equals("i didn't attempt it")) {
                    obj = 2;
                    break;
                }
                break;
            case 3521:
                if (logRegSuccess.equals("no")) {
                    obj = 1;
                    break;
                }
                break;
            case 119527:
                if (logRegSuccess.equals("yes")) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q4 = "Yes";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q4 = "No";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q4 = "I didn't attempt it";
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void logRegEasy(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String logRegEasy = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (logRegEasy.hashCode()) {
            case 3548:
                if (logRegEasy.equals("ok")) {
                    obj = 1;
                    break;
                }
                break;
            case 3105794:
                if (logRegEasy.equals("easy")) {
                    obj = null;
                    break;
                }
                break;
            case 1305942142:
                if (logRegEasy.equals("difficult")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q4a = "Easy";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q4a = "OK";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q4a = "Difficult";
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void whichFeatures(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case C0246R.id.chkbx_reg:
                if (checked) {
                    this.q5 += " Registration ";
                    return;
                }
                return;
            case C0246R.id.chkbx_log:
                if (checked) {
                    this.q5 += " Login ";
                    return;
                }
                return;
            case C0246R.id.chkbx_key:
                if (checked) {
                    this.q5 += " Keyword ";
                    return;
                }
                return;
            case C0246R.id.chkbx_rad:
                if (checked) {
                    this.q5 += " Radius ";
                    return;
                }
                return;
            case C0246R.id.chkbx_rating:
                if (checked) {
                    this.q5 += " Rating ";
                    return;
                }
                return;
            case C0246R.id.chkbx_create_list:
                if (checked) {
                    this.q5 += " Create ";
                    return;
                }
                return;
            case C0246R.id.chkbx_rate_review:
                if (checked) {
                    this.q5 += " Review ";
                    return;
                }
                return;
            default:
                this.q5 += " None ";
                return;
        }
    }

    public void howWasSearch(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String howSuccessful = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (howSuccessful.hashCode()) {
            case 3548:
                if (howSuccessful.equals("ok")) {
                    obj = 1;
                    break;
                }
                break;
            case 76480:
                if (howSuccessful.equals("N/A")) {
                    obj = 3;
                    break;
                }
                break;
            case 3105794:
                if (howSuccessful.equals("easy")) {
                    obj = null;
                    break;
                }
                break;
            case 1305942142:
                if (howSuccessful.equals("difficult")) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q6 = "Easy";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q6 = "OK";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q6 = "Difficult";
                    return;
                }
                return;
            case 3:
                if (checked) {
                    this.q6 = "N/A";
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void howImpressed(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String howImpressed = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (howImpressed.hashCode()) {
            case -887361167:
                if (howImpressed.equals("not impressed")) {
                    obj = 2;
                    break;
                }
                break;
            case -134656386:
                if (howImpressed.equals("impressed")) {
                    obj = null;
                    break;
                }
                break;
            case 76480:
                if (howImpressed.equals("N/A")) {
                    obj = 3;
                    break;
                }
                break;
            case 1844321735:
                if (howImpressed.equals("neutral")) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q7 = "Impressed";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q7 = "Neutral";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q7 = "Not impressed";
                    return;
                }
                return;
            case 3:
                if (checked) {
                    this.q7 = "N/A";
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void howOften(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String howOften = (String) ((RadioButton) view).getText();
        Object obj = -1;
        switch (howOften.hashCode()) {
            case -881863558:
                if (howOften.equals("fortnightly")) {
                    obj = 2;
                    break;
                }
                break;
            case -791707519:
                if (howOften.equals("weekly")) {
                    obj = 1;
                    break;
                }
                break;
            case 95346201:
                if (howOften.equals("daily")) {
                    obj = null;
                    break;
                }
                break;
            case 104712844:
                if (howOften.equals("never")) {
                    obj = 4;
                    break;
                }
                break;
            case 1236635661:
                if (howOften.equals("monthly")) {
                    obj = 3;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (checked) {
                    this.q8 = "Daily";
                    return;
                }
                return;
            case 1:
                if (checked) {
                    this.q8 = "Weekly";
                    return;
                }
                return;
            case 2:
                if (checked) {
                    this.q8 = "Fortnightly";
                    return;
                }
                return;
            case 3:
                if (checked) {
                    this.q8 = "Monthly";
                    return;
                }
                return;
            case 4:
                if (checked) {
                    this.q8 = "Never";
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setQ9(String s) {
        this.q9 = s;
    }

    public void onFeedbackSuccess(String successMsg) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        sendLongMsgToUser(successMsg);
        startActivity(new Intent(this, MainView.class));
    }

    public void onFeedbackFailure(String failureMsg) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        sendLongMsgToUser(failureMsg);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0246R.id.action_home:
                startActivity(new Intent(this, MainView.class));
                break;
            case C0246R.id.action_email_us:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{Developer.ADMIN_EMAIL});
                intent.putExtra("android.intent.extra.SUBJECT", "Abe directory Tester/Participation query");
                intent.setType(HTTP.PLAIN_TEXT_TYPE);
                startActivity(intent);
                break;
            case C0246R.id.action_search:
                startActivity(new Intent(this, SearchEntryView.class));
                break;
            case C0246R.id.action_login:
                startActivity(new Intent(this, LoginView.class));
                break;
            case C0246R.id.action_register:
                startActivity(new Intent(this, RegisterView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
