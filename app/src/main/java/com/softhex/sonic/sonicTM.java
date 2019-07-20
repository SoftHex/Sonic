package com.softhex.sonic;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;

public class sonicTM {

    public Context myCtx;
    public Snackbar mySnackBar;
    public static final int MSG_DEFAULT = PromptDialog.DIALOG_TYPE_DEFAULT;
    public static final int MSG_SUCCESS = PromptDialog.DIALOG_TYPE_SUCCESS;
    public static final int MSG_INFO = PromptDialog.DIALOG_TYPE_INFO;
    public static final int MSG_WARNING = PromptDialog.DIALOG_TYPE_WARNING;
    public static final int MSG_WRONG = PromptDialog.DIALOG_TYPE_WRONG;

    public sonicTM(Context ctx){
        this.myCtx = ctx;
    }

    public void showMS(final String title, final String msg, final int type){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                new PromptDialog(myCtx)
                        .setDialogType(type)
                        .setAnimationEnable(true)
                        .setTitleText(title)
                        .setContentText(msg)
                        .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

    }

    public void showMI(final int title, final int msg, final int type){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                new PromptDialog(myCtx)
                        .setDialogType(type)
                        .setAnimationEnable(true)
                        .setTitleText(title)
                        .setContentText(msg)
                        .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

    }

    public void showMessageContext(final String title, final String msg){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                ColorDialog dialog = new ColorDialog(myCtx);
                dialog.setTitle(title);
                dialog.setContentText(msg);
                dialog.setPositiveListener("OK", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

    }

    public void showMessageContextImage(final String title, final String msg, final int image){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                ColorDialog dialog = new ColorDialog(myCtx);
                dialog.setTitle(title);
                dialog.setContentText(msg);
                dialog.setContentImage(myCtx.getResources().getDrawable(image));
                dialog.setPositiveListener("OK", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

    }

    public void showSB(final View v, final String msg){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                mySnackBar = Snackbar.make(v,msg, Snackbar.LENGTH_SHORT);
                View sbView = mySnackBar.getView();
                int snackbarTextId = android.support.design.R.id.snackbar_text;
                TextView textView = (TextView)sbView.findViewById(snackbarTextId);
                sbView.setBackgroundColor(myCtx.getResources().getColor(R.color.colorPrimaryBlackDarkT));
                textView.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryWhite));
                mySnackBar.show();

            }
        });

    }
}
