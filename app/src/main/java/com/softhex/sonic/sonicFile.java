package com.softhex.sonic;

import android.os.Environment;

import java.io.File;

public class sonicFile {

    public static File searchFile(String path, int codigo){
        File x = new File(Environment.getExternalStorageDirectory(), path + codigo + ".JPG");
        File y = new File(Environment.getExternalStorageDirectory(), path + codigo + ".PNG");
        return x.exists() ? x : y;
    }

}
