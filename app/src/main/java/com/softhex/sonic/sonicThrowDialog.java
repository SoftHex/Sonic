package com.softhex.sonic;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import cn.refactor.lib.colordialog.ColorDialog;
import cn.refactor.lib.colordialog.PromptDialog;

public class sonicThrowDialog extends AlertDialog{

    private AlertDialog dialog;
    public static final int MSG_DEFAULT = PromptDialog.DIALOG_TYPE_DEFAULT;
    public static final int MSG_SUCCESS = PromptDialog.DIALOG_TYPE_SUCCESS;
    public static final int MSG_INFO = PromptDialog.DIALOG_TYPE_INFO;
    public static final int MSG_WARNING = PromptDialog.DIALOG_TYPE_WARNING;
    public static final int MSG_WRONG = PromptDialog.DIALOG_TYPE_WRONG;


    public sonicThrowDialog(Context context) {
        super(context);
    }

    public String setMessge(String message){
        return message;
    }

    public String setTitle(String title){
        return title;
    }

}
