package com.BlackDiamond2010.hzs.ui.activity.lives.util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地存储工具
 * <BR>
 * <LI>文件名请修改TsouConfig.PREFERENCE_NAME
 * <LI>使用前需调用init(Context ctx)初始化，初始化一般放在Application中
 * @author yq
 * @date 2015年3月1日 下午5:07:56
 */
public class PreferenceHelper {

	private SharedPreferences mPreferences;
	private static PreferenceHelper mIntance;
	private String PREFERENCE_NAME="PREFERENCE_NAME";/** 本地存储文件名 */

	private PreferenceHelper(Context ctx) {
		if (mPreferences == null) {
			mPreferences = ctx.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
		}
	}

	/**
	 * 初始化本地存储工具
	 * @author yq
	 * @date 2015年3月1日 下午5:23:35 
	 * @param ctx
	 */
	public static void init(Context ctx) {
		if (mIntance == null) {
			mIntance = new PreferenceHelper(ctx);
		}
	}
	
	/**
	 * 是否初始化
	 * @author yq
	 * @date 2015-3-9 下午2:43:07 
	 * @return
	 */
	public static boolean isInit(){
		return mIntance != null;
	}

	public static PreferenceHelper getIntance() {
		if (mIntance == null) {
			throw new IllegalArgumentException("please invoke init(Context) before used");
		}
		return mIntance;
	}
	/**
	 * 本地保存String类型数据
	 * @author yq
	 * @date 2015年3月1日 下午5:30:19 
	 * @param key 保存关键字
	 * @param value 保存值
	 * @return
	 */
	public boolean saveString(String key, String value) {
		return mPreferences.edit().putString(key, value).commit();
	}
	
	/**
	 * 本地保存整型数据
	 * @author yeqing
	 * @date   2016-5-7  下午4:14:47
	 * @param @param key
	 * @param @param value
	 * @param @return    
	 * @return boolean
	 */
	public boolean saveInt(String key, int value) {
		return mPreferences.edit().putInt(key, value).commit();
	}

	/**
	 * 本地保存Boolean类型数据 
	 * @author yq
	 * @date 2015年3月1日 下午5:31:07 
	 * @param key 关键字
	 * @param value 值
	 * @return
	 */
	public boolean saveBoolean(String key, boolean value) {
		return mPreferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * 读取String类型数据，如果关键字不存在返回""
	 * @author yq
	 * @date 2015年3月1日 下午5:31:51 
	 * @param key 关键字
	 * @return
	 */
	public String readString(String key) {
		return readString(key, "");
	}
	/**
	 * 读取int型数据
	 * @author yeqing
	 * @date   2016-5-7  下午4:15:59
	 * @param @param key
	 * @param @return    
	 * @return String
	 */
	public int readInt(String key) {
		return mPreferences.getInt(key,0);
	}
	
	/**
	 * 读取String类型数据
	 * @author yq
	 * @date 2015年3月1日 下午5:33:29 
	 * @param key 关键字
	 * @param defValue 如果关键字不存在，返回的默认数据
	 * @return
	 */
	public String readString(String key, String defValue){
		return mPreferences.getString(key, defValue);
	}

	/**
	 * 读取Boolean类型数据，如果关键字不存在返回false
	 * @author yq
	 * @date 2015年3月1日 下午5:34:52 
	 * @param key 关键字
	 * @return
	 */
	public boolean readBoolean(String key) {
		return readBoolean(key, false);
	}

	/**
	 * 读取Boolean类型数据
	 * @author yq
	 * @date 2015年3月1日 下午5:35:21 
	 * @param key 关键字
	 * @param def 如果关键字不存在，返回的默认数据
	 * @return
	 */
	public boolean readBoolean(String key, boolean def) {
		return mPreferences.getBoolean(key, def);
	}

}
