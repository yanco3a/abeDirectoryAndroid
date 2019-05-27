package com.directory.abe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    private CheckBox chk_box;

    /* renamed from: com.directory.abe.SettingsActivity$1 */
    class C02621 implements OnCheckedChangeListener {
        C02621() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!SettingsActivity.this.chk_box.isChecked()) {
                return;
            }
            if (ContextCompat.checkSelfPermission(SettingsActivity.this, "android.permission.ACCESS_FINE_LOCATION") != 0 || ContextCompat.checkSelfPermission(SettingsActivity.this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(SettingsActivity.this, "android.permission.ACCESS_COARSE_LOCATION")) {
                    ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 0);
                    Toast.makeText(SettingsActivity.this, "well done donut", 1).show();
                    return;
                }
                String packageName = BuildConfig.APPLICATION_ID;
                try {
                    Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    i.setData(Uri.parse("package:" + packageName));
                    SettingsActivity.this.startActivity(i);
                } catch (ActivityNotFoundException e) {
                    SettingsActivity.this.startActivity(new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS"));
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0246R.layout.settings_menu_layout);
        this.chk_box = (CheckBox) findViewById(C0246R.id.loc_checkBox);
        this.chk_box.setOnCheckedChangeListener(new C02621());
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                    return;
                }
                return;
            default:
                return;
        }
    }
}
