package com.su.botanywarzombies.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.entity.EmplacePea;
import com.su.botanywarzombies.entity.Pea;
import com.su.botanywarzombies.entity.SeedFlower;
import com.su.botanywarzombies.entity.SeedPea;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.Plant;
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

    // 放置死亡集合
    private ArrayList<BaseModel> deadList;

    // 第一层图层
    private ArrayList<BaseModel> gameLayout1;
    // 第二层图层的集合
    private ArrayList<BaseModel> gameLayout2;

    // 安放植物 跑道1
    private ArrayList<BaseModel> gameLayout4plant0;
    // 安放植物 跑道2
    private ArrayList<BaseModel> gameLayout4plant1;
    // 安放植物 跑道3
    private ArrayList<BaseModel> gameLayout4plant2;
    // 安放植物 跑道4
    private ArrayList<BaseModel> gameLayout4plant3;
    // 安放植物 跑道5
    private ArrayList<BaseModel> gameLayout4plant4;

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
        deadList = new ArrayList<BaseModel>();
        gameLayout2 = new ArrayList<BaseModel>();
        gameLayout1 = new ArrayList<BaseModel>();

        gameLayout4plant0 = new ArrayList<BaseModel>();
        gameLayout4plant1 = new ArrayList<BaseModel>();
        gameLayout4plant2 = new ArrayList<BaseModel>();
        gameLayout4plant3 = new ArrayList<BaseModel>();
        gameLayout4plant4 = new ArrayList<BaseModel>();

        // 状态栏位置 + 一张图片宽度
        int statusX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;
        int locationX = statusX + Config.seedFlower.getWidth();
        SeedFlower seedFlower = new SeedFlower(locationX, 0);
        gameLayout2.add(seedFlower);

        // 豌豆射手
        locationX = locationX + Config.seedPea.getWidth();
        SeedPea seedPea = new SeedPea(locationX, 0);
        gameLayout2.add(seedPea);

        // 初始化可安放卡片的所有有效区域
        final int LINE = 5;
        final int ROW = 9;
        final int TOTAL_ROW = 11;
        final int TOTAL_LINE = 6;
        for (int i = 0; i < LINE; i++) {
            for (int j = 0; j < ROW; j++) {
                int x = (j + 2) * Config.screenWidth / TOTAL_ROW - Config.screenWidth / TOTAL_ROW / 2;
                int y = (i + 1) * Config.screenHeight / TOTAL_LINE;
                Point mPoint = new Point(x, y);

                Config.plantPoint.put(i * (ROW + 1) + j, mPoint);

                if (j == 0) {
                    // 记录每一个可安放区域的跑道Y坐标
                    Config.racWayYpoint[i] = (i + 1) * Config.screenHeight / TOTAL_LINE;
                }
            }

        }
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

                    updateData();
                    onDrawing(mCanvas);

                } catch (Exception e) {
                    // e.printStackTrace();
                } finally {
                    // 解锁并提交
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
        deadList.clear();

        for (BaseModel model : gameLayout1) {
            if (!model.isLive) {
                // 放置死亡的对象
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout2) {
            if (!model.isLive) {
                // 放置死亡的对象
                deadList.add(model);
            }
        }

        // 松开鼠标，无效对象消失
        for (BaseModel model : deadList) {
            if (!model.isLive) {
                // 放置死亡的对象
                gameLayout1.remove(model);
                gameLayout1.remove(model);
            }
        }

    }

    private void onDrawing(Canvas mCanvas) {

        for (BaseModel model : gameLayout4plant0) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4plant1) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4plant2) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4plant3) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4plant4) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout2) {
            model.drawSelf(mCanvas, mPaint);
        }

        // 后画的会覆盖先画的，故位置在gameLayout2下面
        if (gameLayout1 != null && !gameLayout1.isEmpty()) {
            for (BaseModel model : gameLayout1) {
                model.drawSelf(mCanvas, mPaint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 第一图层，背景图和放置状态栏的卡片
        for (BaseModel model : gameLayout1) {
            if (model instanceof TouchAble) {
                if (((TouchAble) model).onTouch(event)) {
                    // true 表示触摸事件到此为止不再响应
                    return true;
                }
            }
        }

        // 第二图层，安放卡片
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
            if (gameRunFlag) {

            }
            // 顶层加入被安放状态的植物
            Log.d("sufadi", "applyEmplacePea add EmplacePea");

            gameLayout1.add(new EmplacePea(locationX, locationY));
        }
    }

    public void applay4Plant(int locationX, int locationY, EmplacePea emplacePea) {
        synchronized (mSurfaceHolder) {
            // 当前卡片中心坐标与可安放集合最近的坐标点
            for (Integer key : Config.plantPoint.keySet()) {
                // 1. 两个卡片中心点距离是一个单元格宽度，这里我们取两个卡片直接画中心线
                // 2. 可放置卡片偏向那，自动放置哪里
                final int TOTAL_ROW = 11;
                final int TOTAL_LINE = 6;
                Point point = Config.plantPoint.get(key);
                if ((Math.abs(locationX - point.x) < Config.screenWidth / TOTAL_ROW / 2) && (Math.abs(locationY - point.y) < Config.screenHeight / TOTAL_LINE / 2)) {
                    int raceIndex = TOTAL_LINE;

                    for (int i = 0; i < Config.racWayYpoint.length; i++) {
                        if (point.y == Config.racWayYpoint[i]) {
                            raceIndex = i;
                        }
                    }

                    if (isExist(key, raceIndex)) {
                        return;
                    }

                    switch (raceIndex) {
                    case 0:
                        gameLayout4plant0.add(new Pea(point.x, point.y, key));
                        break;
                    case 1:
                        gameLayout4plant1.add(new Pea(point.x, point.y, key));
                        break;
                    case 2:
                        gameLayout4plant2.add(new Pea(point.x, point.y, key));
                        break;
                    case 3:
                        gameLayout4plant3.add(new Pea(point.x, point.y, key));
                        break;
                    case 4:
                        gameLayout4plant4.add(new Pea(point.x, point.y, key));
                        break;

                    default:
                        break;
                    }
                }
            }
        }

    }

    private boolean isExist(int key, int raceIndex) {
        switch (raceIndex) {
        case 0:
            return hasMapIndex(key, gameLayout4plant0);
        case 1:
            return hasMapIndex(key, gameLayout4plant1);
        case 2:
            return hasMapIndex(key, gameLayout4plant2);
        case 3:
            return hasMapIndex(key, gameLayout4plant3);
        case 4:
            return hasMapIndex(key, gameLayout4plant4);
        default:
            break;
        }
        return false;
    }

    private boolean hasMapIndex(int key, ArrayList<BaseModel> list) {
        for (BaseModel model : list) {
            if (model instanceof Plant) {
                if (key == model.getMapIndex()) {
                    return true;
                }
            }
        }

        return false;
    }
}
