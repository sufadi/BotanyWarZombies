package com.su.botanywarzombies.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final String TAG = GameView.class.getSimpleName();

    private boolean gameRunFlag;

    // 绘图画笔
    private Paint mPaint;
    // 绘图画布
    private Canvas mCanvas;

    private SurfaceHolder mSurfaceHolder;

    public GameView(Context context) {
        super(context);

        gameRunFlag = true;
        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        // 设置操作回调事件
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.d(TAG, "surfaceCreated");
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.d(TAG, "surfaceDestroyed");
        gameRunFlag = false;
    }

    @Override
    public void run() {
        while (gameRunFlag) {
            // 这里需要考虑线程同步
            synchronized (mSurfaceHolder) {
                try {

                    // 锁住画布才能绘图
                    mCanvas = mSurfaceHolder.lockCanvas();

                    int color = mPaint.getColor();
                    mPaint.setColor(Color.RED);

                    mCanvas.drawRect(50, 50, 100, 100, mPaint);
                    // 好习惯画笔复位
                    mPaint.setColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 解锁并提交
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

                try {
                    // 视频达到24帧，1秒24帧图片，肉眼是感受不到变化
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}
