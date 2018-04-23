package com.su.botanywarzombies.view;

import com.su.botanywarzombies.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final String TAG = GameView.class.getSimpleName();

    private int startY = 50;
    private int endY = 50;
    
    private boolean gameRunFlag;

    private Context mContext;
    private Bitmap mIcon;
    // ��ͼ����
    private Paint mPaint;
    // ��ͼ����
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    public GameView(Context context) {
        super(context);
        this.mContext = context;
        
        gameRunFlag = true;
        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        // ���ò����ص��¼�
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.d(TAG, "surfaceCreated");
        mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        
        
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
                    
                    // ����
                    mCanvas.drawRect(0, 0, 480, 320, mPaint);

                    int color = mPaint.getColor();
                    mPaint.setColor(Color.RED);

                    mCanvas.drawBitmap(mIcon, 100, 100, mPaint);
                    mCanvas.drawRect(50, startY, 100, 100, mPaint);
                    // ��ϰ�߻��ʸ�λ
                    mPaint.setColor(color);
                    
                    startY ++;
                    endY ++;
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
