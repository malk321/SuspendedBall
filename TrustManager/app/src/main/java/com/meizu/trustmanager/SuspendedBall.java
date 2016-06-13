package com.meizu.trustmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by miaoshoufei on 16-6-13.
 */
public class SuspendedBall {
    private Context mContext;
    private View mSuspendBall;
    private WindowManager mWindowManager;
    private View mContentView;
    private float offsetX, offsetY;
    private WindowManager.LayoutParams mLayoutParams;
    private DisplayMetrics mDisplayMetrics;

    public SuspendedBall(Context context) {
        this(context, null);
    }

    public SuspendedBall (Context context, View suspendedBall) {
        mContext = context;
        setSuspendedBall(suspendedBall);
        initWindowManager();
        initLayoutParams();
    }

    public void setSuspendedBall(View suspendedBall) {
        if (suspendedBall != null) {
            mSuspendBall = suspendedBall;
            setContentView(mSuspendBall);
        }
    }

    public void initLayoutParams() {
        getLayoutParams().flags = getLayoutParams().flags
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        getLayoutParams().dimAmount = 0.2f;
        getLayoutParams().type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;
        getLayoutParams().width = WindowManager.LayoutParams.WRAP_CONTENT;
        getLayoutParams().gravity = Gravity.TOP | Gravity.LEFT;
        offsetX = 0;
        offsetY = getStatusBarHeight(mContext);
        getLayoutParams().x = (int) (mDisplayMetrics.widthPixels - offsetX);
        getLayoutParams().y = (int) (mDisplayMetrics.heightPixels - offsetY);
    }

    public void setContentView(View contentView) {
        if (contentView != null) {
//            getWindowManager().removeView(contentView);
            createContentView(contentView);
            getWindowManager().addView(mContentView, getLayoutParams());
            updateLocation(getDisplayMetrics().widthPixels / 2, getDisplayMetrics().heightPixels / 2 ,true);
        }
    }

    public View getContentView() {
        return mContentView;
    }

    public void createContentView(View contentView) {
        mContentView = contentView;
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        offsetY = getStatusBarHeight(mContext) + contentView.getMeasuredHeight() / 2;
        offsetX = contentView.getMeasuredWidth() / 2;
        contentView.setOnTouchListener(new WindowTouchListener());
    }

    public WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public WindowManager.LayoutParams getLayoutParams() {
        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            initLayoutParams();
        }
        return mLayoutParams;
    }

    public DisplayMetrics getDisplayMetrics() {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        }
        return mDisplayMetrics;
    }

    /**
     * 获取状态栏的高度
     * */
    public int getStatusBarHeight(Context context) {
        int height = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            height = context.getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    public void updateLocation(float x, float y, boolean offset) {
        if (getContentView() != null) {
            if (offset) {
                getLayoutParams().x = (int) (x - offsetX);
                getLayoutParams().y = (int) (y - offsetY);
            } else {
                getLayoutParams().x = (int) x;
                getLayoutParams().y = (int) y;
            }
            getWindowManager().updateViewLayout(mContentView, getLayoutParams());
        }
    }

    private void initWindowManager() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mDisplayMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
    }






    private class WindowTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_OUTSIDE:
//                    return true;
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
