package com.BlackDiamond2010.hzs.ui.activity.lives.util.ImageScaleUtil;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.BlackDiamond2010.hzs.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

/**
 * 鍗曞紶鍥剧墖鏄剧ずFragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;
    private BitmapUtils bitmapUtils;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

        bitmapUtils = new BitmapUtils(this.getActivity());
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.img_four_bi_three);//鍔犺浇澶辫触鍥剧墖

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bitmapUtils.display(mImageView, mImageUrl, new CustomBitmapLoadCallBack());

//		ImageLoader imageLoader = ImageLoader.getInstance();
//		imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
//		imageLoader.displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {
//				progressBar.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//				String message = null;
//				switch (failReason.getType()) {
//				case IO_ERROR:
//					message = "涓嬭浇閿欒";
//					break;
//				case DECODING_ERROR:
//					message = "鍥剧墖鏃犳硶鏄剧ず";
//					break;
//				case NETWORK_DENIED:
//					message = "缃戠粶鏈夐棶棰橈紝鏃犳硶涓嬭浇";
//					break;
//				case OUT_OF_MEMORY:
//					message = "鍥剧墖澶ぇ鏃犳硶鏄剧ず";
//					break;
//				case UNKNOWN:
//					message = "鏈煡鐨勯敊璇?";
//					break;
//				}
//				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//				progressBar.setVisibility(View.GONE);
//			}
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				progressBar.setVisibility(View.GONE);
//				mAttacher.update();
//			}
//		});
    }

    /**
     * @author Liuj
     * @category 鍥剧墖鍥炶皟鍑芥暟
     */
    public class CustomBitmapLoadCallBack extends
            DefaultBitmapLoadCallBack<ImageView> {

        @Override
        public void onLoading(ImageView container, String uri,
                              BitmapDisplayConfig config, long total, long current) {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadCompleted(ImageView container, String uri,
                                    Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            container.setImageBitmap(bitmap);
            progressBar.setVisibility(View.GONE);
            mAttacher.update();
        }

        @Override
        public void onLoadFailed(ImageView container, String uri,
                                 Drawable drawable) {
            mImageView.setImageResource(R.mipmap.img_four_bi_three);
        }
    }
}
