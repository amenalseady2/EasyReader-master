package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 项目名称：aicaomei_user_mvvm
 * 类描述： 字符串工具类
 * 创建人：yq
 * 创建时间：2016/7/14 11:22
 * 修改人：yq
 * 修改时间：2016/7/14 11:22
 * 修改备注：
 */
public class StringUtil {
    public static final int APPLICATION = 0;
    public static final int BROADCAST = 1;
    public static final int SERVICE = 2;
    public static final int ACTIVITY = 3;

    /**
     * 获取字体长度
     *
     * @param textView
     * @return
     */
    public static int getTextLen(TextView textView) {
        TextPaint paint = textView.getPaint();
        return (int) Layout.getDesiredWidth(textView.getText().toString(), 0,
                textView.getText().length(), paint);
    }

    /**
     * 给某段支付设置下划线
     */
    public static SpannableString underLineHight(String str, String underLineStr) {
        if (!str.contains(underLineStr)) {
            return null;
        }
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString(str);
        int index = str.indexOf(underLineStr);
        //设置背景颜色, StrikethroughSpan()是设置中划线
        sp.setSpan(new UnderlineSpan(), index, index + underLineStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 高亮所有关键字
     *
     * @param str 这个字符串
     * @param key 关键字
     */
    public static SpannableString highlightKeyword(String str, String key, int highlightColor) {
        if (!str.contains(key)) {
            return null;
        }
        SpannableString sp = new SpannableString(str);
        key = Pattern.quote(key);
        Pattern p = Pattern.compile(key);
        Matcher m = p.matcher(str);

        while (m.find()) {  //通过正则查找，逐个高亮
            int start = m.start();
            int end = m.end();
            sp.setSpan(new ForegroundColorSpan(highlightColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }

    /**
     * 创建一个含有超链接的字符串
     *
     * @param text      整段字符串
     * @param clickText 含有超链接的字符
     * @param url       超链接
     */
    public static SpannableString createLinkText(String text, String clickText, String url) {
        if (!text.contains(clickText)) {
            return null;
        }
        SpannableString sp = new SpannableString(text);
        int index = text.indexOf(clickText);
        // 设置超链接
        sp.setSpan(new URLSpan(url), index, index + clickText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 将缓存的key转换为hash码
     *
     * @param key 缓存的key
     * @return 转换后的key的值, 系统便是通过该key来读写缓存
     */
    public static String keyToHashKey(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 读取Activity节点的meta-data
     */
    public static String getActivityMetaData(Activity activity, String key) {
        try {
            return activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA)
                    .metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Service节点的meta-data
     *
     * @param serviceClazz 服务的class
     */
    public static String getServiceMetaData(Context context, Class<? extends Service> serviceClazz, String key) {
        try {
            return context.getPackageManager().getServiceInfo(new ComponentName(context, serviceClazz), PackageManager.GET_META_DATA)
                    .metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取BroadCast节点meta-data数据
     *
     * @param receiverClazz 广播接收器的class
     */
    public static String getBroadCasetMetaData(Context context, Class<? extends BroadcastReceiver> receiverClazz, String key) {
        try {
            return context.getPackageManager().getReceiverInfo(new ComponentName(context, receiverClazz), PackageManager.GET_META_DATA)
                    .metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Application节点的meta-data数据
     */
    public static String getApplicationMetaData(Context context, String key) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从XML读取字符串
     *
     * @param context
     * @param id      字符串id
     * @return
     */
    public static String getStringFromXML(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 从xml读取字符串数组
     *
     * @param context
     * @param id
     * @return
     */
    public static String[] getStringArrayFromXML(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    /**
     * 将字符串数组转换为list
     *
     * @param strArray
     * @return
     */
    public static List<String> stringArray2List(String[] strArray) {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, strArray);
        return list;
    }

    /**
     * 高亮整段字符串
     */
    public static SpannableStringBuilder highLightStr(String str, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(color), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * 高亮代码片段
     *
     * @param str          整段字符串
     * @param highLightStr 要高亮的代码段
     * @param color        高亮颜色
     * @return
     */
    public static SpannableStringBuilder highLightStr(String str, String highLightStr, int color) {
        int start = str.indexOf(highLightStr);
        if (start == -1) {
            return null;
        }
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        // new BackgroundColorSpan(Color.RED)背景高亮
        // ForegroundColorSpan(Color.RED) 字体高亮
        style.setSpan(new ForegroundColorSpan(color), start, start
                + highLightStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * 字符串转dbouble
     *
     * @param str
     * @return
     */
    public static double strToDouble(String str) {
        // double d = Double.parseDouble(str);

		/* 以下代码处理精度问题 */
        BigDecimal bDeci = new BigDecimal(str);
        // BigDecimal chushu =new BigDecimal(100000000);
        // BigDecimal result =bDeci.divide(chushu,new
        // MathContext(4));//MathConText(4)表示结果精确4位！
        // return result.doubleValue() * 100000000;
        return bDeci.doubleValue();
    }

    /**
     * 将普通字符串转换为16位进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (byte aSrc : src) {
            buffer[0] = Character.forDigit((aSrc >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(aSrc & 0x0F, 16);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    /**
     * 把字符串长度加满16位
     *
     * @param str
     * @return 16位长度的字符串
     */
    public static String addStrLenTo16(String str) {
        //由于汉字的特殊性，长度要用byte来判断
        for (int i = str.getBytes().length; i < 16; i++) {
            str += '\0';
        }
        return str;
    }

    /**
     * 获取对象名
     *
     * @param obj 对象
     * @return 对象名
     */
    public static String getClassName(Object obj) {
        String arrays[] = obj.getClass().getName().split("\\.");
        return arrays[arrays.length - 1];
    }

    /**
     * 判断对象是否为 空<BR>
     * <B>只要有一个不为空 就返回 false</B>
     *
     * @param objs
     * @return
     * @date 2015年3月1日 下午4:16:53
     */
    public static boolean isEmpty(Object... objs) {
        if (objs == null) {
            return true;
        } else {
            for (Object obj : objs) {
                if (obj != null) {
                    if (obj instanceof CharSequence) {
                        if (!isEmpty((CharSequence) obj)) {
                            return false;
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     * 判断字符串是否为 空
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        if (cs == null || cs.toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断前面的字符串和后面的字符串数组是否不相等<BR>
     * <B>如果有一个与前面字符串相等则返回false 否则返回true</B>
     *
     * @param cs
     * @param args
     * @return
     * @date 2015年3月1日 下午4:19:07
     */
    public static boolean isNotEquals(CharSequence cs, CharSequence... args) {
        if (isEmpty(cs) || args == null) {
            return false;
        }
        for (CharSequence temp : args) {
            if (cs.equals(temp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断前面的字符串和后面的字符串数组中的某一个是否相等<BR>
     * <B>如果有一个与前面字符串相等则返回true 否则返回false</B>
     *
     * @param cs
     * @param args
     * @return
     * @date 2015年3月1日 下午4:20:51
     */
    public static boolean isEqual(CharSequence cs, CharSequence... args) {
        if (cs == null) {
            if (args == null) {
                return true;
            } else
                return false;
        } else {
            if (args == null) {
                return false;
            } else {
                for (CharSequence temp : args) {
                    if (cs.equals(temp)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }

    /**
     * String 类型
     */
    public enum StringType {
        NULL,
        UNKNOW,
        EMAIL,
        PHONE
    }


    private static final String REX_NICK = "^[\\u4E00-\\u9FA5a-zA-Z0-9]{1,20}$";

    public static boolean isNickName(String str) {
        Pattern pattern = Pattern.compile(REX_NICK);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 字符串按指定长度隔开
     *
     * @param @param  subLength  间隔长度
     * @param @param  str  需要间隔字符串
     * @param @return
     * @return String
     * @author yeqing
     * @date 2016-5-23  下午4:36:01
     */
    public static String StringFilter(int subLength, String str) {
        StringBuffer sb = new StringBuffer();
        int count = 0;
        int length = str.length();
        if (!isEmpty(str) && subLength > 0) {
            count = length / subLength;
            for (int i = 0; i < count; i++) {
                sb.append(str.subSequence(i * subLength, i * subLength + subLength) + " ");
            }
            if (length > count * subLength) {
                sb.append(str.subSequence(count * subLength, length));
            }
        }
        return sb.toString();

    }

    /**
     * 小数点后只输入两位小数
     *
     * @author yeqing
     * @date 2015-9-19  下午2:15:57
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }

    /**
     *
     *
     * md5加密
     *
     * @return
     */
    public static String getMD5String(String str){

        MessageDigest md5 =null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray =new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue =new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) &0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();


    }


    public static String subStr(String str, int start, int end) {
        return str.substring(start, end);
    }
}
