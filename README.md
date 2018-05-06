### 0. 学习来源
没想到教学视频就只讲到这里，视频来源是传智播客之植物大战僵尸Android开发教程+课件+源码
作者是侯哥，非常nice，视频是很早的，当时还是用Eclipse编写的，虽然现在是AndroidStudio

### 1. 碰撞监测

僵尸遇到向日葵和豌豆射手，吃掉对应的植物

![碰撞监测](https://img-blog.csdn.net/20180506210110720?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2. 思路
僵尸和植物相当于2个矩形相交


```
    // 矩形的碰撞监测算法
    private boolean isCollision(BaseModel model1, BaseModel model2) {
        boolean result = false;

        // 矩形的中心线
        int modelMiddleLine1 = model1.locationX + model1.getModelWidth() / 2;
        // 矩形的中心线
        int modelMiddleLine2 = model2.locationX + model2.getModelWidth() / 2;
        // 中心线差
        int diff = Math.abs(modelMiddleLine1 - modelMiddleLine2);
        // 矩形宽和的一般作为标准线
        int stander = (model1.getModelWidth() + model2.getModelWidth()) / 2;

        if (diff < stander) {
            result = true;
        }
        return result;
    }
```

### 3. 开发

1. 让僵尸进行碰撞监测，因为僵尸可以吃向日葵，豌豆射手，省事些，如果想向日葵和豌豆射手监测的话，要写2遍


```
package com.su.botanywarzombies.entity;

/**
 * 僵尸类
 */
public class Zombie extends BaseModel {

     @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.zombieFlames[farmeIndex], locationX, locationY, paint);
            farmeIndex = (++farmeIndex) % 7;

            locationX = locationX - seepX;

            if (locationX < 0) {
                isLive = false;
            }

            // 僵尸发起碰撞监测
            GameView.getInstance().checkCollision(this, raceWay);
        }
    }

```


2. 碰撞监测


```
    // 僵尸发起碰撞监测
    public void checkCollision(Zombie zombie, int raceWay) {
        synchronized (mSurfaceHolder) {
            switch (raceWay) {
            case 0:
                for (BaseModel model : gameLayout4plant0) {
                    // 两个矩形的碰撞监测
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // 僵尸碰到植物，则植物死了
                            model.isLive = false;
                        } else {
                            // 子弹射击僵尸，则僵尸死亡
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 1:
                for (BaseModel model : gameLayout4plant1) {
                    // 两个矩形的碰撞监测
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // 僵尸碰到植物，则植物死了
                            model.isLive = false;
                        } else {
                            // 子弹射击僵尸，则僵尸死亡
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 2:
                for (BaseModel model : gameLayout4plant2) {
                    // 两个矩形的碰撞监测
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // 僵尸碰到植物，则植物死了
                            model.isLive = false;
                        } else {
                            // 子弹射击僵尸，则僵尸死亡
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 3:
                for (BaseModel model : gameLayout4plant3) {
                    // 两个矩形的碰撞监测
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // 僵尸碰到植物，则植物死了
                            model.isLive = false;
                        } else {
                            // 子弹射击僵尸，则僵尸死亡
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 4:
                for (BaseModel model : gameLayout4plant4) {
                    // 两个矩形的碰撞监测
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // 僵尸碰到植物，则植物死了
                            model.isLive = false;
                        } else {
                            // 子弹射击僵尸，则僵尸死亡
                            zombie.isLive = false;
                        }
                    }
                }
                break;

            default:
                break;
            }
        }
    }

    // 矩形的碰撞监测算法
    private boolean isCollision(BaseModel model1, BaseModel model2) {
        boolean result = false;

        // 矩形的中心线
        int modelMiddleLine1 = model1.locationX + model1.getModelWidth() / 2;
        // 矩形的中心线
        int modelMiddleLine2 = model2.locationX + model2.getModelWidth() / 2;
        // 中心线差
        int diff = Math.abs(modelMiddleLine1 - modelMiddleLine2);
        // 矩形宽和的一般作为标准线
        int stander = (model1.getModelWidth() + model2.getModelWidth()) / 2;

        if (diff < stander) {
            result = true;
        }
        return result;
    }
```


### 4. Demo下载

https://github.com/sufadi/BotanyWarZombies