package com.BlackDiamond2010.hzs.ui.activity.lives.cachefile;/**
 * Created by yq on 2016/8/5.
 */


import com.BlackDiamond2010.hzs.ui.activity.lives.util.BaseConfig;

/**
 * 项目名称：Aicaomei_MVVMFrame_Retrofit
 * 类描述：
 * 创建人：yq
 * 创建时间：2016/8/5 15:15
 * 修改人：yq
 * 修改时间：2016/8/5 15:15
 * 修改备注：
 */
public class CacheFile {
    //用户版
    public static final String USER_CACHE_FILE = "UserHttpCache";//网络缓存文件
    public static final String USER_IMAGE_FILE = "UserImageCache";//图片缓存
    public static final String USER_TEMP_FILE = "UserTempCache";//临时文件

    //商户版
    public static final String SHOP_CACHE_FILE = "ShopHttpCache";//网络缓存文件
    public static final String SHOP_IMAGE_FILE = "ShopImageCache";//图片缓存
    public static final String SHOP_TEMP_FILE = "ShopTempCache";//临时文件

    //网络缓存文件
    public static String getCacheFile(){
        if(BaseConfig.APPTYPE.equals(BaseConfig.SHOPAPP)){
            return SHOP_CACHE_FILE;
        }
        return USER_CACHE_FILE;
    }

    //图片缓存件
    public static String getImageFile(){
        if(BaseConfig.APPTYPE.equals(BaseConfig.SHOPAPP)){
            return SHOP_IMAGE_FILE;
        }
        return USER_IMAGE_FILE;
    }

    //临时文件
    public static String getTempFile(){
        if(BaseConfig.APPTYPE.equals(BaseConfig.SHOPAPP)){
            return SHOP_TEMP_FILE;
        }
        return USER_TEMP_FILE;
    }

}
