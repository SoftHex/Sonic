package com.softhex.sonic;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.softhex.materialdialog.ColorDialog;
import com.softhex.materialdialog.PromptDialog;

public class sonicDialog {

    public Context myCtx;
    public Snackbar mySnackBar;
    public static final int MSG_DEFAULT = PromptDialog.DIALOG_TYPE_DEFAULT;
    public static final int MSG_SUCCESS = PromptDialog.DIALOG_TYPE_SUCCESS;
    public static final int MSG_INFO = PromptDialog.DIALOG_TYPE_INFO;
    public static final int MSG_WARNING = PromptDialog.DIALOG_TYPE_WARNING;
    public static final int MSG_WRONG = PromptDialog.DIALOG_TYPE_WRONG;
    public static int RESULT;
    public static final boolean OK = false;
    public static final boolean CANCEL  = false;

    public sonicDialog(Context ctx){
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
                            public void onClick(PromptDialog dialog){
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

    }

    public Void showMS(final String title, final String msg, final int type, Void method){

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
                            public void onClick(PromptDialog dialog){
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        return method;

    }

    public boolean showMSTwoOptions(final String title, final String msg, String optpositive, String optnegative, final int type){

        final PromptDialog prompt = new PromptDialog(myCtx);

        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {

                prompt.setDialogType(type);
                prompt.setAnimationEnable(true);
                prompt.setTitle(title);
                prompt.setContentText(msg);
                prompt.setPositiveListener(optpositive,new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).setNegativeListener(optnegative, new PromptDialog.OnNegativeListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });
                prompt.show();

            }
        });

        return prompt.getResultListener();
    }

    public int showMSThreeOptions(final String title, final String msg, String optpositive, String optnegative, String thirdoption, final int type){

        final PromptDialog prompt = new PromptDialog(myCtx);

        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {

                prompt.setDialogType(type);
                prompt.setAnimationEnable(true);
                prompt.setTitle(title);
                prompt.setContentText(msg);
                prompt.setOrientation(PromptDialog.VERTICAL);
                prompt.setPositiveListener(optpositive,new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).setNegativeListener(optnegative, new PromptDialog.OnNegativeListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).setThirdOptionListener(thirdoption, new PromptDialog.OnThirdOptionListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                });
                prompt.show();

            }
        });

        return prompt.getResultListenerInt();
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

    public void showSnackBar(final View v, final String msg){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                mySnackBar = Snackbar.make(v,msg, Snackbar.LENGTH_SHORT);
                View sbView = mySnackBar.getView();
                TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                sbView.setBackgroundColor(myCtx.getResources().getColor(R.color.colorPrimaryBlackDarkT));
                textView.setTextColor(myCtx.getResources().getColor(R.color.colorPrimaryWhite));
                mySnackBar.show();

            }
        });

    }
}
