package com.softhex.sonic;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.View;
import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;

public class sonicThrowMessage {

    public Context myCtx;
    public Snackbar mySnackBar;
    public static final int MSG_DEFAULT = PromptDialog.DIALOG_TYPE_DEFAULT;
    public static final int MSG_SUCCESS = PromptDialog.DIALOG_TYPE_SUCCESS;
    public static final int MSG_INFO = PromptDialog.DIALOG_TYPE_INFO;
    public static final int MSG_WARNING = PromptDialog.DIALOG_TYPE_WARNING;
    public static final int MSG_WRONG = PromptDialog.DIALOG_TYPE_WRONG;

    public sonicThrowMessage(Context ctx){
        this.myCtx = ctx;
    }
    /*
    **  int type: 0 = DEFAULT, 1 = SUCCESS, 2 = INFO, 3 = WARNING, 4 = HELP, 5 = WRONG
    **  string title:
    **  string message:
     */
    public void showMessage(final String title, final String msg, final int type){

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

    public void showSnackBar(final String msg, final View v){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                mySnackBar = Snackbar.make(v,"Verifique sua conexão com a internet...", Snackbar.LENGTH_SHORT);
                View sbView = mySnackBar.getView();
                sbView.setBackgroundColor(myCtx.getResources().getColor(R.color.colorPrimaryDarkT));
                mySnackBar.show();

            }
        });

    }
}
