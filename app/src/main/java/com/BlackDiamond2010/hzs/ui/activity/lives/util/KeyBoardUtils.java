package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 项目名称：aicaomei_user_mvvm
 * 类描述： 打开或关闭软键盘
 * 创建人：yq
 * 创建时间：2016/7/14 11:22
 * 修改人：yq
 * 修改时间：2016/7/14 11:22
 * 修改备注：
 */
public class KeyBoardUtils {
    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘 <LI>不能用在onStop和onDestroy里，需要写在事件中
     * @param context
     *            上下文
     * @param focusView
     *            获取焦点的View，可通过**.getCurrentFocus()方法获取
     */
    public static void closeKeyboard(Context context, View focusView) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()&& focusView != null)   {
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }
}  