package com.softhex.sonic;

import android.os.Environment;

import java.io.File;

public class sonicFile {

    public static File searchImage(String path, int codigo){
        File x = new File(Environment.getExternalStorageDirectory(), path + codigo + ".JPG");
        File y = new File(Environment.getExternalStorageDirectory(), path + codigo + ".PNG");
        return x.exists() ? x : y;
    }

    public static boolean checkTxtFile(String path, String filename){
        File x = new File(Environment.getExternalStorageDirectory(), path + filename + ".TXT");
        return x.exists() ? true : false;
    }

    public static boolean checkXmlFile(String path, String filename){
        File x = new File(Environment.getExternalStorageDirectory(), path + filename + ".xml");
        return x.exists() ? true : false;
    }

    public static File searchTxtFile(String path, String filename){
        return new File(Environment.getExternalStorageDirectory(), path + filename + ".TXT");
    }

}
