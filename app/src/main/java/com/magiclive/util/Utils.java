package com.magiclive.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.magiclive.R;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static void addShortcut(Context context, Class clazz, String appName, int ic_launcher) {
        // 安装的Intent
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.putExtra("tName", appName);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        shortcutIntent.setClassName(context, clazz.getName());
        //        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 快捷名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getResources().getString(R.string.app_name));
        // 快捷图标是否允许重复
        shortcut.putExtra("duplicate", false);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 快捷图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        // 发送广播
        context.sendBroadcast(shortcut);
    }

    public static void gotoGP(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            Intent launchIntent = new Intent();
            launchIntent.setPackage("com.android.vending");
            launchIntent.setData(Uri.parse("market://details?id=" + appPackageName));
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        } catch (android.content.ActivityNotFoundException anfe) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}