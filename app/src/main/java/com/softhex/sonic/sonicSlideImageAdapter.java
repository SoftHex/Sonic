package com.softhex.sonic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class sonicSlideImageAdapter extends PagerAdapter {

    private Context myCtx;
    private String[] myImages;
    private Drawable image;

    public sonicSlideImageAdapter(Context myCtx, String[] myImages) {
        this.myCtx = myCtx;
        this.myImages = myImages;
    }

    @Override
    public int getCount() {
        return myImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(myCtx);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide.with(myCtx)
                    .load(myImages[position])
                    //.placeholder(R.mipmap.loader)
                    .override(600,400)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(imageView);
            imageView.setColorFilter(myCtx.getResources().getColor(R.color.colorPrimaryBlackExtraLightT), PorterDuff.Mode.MULTIPLY);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
