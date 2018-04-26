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
    // 绘图画笔
    private Paint mPaint;
    // 绘图画布
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;

    private static GameView gameView;

    // 第一层图层
    private ArrayList<BaseModel> gameLayout1;
    // 第二层图层的集合
    private ArrayList<BaseModel> gameLayout2;

    public GameView(Context context) {
        super(context);
        this.mContext = context;
        gameView = this;

        gameRunFlag = true;
        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        // 设置操作回调事件
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

        // 状态栏位置 + 一张图片宽度
        int statusX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;
        int locationX = statusX + Config.seedFlower.getWidth();
        SeedFlower seedFlower = new SeedFlower(locationX, 0);
        gameLayout2.add(seedFlower);

        // 豌豆射手
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
            // 这里需要考虑线程同步
            synchronized (mSurfaceHolder) {
                try {
                    // 锁住画布才能绘图
                    mCanvas = mSurfaceHolder.lockCanvas();
                    mCanvas.drawBitmap(Config.gameBg, 0, 0, mPaint);
                    // 放置卡片的起始 X 坐标是 (界面宽度-卡片宽度) / 2
                    mCanvas.drawBitmap(Config.seekBank, (Config.screenWidth - Config.seekBank.getWidth()) / 2, 0, mPaint);

                    onDrawing(mCanvas);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 解锁并提交
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    private void updateData() {

    }

    private void onDrawing(Canvas mCanvas) {
        for (BaseModel model : gameLayout2) {
            model.drawSelf(mCanvas, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (BaseModel model : gameLayout2) {
            if (model instanceof TouchAble) {
                if (((TouchAble) model).onTouch(event)) {
                    // true 表示触摸事件到此为止不再响应
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
            gameLayout1.add(new EmplacePea(locationX, locationY));
        }
    }
}
