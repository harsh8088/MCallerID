package com.example.harsh.mcallerid;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    }

    private void checkRuntimePermission() {
        if (permissionUtilsFragment == null) {
            permissionUtilsFragment = new RuntimePermissionUtils();
            permissionUtilsFragment.setCallback(this);
            getSupportFragmentManager().beginTransaction()
                    .add(permissionUtilsFragment, RuntimePermissionUtils.TAG)
                    .commit();
        }
        else if (permissionUtilsFragment.hasSelfPermission(MainActivity.this,
                new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CALL_LOG})) {
            Intent intent = new Intent(MainActivity.this, CallDetailsActivity.class);
            startActivity(intent);
            // Toast.makeText(HomeActivity.this, "had permission", Toast.LENGTH_SHORT).show();
            //has permission
        }
    }


    @Override
    public void onUtilReady() {

        if (permissionUtilsFragment.hasSelfPermission(MainActivity.this,
                new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CALL_LOG})) {
            Intent intent = new Intent(MainActivity.this, CallDetailsActivity.class);
            startActivity(intent);
            // Toast.makeText(HomeActivity.this, "had permission", Toast.LENGTH_SHORT).show();
            //has permission
        } else {
            permissionUtilsFragment.requestPermission(
                    new String[]{Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CALL_LOG}, 1000);
        }

    }

    @Override
    public void onPermissionGranted() {
        Toast.makeText(this, "permission given", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, CallDetailsActivity.class);
        startActivity(intent);

    }

    @Override
    public void onPermissionDenied() {
        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
    }

    public void onClickShowLogs(View view) {
        checkRuntimePermission();
    }
}
