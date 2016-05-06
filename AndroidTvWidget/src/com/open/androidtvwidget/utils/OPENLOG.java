package com.open.androidtvwidget.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 开启LOG输出.
 * <p>
 * setprop log.tag.xgimi.bootwizard VERBOSE
 * <p>
 * 
 * @author hailongqiu
 *
 */
public class OPENLOG {
	
	public static String sTag = "";
	public static boolean sDebug = false;
    public static final boolean DEBUG_DEMO_LOG = Log.isLoggable(getTag(), Log.VERBOSE);
    
    public static void initTag(String tag, boolean debug) {
    	sTag = tag;
    	sDebug = debug;
    }
    
    public static void D(String str, Object... args) {
        if (sDebug || DEBUG_DEMO_LOG) {
            Log.d(getTag(), buildLogString(str, args));
        }
    }

    public static void V(String str, Object... args) {
        if (sDebug || DEBUG_DEMO_LOG) {
            Log.v(getTag(), buildLogString(str, args));
        }
    }
    
    public static void E(String str, Object... args) {
        if (sDebug || DEBUG_DEMO_LOG) {
            Log.d(getTag(), buildLogString(str, args));
        }
    }
    
    /**
     * 如果sTAG是空则自动从StackTrace中取TAG
     */
    private static String getTag() {
    	if (!TextUtils.isEmpty(sTag)) {
    		return sTag;
    	}
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return caller.getFileName();
    }

    private static String buildLogString(String str, Object... args) {
        if (args.length > 0) {
            str = String.format(str, args);
        }
        //
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        //
        if (TextUtils.isEmpty(sTag)) {
            stringBuilder.append(caller.getMethodName())
                    .append("():")
                    .append(caller.getLineNumber())
                    .append(":")
                    .append(str);
        } else {
            stringBuilder
                    .append("(")
                    .append(caller.getFileName())
                    .append(":")
                    .append(caller.getLineNumber())
                    .append(").")
                    .append(caller.getMethodName())
                    .append("():")
                    .append(str);
        }
        return stringBuilder.toString();
    }

}
