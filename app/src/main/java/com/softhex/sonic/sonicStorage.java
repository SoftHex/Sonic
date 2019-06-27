package com.softhex.sonic;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrador on 02/08/2017.
 */

public class sonicStorage {

    public void createFolder(Context ctx, String path){

        try {

            File folder = new File(Environment.getExternalStorageDirectory(), path);

            if(!folder.exists()){

                folder.mkdirs();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void deleteFolder(Context ctx, String path){

        try {

            File folder = new File(Environment.getExternalStorageDirectory(), path);

            if (folder.isDirectory())
                for (File child : folder.listFiles())
                    deleteFolder(ctx, path);

            folder.delete();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void deleteFiles(String inputFile) {
        try {

            File folder = new File(Environment.getExternalStorageDirectory(), inputFile);

            if (folder.isDirectory())
            {
                String[] children = folder.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(folder, children[i]).delete();
                }
            }

        }
        catch (Exception e) {

            Log.e("SONIC", e.getMessage());

        }
    }

}
