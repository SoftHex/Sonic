package com.softhex.sonic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class sonicGlide {

    public static void glideDrawable(Context c, ImageView i, int d){
        Glide.with(c)
                .load(d)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }

    public static void glideImageView(Context c, ImageView i, String file){
        Glide.with(c)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }

    public static void glideFile(Context c, ImageView i, File file){
        Glide.with(c)
                .load(file)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }


}