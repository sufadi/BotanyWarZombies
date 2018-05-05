### 1. Ч��

![��ʬ](https://img-blog.csdn.net/20180505232139464?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2. ����

ÿ��һ����ʱ����5���ܵ���������ɽ�ʬ�����Ҵ��������ƶ�

### 3. ����
1. ����һ����ʬ���ɹ����ߣ�����ʱ���ɽ�ʬ�����ﶨ�����ÿ��15�����ɽ�ʬ

```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.view.GameView;

/**
 * ��ʬ������
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
        // ��Ϸͼ����뽩ʬ
        GameView.getInstance().apply4CreatZombie();
    }
}

```

2. ���ý�ʬ������

��onDrawing�¼����ã�����Ҫ��Ҫ���ɣ������Ƿ�����15��Ķ�ʱ����

```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // ��ʬ������
    private ZombieManager mZombieManager;
    
        private void onDrawing(Canvas mCanvas) {

            // ��ʱ���ɽ�ʬ
            mZombieManager.drawSelf(mCanvas, mPaint);
```


3. �������һ����ʬ

�����ܵ�ʹ�����������ɣ���ʬ����������Ķ���

```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

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
```


4. �����ƶ��Ľ�ʬ����

���ﶨ���˽�ʬ�Ķ������ƶ��ٶ�

```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;

/**
 * ��ʬ��
 */
public class Zombie extends BaseModel {

    // �ܵ�������ײ���
    private int raceWay;

    // �ƶ��Ķ���֡
    private int farmeIndex;

    // ��ʬ�ƶ����ٶ�
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
 




### 4. Demo����

https://github.com/sufadi/BotanyWarZombies