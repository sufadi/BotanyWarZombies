### 1.放置太阳花和豌豆射手卡片

![豌豆射手和太阳花](https://img-blog.csdn.net/20180425231427646?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2.基本思路

- 太阳花卡片的起始X位置 = 根据状态栏的X坐标 + 1个图片宽度
- 太阳花卡片的起始X位置 = 根据状态栏的X坐标 + 2个图片宽度


```
        // 状态栏位置 + 一张图片宽度
        int statusX = (Config.screenWidth - Config.seekBank.getWidth()) / 2;
        int locationX = statusX + Config.seedFlower.getWidth();
        SeedFlower seedFlower = new SeedFlower(locationX, 0);
        gameLayout2.add(seedFlower);

        // 豌豆射手
        locationX = locationX + Config.seedPea.getWidth();
        SeedPea seedPea = new SeedPea(locationX, 0);
        gameLayout2.add(seedPea);
```

### 3.开发细节
##### 3.1 卡片对象-基类

```
package com.su.botanywarzombies.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BaseModel {

    // 对象的起始X坐标
    public int locationX;

    // 对象的起始 Y坐标
    public int locationY;

    // 是否还活着
    public boolean isLive;

    // 位置的自我绘制
    public void drawSelf(Canvas canvas, Paint paint) {

    }
}


```
##### 3.1 卡片对象-接口类

```
package com.su.botanywarzombies.model;

public interface TouchAble {
    void onTouch();
}

```



##### 3.2 太阳花卡片对象

继承基类并实现接口

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


##### 3.2 豌豆射手卡片对象


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

##### 3.3 卡片对象的图片加载，这里需要指定宽高

除以 6 是宽度的估算

```
        int seedPicWidth = Config.seekBank.getWidth() / 6;
        int seedPicHeight = Config.seekBank.getHeight();
        Config.seedFlower = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seed_flower), seedPicWidth, seedPicHeight);
        Config.seedPea = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seed_pea), seedPicWidth, seedPicHeight);
```

### 4.demo 下载

https://github.com/sufadi/BotanyWarZombies

