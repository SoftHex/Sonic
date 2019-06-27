package com.softhex.sonic;

import android.content.Context;

public class sonicSystem {

    private Context myCtx;

    System System = new System();

    public sonicSystem(Context ctx) {
        this.myCtx = ctx;
    }

    class System{

        public String getActivityName() {
            return myCtx.getClass().getSimpleName();
        }

        public String getClassName(StackTraceElement e) {
            String c = e.getClassName();
            String d = c.substring(c.lastIndexOf('.') + 1, c.length());
            return d.replace("$",", ");
        }

        public String getMethodNames(StackTraceElement e) {
            return e.getMethodName();
        }

        public String getClassAndMethodNames(StackTraceElement e) {
            String mainClass = myCtx.getClass().getSimpleName();
            String c = e.getClassName();
            String workClass = c.substring(c.lastIndexOf('.') + 1, c.length());
            return mainClass+", "+workClass+", "+e.getMethodName();
        }

    }

}
