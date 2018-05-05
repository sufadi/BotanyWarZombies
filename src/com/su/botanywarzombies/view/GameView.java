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
import com.su.botanywarzombies.entity.EmplaceFlower;
import com.su.botanywarzombies.entity.EmplacePea;
import com.su.botanywarzombies.entity.Flower;
import com.su.botanywarzombies.entity.Pea;
import com.su.botanywarzombies.entity.SeedFlower;
import com.su.botanywarzombies.entity.SeedPea;
import com.su.botanywarzombies.entity.Sun;
import com.su.botanywarzombies.entity.Zombie;
import com.su.botanywarzombies.entity.ZombieManager;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.Plant;
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

    // ������������
    private ArrayList<BaseModel> deadList;

    // ��ʬ������
    private ZombieManager mZombieManager;

    // ��һ��ͼ��
    private ArrayList<BaseModel> gameLayout1;
    // �ڶ���ͼ��ļ���
    private ArrayList<BaseModel> gameLayout2;
    // ������ͼ��ļ��ϣ�ȽȽ�����С̫��
    private ArrayList<BaseModel> gameLayout3;

    // ����ֲ�� �ܵ�1
    private ArrayList<BaseModel> gameLayout4plant0;
    // ����ֲ�� �ܵ�2
    private ArrayList<BaseModel> gameLayout4plant1;
    // ����ֲ�� �ܵ�3
    private ArrayList<BaseModel> gameLayout4plant2;
    // ����ֲ�� �ܵ�4
    private ArrayList<BaseModel> gameLayout4plant3;
    // ����ֲ�� �ܵ�5
    private ArrayList<BaseModel> gameLayout4plant4;

    // ��ʬ�ܵ�
    private ArrayList<BaseModel> gameLayout4zombie0;
    private ArrayList<BaseModel> gameLayout4zombie1;
    private ArrayList<BaseModel> gameLayout4zombie2;
    private ArrayList<BaseModel> gameLayout4zombie3;
    private ArrayList<BaseModel> gameLayout4zombie4;

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
        // �����ռ���X����
        Config.sunDeadLocationX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;

        deadList = new ArrayList<BaseModel>();
        gameLayout2 = new ArrayList<BaseModel>();
        gameLayout1 = new ArrayList<BaseModel>();
        gameLayout3 = new ArrayList<BaseModel>();

        gameLayout4plant0 = new ArrayList<BaseModel>();
        gameLayout4plant1 = new ArrayList<BaseModel>();
        gameLayout4plant2 = new ArrayList<BaseModel>();
        gameLayout4plant3 = new ArrayList<BaseModel>();
        gameLayout4plant4 = new ArrayList<BaseModel>();

        gameLayout4zombie0 = new ArrayList<BaseModel>();
        gameLayout4zombie1 = new ArrayList<BaseModel>();
        gameLayout4zombie2 = new ArrayList<BaseModel>();
        gameLayout4zombie3 = new ArrayList<BaseModel>();
        gameLayout4zombie4 = new ArrayList<BaseModel>();

        // ״̬��λ�� + һ��ͼƬ���
        int statusX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;
        int locationX = statusX + Config.seedFlower.getWidth();
        SeedFlower seedFlower = new SeedFlower(locationX, 0);
        gameLayout2.add(seedFlower);

        // �㶹����
        locationX = locationX + Config.seedPea.getWidth();
        SeedPea seedPea = new SeedPea(locationX, 0);
        gameLayout2.add(seedPea);

        Config.sunSizeX = statusX + 20;
        Config.sunSizeY = Config.seekBank.getHeight() - 5;

        // ��ʼ���ɰ��ſ�Ƭ��������Ч����
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
                    // ��¼ÿһ���ɰ���������ܵ�Y����
                    Config.racWayYpoint[i] = (i + 1) * Config.screenHeight / TOTAL_LINE;
                }
            }

        }

        mZombieManager = new ZombieManager();
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
                    // ������ͼ��
                    mCanvas.drawBitmap(Config.gameBg, 0, 0, mPaint);
                    // ��״̬����Ƭ
                    // ���ÿ�Ƭ����ʼ X ������ (������-��Ƭ���) / 2
                    mCanvas.drawBitmap(Config.seekBank, Config.sunDeadLocationX, Config.sunDeadLocationY, mPaint);

                    updateData();
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
        deadList.clear();

        for (BaseModel model : gameLayout1) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout2) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        // �Ƴ�������������
        for (BaseModel model : gameLayout3) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4plant0) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4plant1) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4plant2) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4plant3) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4plant4) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4zombie0) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4zombie1) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4zombie2) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4zombie3) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        for (BaseModel model : gameLayout4zombie4) {
            if (!model.isLive) {
                // ���������Ķ���
                deadList.add(model);
            }
        }

        // �ɿ���꣬��Ч������ʧ
        for (BaseModel model : deadList) {
            if (!model.isLive) {
                // ���������Ķ���
                gameLayout1.remove(model);
                gameLayout2.remove(model);
                gameLayout3.remove(model);

                gameLayout4plant0.remove(model);
                gameLayout4plant1.remove(model);
                gameLayout4plant2.remove(model);
                gameLayout4plant3.remove(model);
                gameLayout4plant4.remove(model);

                gameLayout4zombie0.remove(model);
                gameLayout4zombie1.remove(model);
                gameLayout4zombie2.remove(model);
                gameLayout4zombie3.remove(model);
                gameLayout4zombie4.remove(model);
            }
        }

    }

    private void onDrawing(Canvas mCanvas) {

        // ��ʱ���ɽ�ʬ
        mZombieManager.drawSelf(mCanvas, mPaint);

        mCanvas.drawText(String.valueOf(Config.sunSize), Config.sunSizeX, Config.sunSizeY, mPaint);

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

        for (BaseModel model : gameLayout4zombie0) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4zombie1) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4zombie2) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4zombie3) {
            model.drawSelf(mCanvas, mPaint);
        }

        for (BaseModel model : gameLayout4zombie4) {
            model.drawSelf(mCanvas, mPaint);
        }

        // ����ͼ�㣬����
        for (BaseModel model : gameLayout3) {
            model.drawSelf(mCanvas, mPaint);
        }

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

        // ����ͼ�㣬���С̫��
        for (BaseModel model : gameLayout3) {
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

    public void applyEmplacePlant(int locationX, int locationY, BaseModel mBaseModel) {
        synchronized (mSurfaceHolder) {
            // ������뱻����״̬��ֲ��
            // gameLayout1 ��ʾ���ڰ���ֲ��ļ���
            // ����״̬��һ��ֻ�ܷ�һ��ֲ��
            if (gameLayout1.size() < 1) {
                if (mBaseModel instanceof SeedPea) {
                    gameLayout1.add(new EmplacePea(locationX, locationY));
                } else if (mBaseModel instanceof SeedFlower) {
                    gameLayout1.add(new EmplaceFlower(locationX, locationY));
                }
            }
        }
    }

    public void applay4Plant(int locationX, int locationY, int type) {
        synchronized (mSurfaceHolder) {
            // ��ǰ��Ƭ����������ɰ��ż�������������
            for (Integer key : Config.plantPoint.keySet()) {
                // 1. ������Ƭ���ĵ������һ����Ԫ���ȣ���������ȡ������Ƭֱ�ӻ�������
                // 2. �ɷ��ÿ�Ƭƫ���ǣ��Զ���������
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
                        gameLayout4plant0.add(getPlant(point.x, point.y, key, type));
                        break;
                    case 1:
                        gameLayout4plant1.add(getPlant(point.x, point.y, key, type));
                        break;
                    case 2:
                        gameLayout4plant2.add(getPlant(point.x, point.y, key, type));
                        break;
                    case 3:
                        gameLayout4plant3.add(getPlant(point.x, point.y, key, type));
                        break;
                    case 4:
                        gameLayout4plant4.add(getPlant(point.x, point.y, key, type));
                        break;

                    default:
                        break;
                    }
                }
            }
        }

    }

    private BaseModel getPlant(int locationX, int locationY, int key, int type) {
        switch (type) {
        case Config.TYPE_PEA:
            return new Pea(locationX, locationY, key);
        case Config.TYPE_FLOWER:
            return new Flower(locationX, locationY, key);
        default:
            break;
        }

        return null;
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

    // ��������
    public void creatSun(int locationX, int locationY) {
        // �����ڵ���ͼ��
        synchronized (mSurfaceHolder) {
            gameLayout3.add(new Sun(locationX, locationY));
        }
    }

    // ������ʬ
    public void apply4CreatZombie() {
        synchronized (mSurfaceHolder) {
            int raceWay = 0;
            // ���� 0~4���ܵ�������ǿתȡ��
            raceWay = (int) (Math.random() * 5);

            switch (raceWay) {
            case 0:
                // Config.screenWidth + Config.zombieFlames[0].getWidth()
                // Ϊ�˽�ʬ��ǰ��
                gameLayout4zombie0.add(new Zombie(Config.screenWidth + Config.zombieFlames[0].getWidth(), Config.racWayYpoint[0], raceWay));
                break;
            case 1:
                gameLayout4zombie1.add(new Zombie(Config.screenWidth + Config.zombieFlames[0].getWidth(), Config.racWayYpoint[1], raceWay));

                break;
            case 2:
                gameLayout4zombie2.add(new Zombie(Config.screenWidth + Config.zombieFlames[0].getWidth(), Config.racWayYpoint[2], raceWay));

                break;
            case 3:
                gameLayout4zombie3.add(new Zombie(Config.screenWidth + Config.zombieFlames[0].getWidth(), Config.racWayYpoint[3], raceWay));

                break;
            case 4:
                gameLayout4zombie4.add(new Zombie(Config.screenWidth + Config.zombieFlames[0].getWidth(), Config.racWayYpoint[4], raceWay));

                break;

            default:
                break;
            }
        }
    }

}
