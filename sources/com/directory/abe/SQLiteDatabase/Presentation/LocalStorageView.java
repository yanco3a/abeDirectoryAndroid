package com.directory.abe.SQLiteDatabase.Presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.directory.abe.C0246R;
import com.directory.abe.FeaturedEntryFeature.Presentation.MainView;
import com.directory.abe.LoginFeature.Presentation.LoginView;
import com.directory.abe.SQLiteDatabase.Services.SQLiteHelper;
import com.directory.abe.SQLiteDatabase.UseCases.LocalStoragePresenter;

public class LocalStorageView extends AppCompatActivity implements ILocalStorageView, AnimationListener {
    private static final String TAG = SQLiteHelper.class.getSimpleName();
    private LocalStorageView activity;
    private Animation animation;
    private Context context;
    private ProgressDialog dialog;
    private ImageView img_splash;
    private LocalStoragePresenter localStoragePresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.act_splash);
        this.context = getBaseContext();
        this.activity = this;
        this.localStoragePresenter = new LocalStoragePresenter(this, this.context, this.activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0246R.menu.menu_search, menu);
        return true;
    }

    protected void onResume() {
        super.onResume();
        gotoMain();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void toastLMsg(String toastLMsg) {
        Toast.makeText(getBaseContext(), toastLMsg, 1).show();
    }

    public void toastSMsg(String toastSMsg) {
        Toast.makeText(getBaseContext(), toastSMsg, 0).show();
    }

    public void progessDialogMsg(String dialogMsg) {
        this.dialog = ProgressDialog.show(this, dialogMsg, null);
    }

    public void onSQLiteFailure(String failureMsg) {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        gotoMain();
    }

    public void onSQLiteSuccess(String successMsg) {
        this.dialog.dismiss();
        toastLMsg(successMsg);
        gotoMain();
    }

    public void gotoMain() {
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        startActivity(new Intent(this, MainView.class));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0246R.id.action_home:
                startActivity(new Intent(this, MainView.class));
                break;
            case C0246R.id.action_login:
                startActivity(new Intent(this, LoginView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (this.localStoragePresenter.isOnline(getBaseContext()).booleanValue()) {
            this.localStoragePresenter.setupDBHelper(getBaseContext());
            this.localStoragePresenter.showProgessDialogMsg();
            this.localStoragePresenter.getServerData();
            return;
        }
        gotoMain();
        toastLMsg("Abe isn't online right now");
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
