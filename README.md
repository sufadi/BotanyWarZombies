### 1. 效果图
![添加向日葵](https://img-blog.csdn.net/20180503223540772?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2.思路
仿照这豌豆射手

#### 2.1 点击状态栏的向日葵

```
package com.su.botanywarzombies.entity;

public class SeedFlower extends BaseModel implements TouchAble {

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // x,y坐标是否在触摸区域
        if (touchArea.contains(x, y)) {
            Log.d("sufadi", "touch seed flower");
            apply4EmplaceFlower();
            return true;
        }

        return false;
    }

    private void apply4EmplaceFlower() {
        GameView.getInstance().applyEmplacePlant(locationX, locationY, this);

    }
```

#### 2.2 可拖动的向日葵图片


```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    public void applyEmplacePlant(int locationX, int locationY, BaseModel mBaseModel) {
        synchronized (mSurfaceHolder) {
            // 顶层加入被安放状态的植物
            // gameLayout1 表示正在安放植物的集合
            // 安放状态下一次只能放一个植物
            if (gameLayout1.size() < 1) {
                if (mBaseModel instanceof SeedPea) {
                    gameLayout1.add(new EmplacePea(locationX, locationY));
                } else if (mBaseModel instanceof SeedFlower) {
                    gameLayout1.add(new EmplaceFlower(locationX, locationY));
                }
            }
        }
    }
```

#### 2.3 松开可拖动的向日葵卡片

MotionEvent.ACTION_UP 事件

```
package com.su.botanywarzombies.entity;

public class EmplaceFlower extends BaseModel implements TouchAble {

@Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (touchArea.contains(x, y)) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                // 卡片位置更新,这里是卡片原点的变化，故是中心点(locationX, locationY)的变化
                locationX = x - Config.peaFlames[0].getWidth() / 2;
                locationY = y - Config.peaFlames[0].getHeight() / 2;

                // 触摸点跟随，重新移到新的位置
                touchArea.offsetTo(locationX, locationY);
                break;
            case MotionEvent.ACTION_UP:
                // 对象标志位失效，即死亡对象
                isLive = false;

                GameView.getInstance().applay4Plant(locationX, locationY, Config.TYPE_FLOWER);
                break;
            default:
                break;
            }
        }

        return false;
    }
```

#### 2.4 可安放区域自定定位邻近位置


```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public void applay4Plant(int locationX, int locationY, int type) {
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

```

#### 2.5 一个不断点头的向日葵


```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;

public class Flower extends BaseModel {

    private int farmeIndex = 0;
    // 禁止一个位置放2个动画卡片
    private int mapIndex;

    public Flower(int locationX, int locationY, int mapIndex) {
        this.locationX = locationX;
        this.locationY = locationY;
        isLive = true;
        this.mapIndex = mapIndex;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        super.drawSelf(canvas, paint);
        if (isLive) {
            canvas.drawBitmap(Config.flowerFlames[farmeIndex], locationX, locationY, paint);

            /*
             * farmeIndex ++; if (farmeIndex == Config.peaFlames.length -1) {
             * farmeIndex = 0; }
             */
            farmeIndex = (++farmeIndex) % 8;
        }
    }

    @Override
    public int getMapIndex() {
        return mapIndex;
    }
}

```




### 3. Demo 下载

https://github.com/sufadi/BotanyWarZombies