package com.softhex.sonic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

public class sonicAppearence {


    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WINE = 1;
    public final static int THEME_GREEN = 2;
    public final static int THEME_NIGHT = 3;

    private static int sTheme;
    private Activity _activity;

    public sonicAppearence(Activity _activity) {
        this._activity = _activity;
    }

    public static void changeTheme(int theme)
    {
        sTheme = theme;

    }

    public static void onFragmentCreateSetTheme(Fragment fragment)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                fragment.getActivity().setTheme(R.style.DefaultAppTheme);
                break;
            case THEME_WINE:
                fragment.getActivity().setTheme(R.style.WineAppTheme);
                break;
            case THEME_GREEN:
                fragment.getActivity().setTheme(R.style.GreenAppTheme);
                break;
            case THEME_NIGHT:
                fragment.getActivity().setTheme(R.style.NightAppTheme);
                break;
        }
    }

    public static void onActivityCreateSetTheme(Activity activity, int theme)
    {
        switch (theme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.DefaultAppTheme);
                break;
            case THEME_WINE:
                activity.setTheme(R.style.WineAppTheme);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.GreenAppTheme);
                break;
            case THEME_NIGHT:
                activity.setTheme(R.style.NightAppTheme);
                break;
        }
    }

    public static void changeStatusBarColor(Activity act,int color){

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = act.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }

    }

    public static void searchAppearence(final Activity act, final SearchView sv, final Toolbar tool, int numberOfMenuIcon, boolean containsOverflow, boolean show){

        tool.setBackgroundColor(Color.WHITE);
        final Window window = act.getWindow();
        EditText searchEditText = (EditText) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(act.getResources().getColor(R.color.colorTextAccent));
        searchEditText.setHintTextColor(act.getResources().getColor(R.color.colorTextNoAccent));

        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = tool.getWidth() -
                        (containsOverflow ? act.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((act.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(tool,
                        false ? tool.getWidth() - width : width, tool.getHeight() / 2, 0.0f, (float) width);
                createCircularReveal.setDuration(250);
                createCircularReveal.start();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryBlackLight));
            } else {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (-tool.getHeight()), 0.0f);
                translateAnimation.setDuration(220);
                tool.clearAnimation();
                tool.startAnimation(translateAnimation);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width = tool.getWidth() -
                        (containsOverflow ? act.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((act.getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(tool,
                        false ? tool.getWidth() - width : width, tool.getHeight() / 2, (float) width, 0.0f);
                createCircularReveal.setDuration(250);
                createCircularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        tool.setBackgroundColor(act.getResources().getColor(R.color.colorPrimary));
                        window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
                    }
                });
                createCircularReveal.start();
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-tool.getHeight()));
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setDuration(220);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                tool.startAnimation(animationSet);
            }

        }

    }

    public static Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }

    public static void animateDrawable(View view, int drawable) {
        ImageView v = (ImageView) view;

        if(Build.VERSION.SDK_INT >= 21) {
            AnimatedVectorDrawable d = (AnimatedVectorDrawable) view.getContext().getDrawable(drawable);
            v.setImageDrawable(d);
            d.start();
        }

        else
            {
                AnimatedVectorDrawableCompat d = (AnimatedVectorDrawableCompat) view.getResources().getDrawable(drawable);
                if (d instanceof AnimatedVectorDrawableCompat) {
                    v.setImageDrawable(d);
                    d.start();
                }


            }

    }

}
