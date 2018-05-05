### 1. Ч��
![Sun](https://img-blog.csdn.net/20180505123541330?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


### 2. ����
1. �������տ���ɣ��ȴ� 10 �� ����һ��̫��
2. ̫�� 5 �������û�б��ռ������Զ���ʧ
3. ̫���������������ռ�����ִ��λ�Ʋ������ص���Ƭ״̬����̫��������

### 3. ˼·

1. ���տ��� 10 �����̫���¼�

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

            // ÿ��ʮ�����һ��С̫��
            if (System.currentTimeMillis() - lastBrithTime > TIME) {
                lastBrithTime = System.currentTimeMillis();
                creatSun(locationX, locationY);

            }
        }
    }
    
    // ��������
    private void creatSun(int locationX, int locationY) {
        GameView.getInstance().creatSun(locationX, locationY);
    }

```
 
2. ��Ϸͼ�㻭��С̫��


```
package com.su.botanywarzombies.view;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // ������ͼ��ļ��ϣ�ȽȽ�����С̫��
    private ArrayList<BaseModel> gameLayout3;


    // ��������
    public void creatSun(int locationX, int locationY) {
        // �����ڵ���ͼ��
        synchronized (mSurfaceHolder) {
            gameLayout3.add(new Sun(locationX, locationY));
        }
    }
    
    private void onDrawing(Canvas mCanvas) {
        ...
        // ����ͼ�㣬����
        for (BaseModel model : gameLayout3) {
            model.drawSelf(mCanvas, mPaint);
        }
        ...
    }
```


3. ��ʱ�Զ�����С̫��


```
package com.su.botanywarzombies.entity;

public class Sun extends BaseModel implements TouchAble {

    /**
     * SHOW ��������TIME��Ч
     * 
     * MOVE ����������Ч
     * 
     */
    public enum SunState {
        SHOW, MOVE
    }
    
    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
        
            } else if (mSunState == SunState.SHOW) {
                // ������ TIME ����ʧ
                if (System.currentTimeMillis() - brithTime > TIME) {
                    isLive = false;
                }

            }

            canvas.drawBitmap(Config.sun, locationX, locationY, paint);
    }
```

������Ϸͼ��

```
package com.su.botanywarzombies.view;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private void updateData() {
        deadList.clear();
        
         // �Ƴ�������������
        for (BaseModel model : gameLayout3) {
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
            }
        }
```

4. �����ȡС̫������Ϊλ���˶���׼��

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

                // С̫����locationX��locationY������Ƭ״̬����̫����Config.sunDeadLocationX��Config.sunDeadLocationY
                // ��
                xDirection = locationX - Config.sunDeadLocationX;
                yDirection = locationY - Config.sunDeadLocationY;

                // �ٶȶ���
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

5. С̫���ƶ�����Ƭ�۶���


```
package com.su.botanywarzombies.entity;

public class Sun extends BaseModel implements TouchAble {

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {

            // move״̬������λ�ƶ���
            if (mSunState == SunState.MOVE) {
                locationX = (int) (locationX - xSpeed);
                locationY = (int) (locationY - ySpeed);

                // ��С̫������̫����Ƭλ�õ�ʱ��С̫����ʾ�Ѿ��ռ���ϣ�������ʧ��
                if (locationX < 0 || locationY < 0) {
                    locationX = Config.sunDeadLocationX;
                    locationY = Config.sunDeadLocationY;
                    isLive = false;
                }
            ....            
            canvas.drawBitmap(Config.sun, locationX, locationY, paint);

```
 





### 4. Demo ����

https://github.com/sufadi/BotanyWarZombies



