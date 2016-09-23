package com.open.androidtvwidget.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据资源名字获取ID.
 * Created by hailongqiu on 2016/9/4.
 */
public class IdentifierUtuils {

    public static int loadViewGravity(String gravity) {
        int g = Gravity.NO_GRAVITY;
        if (!TextUtils.isEmpty(gravity)) {
            String[] gravityArr = gravity.split(",");
            for (String gStr : gravityArr) {
                if (gStr.equalsIgnoreCase("X_CENTER")) {
                    g |= Gravity.CENTER_HORIZONTAL;
                } else if (gStr.equalsIgnoreCase("Y_CENTER")) {
                    g |= Gravity.CENTER_VERTICAL;
                } else if (gStr.equalsIgnoreCase("CENTER")) {
                    g |= Gravity.CENTER;
                } else if (gStr.equalsIgnoreCase("LEFT")) {
                    g |= Gravity.LEFT;
                } else if (gStr.equalsIgnoreCase("RIGHT")) {
                    g |= Gravity.RIGHT;
                } else if (gStr.equalsIgnoreCase("TOP")) {
                    g |= Gravity.TOP;
                } else if (gStr.equalsIgnoreCase("BOTTOM")) {
                    g |= Gravity.BOTTOM;
                }
            }
        }
        return g;
    }

    public static void loadViewMargin(Context context, FrameLayout.LayoutParams lp, String margin) {
        if (!TextUtils.isEmpty(margin)) {
            String[] marginArr = margin.split(","); // l, t, r, b
            if (marginArr.length != 4)
                return;
            int l = (int) IdentifierUtuils.getIdentifierWidthDimen(context, marginArr[0]);
            int t = (int) IdentifierUtuils.getIdentifierHeightDimen(context, marginArr[1]);
            int r = (int) IdentifierUtuils.getIdentifierWidthDimen(context, marginArr[2]);
            int b = (int) IdentifierUtuils.getIdentifierHeightDimen(context, marginArr[3]);
            lp.leftMargin = l;
            lp.topMargin = t;
            lp.rightMargin = r;
            lp.bottomMargin = b;
        }
    }

    public static void loadViewSize(Context context, FrameLayout.LayoutParams lp, String size) {
        if (!TextUtils.isEmpty(size)) {
            String[] sizeArr = size.split(","); // x, y, w, h
            if (sizeArr.length != 2)
                return;
            // w.
            String wStr = sizeArr[0];
            if (isNumeric(wStr)) {
                int w = (int) IdentifierUtuils.getIdentifierWidthDimen(context, wStr);
                lp.width = w;
            } else if (wStr.equals("MATCH")) {
                lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            }
            // h.
            String hStr = sizeArr[1];
            if (isNumeric(hStr)) {
                int h = (int) IdentifierUtuils.getIdentifierHeightDimen(context, hStr);
                lp.height = h;
            } else if (hStr.equals("MATCH")) {
                lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
            }
        }
    }

    public static void setViewImage(Context context, View view, String name, int defulatID) {
        if (!TextUtils.isEmpty(name)) {
            if (!name.startsWith("http://")) {
                int upRes = IdentifierUtuils.getIdentifierDrawable(context, name, defulatID);
                view.setBackgroundResource(upRes);
            } else {
                // 加载网络图片到view.
            }
        } else {
            view.setBackgroundResource(defulatID);
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static int getIdentifierTextColor(String colorName, int defulatColor) {
        if (!TextUtils.isEmpty(colorName) && colorName.startsWith("#")) {
            try {
                int color = Color.parseColor(colorName);
                return color;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defulatColor;
    }

    public static float getIdentifierTextSize(Context context, String name) {
        float textSize = getIdentifierWidthDimen(context, name);
        if (textSize <= 0)
            textSize = 20;
        return textSize;
    }

    public static float getIdentifierWidthDimen(Context context, String name) {
        try {
            if (!TextUtils.isEmpty(name) && isNumeric(name) && !name.equals("0")) {
                if (!name.startsWith("w_"))
                    name = "w_" + name;
                int id = getIdentifierDimen(context, name);
                float pixelsize = context.getResources().getDimensionPixelSize(id);
//                float pixeloffset = context.getResources().getDimensionPixelOffset(id);
//                float dimension = context.getResources().getDimension(id);
                return pixelsize;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static float getIdentifierHeightDimen(Context context, String name) {
        try {
            if (!TextUtils.isEmpty(name) && isNumeric(name) && !name.equals("0")) {
                if (!name.startsWith("h_"))
                    name = "h_" + name;
                int id = getIdentifierDimen(context, name);
                return context.getResources().getDimensionPixelSize(id);
//                return context.getResources().getDimensionPixelOffset(id);
//                return context.getResources().getDimension(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getIdentifierDimen(Context context, String name) {
        int resid = context.getResources().getIdentifier(name, "dimen", context.getPackageName());
        return resid;
    }

    public static int getIdentifierDrawable(Context context, String name, int defulatID) {
        if (!TextUtils.isEmpty(name)) {
            int resid = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
            return resid;
        }
        return defulatID;
    }

    public static int getIdentifierString(Context context, String name) {
        int resid = context.getResources().getIdentifier(name, "string", context.getPackageName());
        return resid;
    }

}
