package com.softhex.sonic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class sonicSlideImageAdapter extends PagerAdapter {

    private Context myCtx;
    private String[] myImages;
    private Boolean myCcrop;

    public sonicSlideImageAdapter(Context myCtx, String[] myImages, Boolean ccrop) {
        this.myCtx = myCtx;
        this.myImages = myImages;
        this.myCcrop = ccrop;
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
        imageView.setScaleType(myCcrop ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_CENTER);

            Glide.with(myCtx).clear(imageView);
            Glide.get(myCtx).clearMemory();
            Glide.with(myCtx)
                    .load(myImages[position])
                    //.placeholder(R.mipmap.loader)
                    .override(myCcrop ? 600 : 300, myCcrop ? 400 : 300)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(imageView);
            //imageView.setColorFilter(myCtx.getResources().getColor(R.color.colorPrimaryBlackExtraLightT), PorterDuff.Mode.DARKEN);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
