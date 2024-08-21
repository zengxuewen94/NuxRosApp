package com.eaibot.running.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 语言切换
 * Created by 41455 on 2016/10/13.
 */
public class LanguageUtil {
    /**
     * @param localLanguage 当前语言
     * 0:中文
     * 1:英文
     */
    public static void setLanguage(int localLanguage, Resources resources) {

        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (localLanguage==0) {
            //设置英文
            configuration.locale = Locale.US;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        resources.updateConfiguration(configuration, displayMetrics);
    }
}