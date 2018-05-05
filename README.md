### 1. 效果
![Sun](https://img-blog.csdn.net/20180505123541330?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


### 2. 需求
1. 安置向日葵完成，等待 10 秒 产生一个太阳
2. 太阳 5 秒钟如果没有被收集，则自动消失
3. 太阳如果被点击，即收集，则执行位移操作，回到卡片状态栏的太阳卡槽里

### 3. 思路

1. 向日葵的 10 秒产生太阳事件

```
package com.su.botanywarzombies.entity;

public class Flower extends BaseModel {

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

            // 每隔十秒产生一个小太阳
            if (System.currentTimeMillis() - lastBrithTime > TIME) {
                lastBrithTime = System.currentTimeMillis();
                creatSun(locationX, locationY);

            }
        }
    }
    
    // 产生阳光
    private void creatSun(int locationX, int locationY) {
        GameView.getInstance().creatSun(locationX, locationY);
    }

```
 
2. 游戏图层画出小太阳


```
package com.su.botanywarzombies.view;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // 第三层图层的集合，冉冉升起的小太阳
    private ArrayList<BaseModel> gameLayout3;


    // 生产阳光
    public void creatSun(int locationX, int locationY) {
        // 阳光在第三图层
        synchronized (mSurfaceHolder) {
            gameLayout3.add(new Sun(locationX, locationY));
        }
    }
    
    private void onDrawing(Canvas mCanvas) {
        ...
        // 第三图层，阳光
        for (BaseModel model : gameLayout3) {
            model.drawSelf(mCanvas, mPaint);
        }
        ...
    }
```


3. 超时自动回收小太阳


```
package com.su.botanywarzombies.entity;

public class Sun extends BaseModel implements TouchAble {

    /**
     * SHOW 生命周期TIME有效
     * 
     * MOVE 生命周期无效
     * 
     */
    public enum SunState {
        SHOW, MOVE
    }
    
    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
        
            } else if (mSunState == SunState.SHOW) {
                // 让阳光 TIME 后消失
                if (System.currentTimeMillis() - brithTime > TIME) {
                    isLive = false;
                }

            }

            canvas.drawBitmap(Config.sun, locationX, locationY, paint);
    }
```

对于游戏图层

```
package com.su.botanywarzombies.view;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private void updateData() {
        deadList.clear();
        
         // 移除到死亡的阳光
        for (BaseModel model : gameLayout3) {
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
                gameLayout2.remove(model);
                gameLayout3.remove(model);
            }
        }
```

4. 点击收取小太阳，并为位移运动做准备

```
package com.su.botanywarzombies.entity;

public class Sun extends BaseModel implements TouchAble {

    @Override
    public boolean onTouch(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (touchArea.contains(x, y)) {
                mSunState = SunState.MOVE;

                // 小太阳（locationX，locationY）到卡片状态栏的太阳（Config.sunDeadLocationX，Config.sunDeadLocationY
                // ）
                xDirection = locationX - Config.sunDeadLocationX;
                yDirection = locationY - Config.sunDeadLocationY;

                // 速度定义
                xSpeed = xDirection / 10f;
                ySpeed = yDirection / 10f;

                return true;
            }
            break;

        default:
            break;
        }

        return false;
    }
```

5. 小太阳移动到卡片槽动画


```
package com.su.botanywarzombies.entity;

public class Sun extends BaseModel implements TouchAble {

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {

            // move状态的阳光位移动画
            if (mSunState == SunState.MOVE) {
                locationX = (int) (locationX - xSpeed);
                locationY = (int) (locationY - ySpeed);

                // 当小太阳升到太阳卡片位置的时候，小太阳表示已经收集完毕，可以消失了
                if (locationX < 0 || locationY < 0) {
                    locationX = Config.sunDeadLocationX;
                    locationY = Config.sunDeadLocationY;
                    isLive = false;
                }
            ....            
            canvas.drawBitmap(Config.sun, locationX, locationY, paint);

```
 





### 4. Demo 下载

https://github.com/sufadi/BotanyWarZombies



