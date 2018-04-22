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

    // ��ͼ����
    private Paint mPaint;
    // ��ͼ����
    private Canvas mCanvas;

    private SurfaceHolder mSurfaceHolder;

    public GameView(Context context) {
        super(context);

        gameRunFlag = true;
        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        // ���ò����ص��¼�
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
            // ������Ҫ�����߳�ͬ��
            synchronized (mSurfaceHolder) {
                try {

                    // ��ס�������ܻ�ͼ
                    mCanvas = mSurfaceHolder.lockCanvas();

                    int color = mPaint.getColor();
                    mPaint.setColor(Color.RED);

                    mCanvas.drawRect(50, 50, 100, 100, mPaint);
                    // ��ϰ�߻��ʸ�λ
                    mPaint.setColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // �������ύ
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

                try {
                    // ��Ƶ�ﵽ24֡��1��24֡ͼƬ�������Ǹ��ܲ����仯
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
}
