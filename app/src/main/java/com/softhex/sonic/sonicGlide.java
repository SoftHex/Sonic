package com.softhex.sonic;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class sonicGlide {

    public static void glideDrawable(Context c, ImageView i, int d){
        Glide.with(c).clear(i);
        Glide.with(c)
                .load(d)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(600,600)
                .centerCrop()
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }

    public static void glideImageView(Context c, ImageView i, String file, int width, int height){

        Glide.with(c).clear(i);
        Glide.get(c).clearMemory();
        Glide.with(c)
                .load(file)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(width, height)
                // MELHOR OPÇÃO PARA TODAS AS IMAGENS
                .fitCenter()
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }

    public static void glideImageViewPlaceHolder(Context c, ImageView i, String file, int placeholder){
        Glide.with(c).clear(i);
        Glide.with(c)
                .load(file)
                .placeholder(placeholder)
                .override(600,600)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }

    public static void glideFile(Context c, ImageView i, File file){
        Glide.with(c).clear(i);
        Glide.get(c).clearMemory();
        Glide.with(c)
                .load(file)
                .override(300,300)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(i);
    }


}
