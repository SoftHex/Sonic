package com.softhex.sonic;

import android.app.Activity;
import android.content.Context;

public class sonicHelper {


        public static String getActivityName(Activity act) {
            return act.getClass().getSimpleName();
        }

        public static String getClassName(StackTraceElement e) {
            String c = e.getClassName();
            String d = c.substring(c.lastIndexOf('.') + 1, c.length());
            return d.replace("$",", ");
        }

        public static String getMethodNames(StackTraceElement e) {
            return e.getMethodName();
        }

        public String getClassAndMethodNames(Activity act, StackTraceElement e) {
            String mainClass = act.getClass().getSimpleName();
            String c = e.getClassName();
            String workClass = c.substring(c.lastIndexOf('.') + 1, c.length());
            return mainClass+", "+workClass+", "+e.getMethodName();
        }

}
