package com.softhex.sonic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class sonicAppearence {


    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WINE = 1;
    public final static int THEME_GREEN = 2;
    public final static int THEME_NIGHT = 3;

    private static int sTheme;
    private AppCompatActivity _activity;

    public sonicAppearence(AppCompatActivity _activity) {
        this._activity = _activity;
    }

    public static void layoutWhitTransparentStatusBar(Context ctx, Window w){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void layoutWhitLogicalMenu(Context ctx, Window w){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && ViewConfiguration.get(ctx).hasPermanentMenuKey()) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void layoutFullScreenMode(Window w){
        int uiOptions = w.getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        w.getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public static void layoutWhitNoLogicalMenu(Context ctx, Window w){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(Color.TRANSPARENT);
        }
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

    public static void onActivityCreateSetTheme(AppCompatActivity activity, int theme)
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

    public static void changeStatusBarColor(AppCompatActivity act, int color){

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = act.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }

    }

    public static void searchAppearence(Activity act, SearchView sv, final Toolbar tool, int numberOfMenuIcon, boolean containsOverflow, boolean show){

        tool.setBackgroundColor(Color.WHITE);
        Window window = act.getWindow();
        EditText searchEditText = (EditText) sv.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(act.getResources().getColor(R.color.colorTextAccentDark));
        searchEditText.setHintTextColor(act.getResources().getColor(R.color.colorTextAccentDark));

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
                //window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryBlackLight));
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
                        //window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
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
