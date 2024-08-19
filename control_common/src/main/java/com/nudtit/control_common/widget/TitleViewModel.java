package com.nudtit.control_common.widget;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.nudtit.control_common.R;
import com.nudtit.lib_common.utils.Utils;


public class TitleViewModel {
    public String title;
    public String rightTitle;
    public View.OnClickListener onClickListener;
    public int textColor;
    public int rightVisibility = View.GONE;
    public Drawable rightTvBackground;
    public Drawable rightIvBackground;
    public Drawable leftBackBackground;
    public Drawable layoutBg=ContextCompat.getDrawable(Utils.getContext(), R.color.blue_color);

    public TitleViewModel(String title, View.OnClickListener onClickListener) {
        this.title = title;
        this.onClickListener = onClickListener;
        this.textColor = Utils.getColor(R.color.white);
        this.leftBackBackground = ContextCompat.getDrawable(Utils.getContext(), R.mipmap.ic_common_setting_back);
    }

    /**
     * @param title           标题
     * @param titleColor      标题颜色
     * @param onClickListener 点击事件
     */
    public TitleViewModel(String title, int titleColor, View.OnClickListener onClickListener) {
        this.title = title;
        this.onClickListener = onClickListener;
        this.textColor = Utils.getColor(titleColor);
        this.leftBackBackground = ContextCompat.getDrawable(Utils.getContext(), R.mipmap.ic_common_setting_back);
    }

    /**
     * @param title             标题
     * @param color             标题颜色
     * @param leftBackgroundRes 左侧返回按钮图标
     * @param onClickListener   点击事件
     */
    public TitleViewModel(String title, int color, int leftBackgroundRes, View.OnClickListener onClickListener) {
        this.title = title;
        this.onClickListener = onClickListener;
        this.textColor = Utils.getColor(color);
        this.leftBackBackground = ContextCompat.getDrawable(Utils.getContext(), leftBackgroundRes);
    }


    /**
     * 设置整个控件背景
     *
     * @param bg
     */
    public TitleViewModel setViewBg(int bg) {
        this.layoutBg = ContextCompat.getDrawable(Utils.getContext(), bg);
        return this;
    }

    /**
     * 设置右侧图片背景
     *
     * @param bg
     */
    public TitleViewModel setRightIvBg(int bg) {
        this.rightIvBackground = ContextCompat.getDrawable(Utils.getContext(), bg);
        return this;
    }

    /**
     * 设置右侧文字及背景
     * 必须一起设置
     *
     * @param bg
     */
    public TitleViewModel setRightTvData(String text, int bg) {
        this.rightTvBackground = ContextCompat.getDrawable(Utils.getContext(), bg);
        this.rightTitle = text;
        return this;
    }
}
