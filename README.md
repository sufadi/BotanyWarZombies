### 1. 效果

![僵尸](https://img-blog.csdn.net/20180505232139464?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2. 需求

每隔一定的时间在5个跑道中随机生成僵尸，并且从右往左移动

### 3. 开发
1. 定义一个僵尸生成管理者，负责定时生成僵尸，这里定义的是每隔15秒生成僵尸

```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.view.GameView;

/**
 * 僵尸生成器
 */
public class ZombieManager extends BaseModel {

    private int TIME = 15 * 1000;
    private long lastBirthTime = 0l;

    public ZombieManager() {
        this.isLive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (System.currentTimeMillis() - lastBirthTime > TIME) {
            lastBirthTime = System.currentTimeMillis();
            creatZombie();
        }
    }

    private void creatZombie() {
        // 游戏图层加入僵尸
        GameView.getInstance().apply4CreatZombie();
    }
}

```

2. 调用僵尸生成器

在onDrawing事件调用，但是要不要生成，根据是否满足15秒的定时周期

```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // 僵尸生成器
    private ZombieManager mZombieManager;
    
        private void onDrawing(Canvas mCanvas) {

            // 定时生成僵尸
            mZombieManager.drawSelf(mCanvas, mPaint);
```


3. 随机生成一个僵尸

其中跑道使用随机函数完成，僵尸的生成坐标的定义

```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // 生产僵尸
    public void apply4CreatZombie() {
        synchronized (mSurfaceHolder) {
            int raceWay = 0;
            // 生成 0~4的跑道，向下强转取整
            raceWay = (int) (Math.random() * 5);

            switch (raceWay) {
            case 0:
                // Config.screenWidth + Config.zombieFlames[0].getWidth()
                // 为了僵尸提前走
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
```


4. 可以移动的僵尸子类

这里定义了僵尸的动画和移动速度

```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;

/**
 * 僵尸类
 */
public class Zombie extends BaseModel {

    // 跑道，有碰撞监测
    private int raceWay;

    // 移动的动画帧
    private int farmeIndex;

    // 僵尸移动的速度
    private int seepX = 1;

    public Zombie(int locationX, int locationY, int raceWay) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.raceWay = raceWay;
        this.isLive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.zombieFlames[farmeIndex], locationX, locationY, paint);
            farmeIndex = (++farmeIndex) % 7;

            locationX = locationX - seepX;
        }
    }
}

```
 




### 4. Demo下载

https://github.com/sufadi/BotanyWarZombies