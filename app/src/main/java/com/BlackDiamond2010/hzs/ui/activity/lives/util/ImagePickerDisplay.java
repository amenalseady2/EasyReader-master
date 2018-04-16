package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import android.content.Context;
import android.widget.ImageView;

import com.BlackDiamond2010.hzs.utils.GlideUtils;
import com.jaiky.imagespickers.ImageLoader;

/**
 * 配合图片选择器
 * 实现Imageloader
 * */
public class ImagePickerDisplay implements ImageLoader {

    private static final long serialVersionUID = 1L;

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        GlideUtils.loadImage(2,path,imageView);

//        Glide.with(context)
//                .load(path)
//                .placeholder(R.drawable.d)
//                .centerCrop()
//                .into(imageView);
    }

}
