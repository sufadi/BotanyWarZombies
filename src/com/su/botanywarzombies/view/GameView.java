package com.su.botanywarzombies.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.entity.EmplacePea;
import com.su.botanywarzombies.entity.SeedFlower;
import com.su.botanywarzombies.entity.SeedPea;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.TouchAble;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final String TAG = GameView.class.getSimpleName();

    private boolean gameRunFlag;

    private Context mContext;
    // ��ͼ����
    private Paint mPaint;
    // ��ͼ����
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    private static GameView gameView;

    // ��һ��ͼ��
    private ArrayList<BaseModel> gameLayout1;
    // �ڶ���ͼ��ļ���
    private ArrayList<BaseModel> gameLayout2;

    public GameView(Context context) {
        super(context);
        this.mContext = context;
        gameView = this;

        gameRunFlag = true;
        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        // ���ò����ص��¼�
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        creatElement();

        Log.d(TAG, "surfaceCreated");
        new Thread(this).start();
    }

    private void creatElement() {
        gameLayout2 = new ArrayList<BaseModel>();
        gameLayout1 = new ArrayList<BaseModel>();

        // ״̬��λ�� + һ��ͼƬ���
        int statusX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;
        int locationX = statusX + Config.seedFlower.getWidth();
        SeedFlower seedFlower = new SeedFlower(locationX, 0);
        gameLayout2.add(seedFlower);

        // �㶹����
        locationX = locationX + Config.seedPea.getWidth();
        SeedPea seedPea = new SeedPea(locationX, 0);
        gameLayout2.add(seedPea);

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
                    mCanvas.drawBitmap(Config.gameBg, 0, 0, mPaint);
                    // ���ÿ�Ƭ����ʼ X ������ (������-��Ƭ���) / 2
                    mCanvas.drawBitmap(Config.seekBank, (Config.screenWidth - Config.seekBank.getWidth()) / 2, 0, mPaint);

                    onDrawing(mCanvas);

                } catch (Exception e) {
                    // e.printStackTrace();
                } finally {
                    // �������ύ
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

            }

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void updateData() {

    }

    private void onDrawing(Canvas mCanvas) {
        for (BaseModel model : gameLayout2) {
            model.drawSelf(mCanvas, mPaint);
        }

        // �󻭵ĻḲ���Ȼ��ģ���λ����gameLayout2����
        if (gameLayout1 != null && !gameLayout1.isEmpty()) {
            for (BaseModel model : gameLayout1) {
                model.drawSelf(mCanvas, mPaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // ��һͼ�㣬����ͼ�ͷ���״̬���Ŀ�Ƭ
        for (BaseModel model : gameLayout1) {
            if (model instanceof TouchAble) {
                if (((TouchAble) model).onTouch(event)) {
                    // true ��ʾ�����¼�����Ϊֹ������Ӧ
                    return true;
                }
            }
        }

        // �ڶ�ͼ�㣬���ſ�Ƭ
        for (BaseModel model : gameLayout2) {
            if (model instanceof TouchAble) {
                if (((TouchAble) model).onTouch(event)) {
                    // true ��ʾ�����¼�����Ϊֹ������Ӧ
                    return true;
                }
            }
        }

        return false;
    }

    public static GameView getInstance() {
        return gameView;
    }

    public void applyEmplacePea(int locationX, int locationY) {
        synchronized (mSurfaceHolder) {
            // ������뱻����״̬��ֲ��
            Log.d("sufadi", "applyEmplacePea add EmplacePea");

            gameLayout1.add(new EmplacePea(locationX, locationY));
        }
    }
}
