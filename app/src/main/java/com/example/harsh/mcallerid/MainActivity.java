package com.example.harsh.mcallerid;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.harsh.mcallerid.utils.RuntimePermissionUtils;

public class MainActivity extends AppCompatActivity implements RuntimePermissionUtils.OnPermissionResult {

    private RuntimePermissionUtils permissionUtilsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtilsFragment = (RuntimePermissionUtils) getSupportFragmentManager()
                .findFragmentByTag(RuntimePermissionUtils.TAG);
        checkRuntimePermission();
    }

    private void checkRuntimePermission() {
        if (permissionUtilsFragment == null) {
            permissionUtilsFragment = new RuntimePermissionUtils();
            permissionUtilsFragment.setCallback(this);
            getSupportFragmentManager().beginTransaction()
                    .add(permissionUtilsFragment, RuntimePermissionUtils.TAG)
                    .commit();
        }
    }

    @Override
    public void onUtilReady() {

        if (permissionUtilsFragment.hasSelfPermission(MainActivity.this,
                new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS})) {
            // Toast.makeText(HomeActivity.this, "had permission", Toast.LENGTH_SHORT).show();
            //has permission
        } else {
            permissionUtilsFragment.requestPermission(
                    new String[]{Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_CONTACTS,}, 1000);
        }

    }

    @Override
    public void onPermissionGranted() {
        Toast.makeText(this, "permission given", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPermissionDenied() {
        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();


    }
}
