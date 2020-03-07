package com.softhex.sonic;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrador on 02/08/2017.
 */

public class sonicStorage {

    private Context myCtx;

    public sonicStorage (Context ctx) {
        this.myCtx = ctx;

    }

    public sonicStorage show(Context ctx, String m) {
        this.myCtx = ctx;
        return show(ctx, m);

    }

    public void createFolder(String path){

        try {

            File folder = new File(Environment.getExternalStorageDirectory(), path);

            if(!folder.exists()){

                folder.mkdirs();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createFolder(String[] folders){

        try {

            for (int i = 0; i< folders.length; i++) {
                File folder = new File(Environment.getExternalStorageDirectory(), folders[i]);
                if(!folder.exists()){

                    folder.mkdirs();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void deleteFolder(String path){

        try {

            File folder = new File(Environment.getExternalStorageDirectory(), path);

            if (folder.isDirectory())
                for (File child : folder.listFiles())
                    deleteFolder(path);

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
