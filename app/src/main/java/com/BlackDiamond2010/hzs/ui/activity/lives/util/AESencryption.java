package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by xiawei on 2017/8/4.
 * AES加密
 */

public class AESencryption {
    private static final String ECB_PASSWORD  = "Cecb-cm@#2g%(^$}";

    /**
     * @param content 所有的参数都放在params  通过map.put(key,value)
     *  String s1 = "parent=";String s2 = "parent=&prent2=1";要重新过滤上面两种情况
     * */
    public static String encryption(String content){
        StringBuffer buffer = new StringBuffer();
        boolean hasP = false; //是否有有效参数的flag
        if(content.contains("&")){
            String[] keyP = content.split("&");
            for (int i = 0;i<keyP.length;i++){
                String[] nameV = keyP[i].split("=");
                if(nameV.length == 2){
                    buffer.append(nameV[0]+"="+nameV[1]+"&");
                    hasP = true ;
                }
            }
        }else {
            String[] nameV1 = content.split("=");
            if(nameV1.length ==2){
                buffer.append(nameV1[0]+"="+nameV1[1]+"&");
                hasP = true ;
            }
        }
        if (hasP){
            return ecbEncrypt(buffer.toString().substring(0,buffer.toString().length()-1));
        }else{
            return "";
        }
    }

    /**
     * @param content 所有的参数都放在params  通过map.put(key,value)
     * */
    public static String encryptionJSON(String content){

        if (TextUtils.isEmpty(content)||"null".equals(content)){
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        try {
            JSONObject jsonObj = new JSONObject(content);
            JSONArray namesArray = jsonObj.optJSONArray("encodedNames");
            JSONArray valusArray = jsonObj.optJSONArray("encodedValues");
            for (int i = 0 ; i<namesArray.length();i++){
                String name = namesArray.optString(i);
                String value = valusArray.optString(i);
                if(value != null && !"".equals(value)){
                    buffer.append(name+"="+value+"&");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return "null".equals(content)?"":ecbEncrypt(buffer.toString().substring(0,buffer.toString().length()-1));
    }
    /**
     * @param params 所有的参数都放在params  通过map.put(key,value)
     * */
    public static String encryption(Map<String,String> params){
        StringBuffer buffer = new StringBuffer();

        if (params != null) {
            Set<String> s = params.keySet();
            Iterator<String> it = s.iterator();
            // 把key和value 循环的加入到params中
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if(value != null && !"".equals(value)){
                    buffer.append(key+"="+value+"&");
                }
            }
        }
        return ecbEncrypt(buffer.toString().substring(0,buffer.toString().length()-1));
    }


    /**
     * 注解：AES-128-ECB 加密
     * 签名长度相对较短，无换行
     * @param content
     * @return
     * @author
     */
    public  static  String ecbEncrypt(String content) {
        try {
            byte[] raw = ECB_PASSWORD.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
//            return new org.apache.commons.codec1.binary.Base64().encodeToString(encrypted);
            return "";
        } catch (Exception e) {
            return null;
        }
    }

}
