package com.BlackDiamond2010.hzs.ui.activity.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.http.LifeSubscription;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.StatusBarUtil;
import com.BlackDiamond2010.hzs.view.XDialog;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.gyf.barlibrary.ImmersionBar.with;

/**
 * Created by quantan.liu on 2017/3/21.
 */

public abstract class BaseActivity extends AppCompatActivity implements LifeSubscription {

    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<AppCompatActivity>();

    public static BaseActivity activity;

    //一下变量用于从左边滑动到右边关闭的变量   类似ios自带的关闭效果
    private int endX;
    private int startX;
    private int deltaX;
    private int endY;
    private int startY;
    private int deltaY;

    //    private View decorView;
    private VelocityTracker mVelocityTracker;
    private boolean isClose = true;

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mVelocityTracker = mVelocityTracker.obtain();
//        decorView = getWindow().getDecorView();
        synchronized (mActivities) {
            mActivities.add(this);
        }
//        setStatusBar(R.color.colorWhite,true,R.color.black);
//        StatusBarUtil.setStatusBarTranslucent(R.color.colorWhite,this,true);
        StatusBarUtil.setStatusBarColor(true, R.color.colorWhite, this);
    }



    /**
     * 修改状态栏颜色
     *
     * @param statusBarColor 状态栏颜色
     * @param isDarkFont     状态栏字体是否为深色
     * @param fontColor      状态栏字体颜色
     */

    public void setStatusBar(int statusBarColor, boolean isDarkFont, int fontColor) {
        with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .statusBarColor(statusBarColor)     //状态栏颜色，不写默认透明色
                .statusBarDarkFont(isDarkFont)   //状态栏字体是深色，不写默认为亮色
                .flymeOSStatusBarFontColor(fontColor)  //修改flyme OS状态栏字体颜色
                .statusBarColorTransform(statusBarColor)  //状态栏变色后的颜色
                .init();  //必须调用方可沉浸式
    }

    /*
    * 设置标题和对返回键的监听
    * **/
    protected void setTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager imm = (InputMethodManager) MyApplication.instance.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive() && v != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                finish();
            }

        });
    }


    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        setToolBar(toolbar, title, true);
    }

    /**
     * 子类可以直接用
     *
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title, boolean enable) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);//1.显示toolbar的返回按钮左上角图标
        getSupportActionBar().setDisplayShowHomeEnabled(enable);//2.显示toolbar的返回按钮12要一起用
        getSupportActionBar().setDisplayShowTitleEnabled(enable);//显示toolbar的标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private CompositeSubscription mCompositeSubscription;

    //用于添加rx的监听的在onDestroy中记得关闭不然会内存泄漏。
    @Override
    public void bindSubscription(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

//----------------------网络请求开始-----------------

    /**
     * 添加请求 进一步简化
     *
     * @param observable 方法
     * @param subscriber 回调
     */
    protected <T> T addMainSubscription(Observable<T> observable, Subscriber<T> subscriber) {
        addSubscription(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
        return null;
    }
    //网络请求管理
//    private CompositeSubscription mCompositeSubscription;

    private void addSubscription(Subscription s) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(s);
    }

    //----------------------网络请求开始-----------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
            if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
                this.mCompositeSubscription.unsubscribe();
            }
    }

    public void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //下面的用于侧滑关闭Activity
    public void touchFinish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
    }

    /**
     * 关闭activity时执行这个动画
     *
     * @param deltaX
     */
//    public void closeAnimator(int deltaX) {
//        if (isClose) {
//            ValueAnimator animator = ValueAnimator.ofInt(deltaX, decorView.getWidth());
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int value = (Integer) animation.getAnimatedValue();
////                decorView.layout(value, 0, value + decorView.getWidth(), decorView.getHeight());
//                    decorView.scrollTo(-value, 0);
//                }
//            });
//            animator.addListener(new Animator.AnimatorListener() {
//
//                @Override
//                public void onAnimationStart(Animator arg0) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator arg0) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator arg0) {
//                    if (isClose) {
//                        touchFinish();
//                    }
//                }
//
//                @Override
//                public void onAnimationCancel(Animator arg0) {
//
//                }
//            });
//            animator.setDuration(300);
//            animator.start();
//        } else {
//            ValueAnimator animator = ValueAnimator.ofInt(deltaX, 0);
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int value = (Integer) animation.getAnimatedValue();
////                decorView.layout(value, 0, value + decorView.getWidth(), decorView.getHeight());
//                    decorView.scrollTo(-value, 0);
//                }
//            });
//            animator.setDuration(300);
//            animator.start();
//        }
//    }

    //    需要测滑关闭时在打开这个注释
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        mVelocityTracker.addMovement(ev);
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = (int) ev.getRawX();
//                startY = (int) ev.getRawY();
//                if (startX < getWindow().getDecorView().getWidth() / 32) {
//                    return true;
//                } else {
//                    return super.dispatchTouchEvent(ev);
//                }
//            case MotionEvent.ACTION_MOVE:
//                endX = (int) ev.getRawX();
//                endY = (int) ev.getRawY();
//                deltaX = endX - startX;
//                deltaY = endY - startY;
//                if (deltaX > deltaY && startX < getWindow().getDecorView().getWidth() / 32) {
//                    decorView.scrollTo(-deltaX, 0);
//                    decorView.getBackground().setColorFilter((Integer) evaluateColor((float) deltaX / (float) decorView.getWidth(), Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
//                    return true;
//                } else {
//                    return super.dispatchTouchEvent(ev);
//                }
//            case MotionEvent.ACTION_UP:
//                mVelocityTracker.computeCurrentVelocity(1000);
//                float xVelocity = mVelocityTracker.getXVelocity();
//                if (-25 < xVelocity && xVelocity <= 50 && deltaX > decorView.getWidth() / 3 && startX < getWindow().getDecorView().getWidth() / 32
//                        || xVelocity > 50 && startX < getWindow().getDecorView().getWidth() / 32) {
//                    isClose = true;
//                    closeAnimator(deltaX);
//                    return true;
//                } else {
//                    if (deltaX > 0 && startX < getWindow().getDecorView().getWidth() / 32) {
//                        isClose = false;
//                        closeAnimator(deltaX);
//                        return true;
//                    } else {
//                        if (startX < getWindow().getDecorView().getWidth() / 32) {
//                            decorView.scrollTo(0, 0);
//                        }
//                        return super.dispatchTouchEvent(ev);
//                    }
//                }
//            case MotionEvent.ACTION_CANCEL:
//                mVelocityTracker.clear();
//                mVelocityTracker.recycle();
//                return super.dispatchTouchEvent(ev);
//        }
//        return super.dispatchTouchEvent(ev);
//    }


    /**
     * 颜色变化过度
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }


    //--------------------------加载提示框---------------------

    private XDialog mDialog;

    public void createDialog() {
        if (mDialog == null) {
            mDialog = new XDialog(this, R.style.Dialog);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void showLoadingDialog() {
        createDialog();
        if (!isFinishing()) {
            mDialog.start();
        }
    }

    public void showLoadingDialog(String msg) {
        createDialog();
        if (!isFinishing()) {
            mDialog.setLoadingText(msg);
            mDialog.start();
        }
    }

    public void dismissDialog() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

//    -------------------Toast-------------------

    public void mackToastSHORT(String str, Context context) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public void mackToastLONG(String str, Context context) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

}
