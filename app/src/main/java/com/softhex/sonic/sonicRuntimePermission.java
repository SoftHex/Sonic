package com.softhex.sonic;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Administrador on 03/08/2017.
 */

public abstract class sonicRuntimePermission extends AppCompatActivity{

    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
    }

    public abstract void onPermissionGaranted(int requestCode);

    public void requestAppPermissions(final String[]requestedPermissions, final int stringId, final int requestCode){

        mErrorString.put(requestCode, stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermission = false;

        for(String permission: requestedPermissions){
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);

        }

        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){

            if(showRequestPermission){
                Snackbar.make(findViewById(android.R.id.content), stringId, Snackbar.LENGTH_INDEFINITE).setAction("PERMITIR", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(sonicRuntimePermission.this, requestedPermissions, requestCode);
                    }
                }).setTextColor(Color.WHITE).setActionTextColor(Color.YELLOW).show();

            } else{

                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);

            }

        } else{

            onPermissionGaranted(requestCode);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for(int permission: grantResults){

            permissionCheck = permissionCheck + permission;

        }

        if( (grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck){

            onPermissionGaranted(requestCode);

        } else{

            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode), Snackbar.LENGTH_INDEFINITE).setAction("PERMITIR", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(sonicRuntimePermission.this, permissions, requestCode);
                    //Intent i = new Intent();
                    //i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    //i.setData(Uri.parse("package:" + getPackageName()));
                    //i.addCategory(Intent.CATEGORY_DEFAULT);
                    //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    //i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    //startActivity(i);

                }
            }).setTextColor(Color.WHITE).setActionTextColor(Color.YELLOW) .show();

        }

    }
}
