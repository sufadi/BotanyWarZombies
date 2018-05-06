### 1. ��ײ���

��ʬ�������տ����㶹���֣��Ե���Ӧ��ֲ��

![��ײ���](https://img-blog.csdn.net/20180506210110720?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2. ˼·
��ʬ��ֲ���൱��2�������ཻ


```
    // ���ε���ײ����㷨
    private boolean isCollision(BaseModel model1, BaseModel model2) {
        boolean result = false;

        // ���ε�������
        int modelMiddleLine1 = model1.locationX + model1.getModelWidth() / 2;
        // ���ε�������
        int modelMiddleLine2 = model2.locationX + model2.getModelWidth() / 2;
        // �����߲�
        int diff = Math.abs(modelMiddleLine1 - modelMiddleLine2);
        // ���ο�͵�һ����Ϊ��׼��
        int stander = (model1.getModelWidth() + model2.getModelWidth()) / 2;

        if (diff < stander) {
            result = true;
        }
        return result;
    }
```

### 3. ����

1. �ý�ʬ������ײ��⣬��Ϊ��ʬ���Գ����տ����㶹���֣�ʡ��Щ����������տ����㶹���ּ��Ļ���Ҫд2��


```
package com.su.botanywarzombies.entity;

/**
 * ��ʬ��
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

            // ��ʬ������ײ���
            GameView.getInstance().checkCollision(this, raceWay);
        }
    }

```


2. ��ײ���


```
    // ��ʬ������ײ���
    public void checkCollision(Zombie zombie, int raceWay) {
        synchronized (mSurfaceHolder) {
            switch (raceWay) {
            case 0:
                for (BaseModel model : gameLayout4plant0) {
                    // �������ε���ײ���
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // ��ʬ����ֲ���ֲ������
                            model.isLive = false;
                        } else {
                            // �ӵ������ʬ����ʬ����
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 1:
                for (BaseModel model : gameLayout4plant1) {
                    // �������ε���ײ���
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // ��ʬ����ֲ���ֲ������
                            model.isLive = false;
                        } else {
                            // �ӵ������ʬ����ʬ����
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 2:
                for (BaseModel model : gameLayout4plant2) {
                    // �������ε���ײ���
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // ��ʬ����ֲ���ֲ������
                            model.isLive = false;
                        } else {
                            // �ӵ������ʬ����ʬ����
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 3:
                for (BaseModel model : gameLayout4plant3) {
                    // �������ε���ײ���
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // ��ʬ����ֲ���ֲ������
                            model.isLive = false;
                        } else {
                            // �ӵ������ʬ����ʬ����
                            zombie.isLive = false;
                        }
                    }
                }
                break;
            case 4:
                for (BaseModel model : gameLayout4plant4) {
                    // �������ε���ײ���
                    if (isCollision(model, zombie)) {
                        if (model instanceof Plant) {
                            // ��ʬ����ֲ���ֲ������
                            model.isLive = false;
                        } else {
                            // �ӵ������ʬ����ʬ����
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

    // ���ε���ײ����㷨
    private boolean isCollision(BaseModel model1, BaseModel model2) {
        boolean result = false;

        // ���ε�������
        int modelMiddleLine1 = model1.locationX + model1.getModelWidth() / 2;
        // ���ε�������
        int modelMiddleLine2 = model2.locationX + model2.getModelWidth() / 2;
        // �����߲�
        int diff = Math.abs(modelMiddleLine1 - modelMiddleLine2);
        // ���ο�͵�һ����Ϊ��׼��
        int stander = (model1.getModelWidth() + model2.getModelWidth()) / 2;

        if (diff < stander) {
            result = true;
        }
        return result;
    }
```


### 4. Demo����

https://github.com/sufadi/BotanyWarZombies