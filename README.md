### 1.背景图层布置效果

该图层主要是2张图片过程，草地和放置卡片的状态图层过程，属于静态图片范畴

![背景图片](https://img-blog.csdn.net/20180424223545697?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2. 背景图片的屏幕适配

这里主要根据图片的缩放比对原始图片进行重新绘制，达到适配屏幕的效果

- 缩放比宽 = 屏幕界面宽 / 图片本身宽
- 缩放比高 = 屏幕界面高 / 图片本身高

```
    private void initValue() {
        int[] mDeviceInfo = DeviceTools.getDeviceInfo(this);
        Config.screenWidth = mDeviceInfo[0];
        Config.screenHeight = mDeviceInfo[0];

        Config.gameBg = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        Config.scaleWidth = Config.screenWidth / (float) Config.gameBg.getWidth();
        Config.scaleHeight = Config.screenHeight / (float) Config.gameBg.getHeight();

        // 获取新的适配手机屏幕的背景图片
        Config.gameBg = DeviceTools.resizeBitmap(Config.gameBg);

        Config.seekBank = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seedbank));

        mGameView = new GameView(this);
    }
```

屏幕宽高获取方法如下


```
/**
     * 获取屏幕大小
     * 
     * @param context
     * @return
     */
    public static int[] getDeviceInfo(Context context) {
        if ((deviceWidthHeight[0] == 0) && (deviceWidthHeight[1] == 0)) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

            deviceWidthHeight[0] = metrics.widthPixels;
            deviceWidthHeight[1] = metrics.heightPixels;
        }
        return deviceWidthHeight;
    }
```

根据缩放比重新绘制图片的方法如下


```
public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = w;
            int newHeight = h;
            // 缩放比
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 图片矩阵
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return resizedBitmap;
        } else {
            return null;
        }
    }
```

### 3. 背景图片绘制到sufaceView中

其中 放置卡片的起始 X 坐标是 (界面宽度-卡片宽度) / 2

```
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    @Override
    public void run() {
        while (gameRunFlag) {
            // 这里需要考虑线程同步
            synchronized (mSurfaceHolder) {
                try {
                    // 锁住画布才能绘图
                    mCanvas = mSurfaceHolder.lockCanvas();
                    mCanvas.drawBitmap(Config.gameBg, 0, 0, mPaint);
                    // 放置卡片的起始 X 坐标是 (界面宽度-卡片宽度) / 2
                    mCanvas.drawBitmap(Config.seekBank, (Config.screenWidth - Config.seekBank.getWidth()) /2 , 0, mPaint);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 解锁并提交
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
```

### 4. 完整 Demo 下载地址

https://github.com/sufadi/BotanyWarZombies

