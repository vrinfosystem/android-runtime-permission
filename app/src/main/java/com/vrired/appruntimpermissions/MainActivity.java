package com.vrired.appruntimpermissions;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button check_perm_btn;
    Button request_perm_btn;
   public static final int PERMISSION_REQUEST_CODE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check_perm_btn=(Button) findViewById(R.id.check_perm_btn);
        request_perm_btn=(Button) findViewById(R.id.request_perm_btn);
        check_perm_btn.setOnClickListener(this);
        request_perm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.check_perm_btn:
                if(checkPermission()){
                    Toast.makeText(this,"Permission is already granted.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Please request permission...",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.request_perm_btn:
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    Toast.makeText(this,"Wow,Permission granted already.",Toast.LENGTH_SHORT).show();

                }
                break;

        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),READ_CONTACTS);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS,WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean readContactsAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (readContactsAccepted && writeStorageAccepted)
                        Toast.makeText(this,"Permission is Granted for read contacts and write storage,Now you can enjoy all features of our app",Toast.LENGTH_LONG).show();
                    else {

                        Toast.makeText(this,"Oops!you denied permission,We are sorry ,you can't access all features of this app.",Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions,otherwise you will miss certain feature of this app",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_CONTACTS,WRITE_EXTERNAL_STORAGE},

                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}


