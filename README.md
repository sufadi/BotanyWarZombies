### 1. Ч��ͼ
![������տ�](https://img-blog.csdn.net/20180503223540772?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2.˼·
�������㶹����

#### 2.1 ���״̬�������տ�

```
package com.su.botanywarzombies.entity;

public class SeedFlower extends BaseModel implements TouchAble {

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // x,y�����Ƿ��ڴ�������
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

#### 2.2 ���϶������տ�ͼƬ


```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


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
```

#### 2.3 �ɿ����϶������տ���Ƭ

MotionEvent.ACTION_UP �¼�

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
                // ��Ƭλ�ø���,�����ǿ�Ƭԭ��ı仯���������ĵ�(locationX, locationY)�ı仯
                locationX = x - Config.peaFlames[0].getWidth() / 2;
                locationY = y - Config.peaFlames[0].getHeight() / 2;

                // ��������棬�����Ƶ��µ�λ��
                touchArea.offsetTo(locationX, locationY);
                break;
            case MotionEvent.ACTION_UP:
                // �����־λʧЧ������������
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

#### 2.4 �ɰ��������Զ���λ�ڽ�λ��


```
package com.su.botanywarzombies.view;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

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

```

#### 2.5 һ�����ϵ�ͷ�����տ�


```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;

public class Flower extends BaseModel {

    private int farmeIndex = 0;
    // ��ֹһ��λ�÷�2��������Ƭ
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




### 3. Demo ����

https://github.com/sufadi/BotanyWarZombies