### 1.����̫�������㶹���ֿ�Ƭ

![�㶹���ֺ�̫����](https://img-blog.csdn.net/20180425231427646?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2.����˼·

- ̫������Ƭ����ʼXλ�� = ����״̬����X���� + 1��ͼƬ���
- ̫������Ƭ����ʼXλ�� = ����״̬����X���� + 2��ͼƬ���


```
        // ״̬��λ�� + һ��ͼƬ���
        int statusX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;
        int locationX = statusX + Config.seedFlower.getWidth();
        SeedFlower seedFlower = new SeedFlower(locationX, 0);
        gameLayout2.add(seedFlower);

        // �㶹����
        locationX = locationX + Config.seedPea.getWidth();
        SeedPea seedPea = new SeedPea(locationX, 0);
        gameLayout2.add(seedPea);
```

### 3.����ϸ��
##### 3.1 ��Ƭ����-����

```
package com.su.botanywarzombies.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BaseModel {

    // �������ʼX����
    public int locationX;

    // �������ʼ Y����
    public int locationY;

    // �Ƿ񻹻���
    public boolean isLive;

    // λ�õ����һ���
    public void drawSelf(Canvas canvas, Paint paint) {

    }
}


```
##### 3.1 ��Ƭ����-�ӿ���

```
package com.su.botanywarzombies.model;

public interface TouchAble {
    void onTouch();
}

```



##### 3.2 ̫������Ƭ����

�̳л��ಢʵ�ֽӿ�

```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.TouchAble;

public class SeedFlower extends BaseModel implements TouchAble {

    public SeedFlower(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.isLive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.seedFlower, locationX, locationY, paint);
        }
    }

    @Override
    public void onTouch() {

    }

}

```


##### 3.2 �㶹���ֿ�Ƭ����


```
package com.su.botanywarzombies.entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.su.botanywarzombies.constant.Config;
import com.su.botanywarzombies.model.BaseModel;
import com.su.botanywarzombies.model.TouchAble;

public class SeedPea extends BaseModel implements TouchAble {

    public SeedPea(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.isLive = true;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        if (isLive) {
            canvas.drawBitmap(Config.seedPea, locationX, locationY, paint);
        }
    }

    @Override
    public void onTouch() {

    }

}

```

##### 3.3 ��Ƭ�����ͼƬ���أ�������Ҫָ�����

���� 6 �ǿ�ȵĹ���

```
        int seedPicWidth = Config.seekBank.getWidth() / 6;
        int seedPicHeight = Config.seekBank.getHeight();
        Config.seedFlower = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seed_flower), seedPicWidth, seedPicHeight);
        Config.seedPea = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seed_pea), seedPicWidth, seedPicHeight);
```

### 4.demo ����

https://github.com/sufadi/BotanyWarZombies

