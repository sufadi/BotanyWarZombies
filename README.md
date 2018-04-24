### 1.����ͼ�㲼��Ч��

��ͼ����Ҫ��2��ͼƬ���̣��ݵغͷ��ÿ�Ƭ��״̬ͼ����̣����ھ�̬ͼƬ����

![����ͼƬ](https://img-blog.csdn.net/20180424223545697?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3N1NzQ5NTIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 2. ����ͼƬ����Ļ����

������Ҫ����ͼƬ�����űȶ�ԭʼͼƬ�������»��ƣ��ﵽ������Ļ��Ч��

- ���űȿ� = ��Ļ����� / ͼƬ�����
- ���űȸ� = ��Ļ����� / ͼƬ�����

```
    private void initValue() {
        int[] mDeviceInfo = DeviceTools.getDeviceInfo(this);
        Config.screenWidth = mDeviceInfo[0];
        Config.screenHeight = mDeviceInfo[0];

        Config.gameBg = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        Config.scaleWidth = Config.screenWidth / (float) Config.gameBg.getWidth();
        Config.scaleHeight = Config.screenHeight / (float) Config.gameBg.getHeight();

        // ��ȡ�µ������ֻ���Ļ�ı���ͼƬ
        Config.gameBg = DeviceTools.resizeBitmap(Config.gameBg);

        Config.seekBank = DeviceTools.resizeBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.seedbank));

        mGameView = new GameView(this);
    }
```

��Ļ��߻�ȡ��������


```
/**
     * ��ȡ��Ļ��С
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

�������ű����»���ͼƬ�ķ�������


```
public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = w;
            int newHeight = h;
            // ���ű�
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // ͼƬ����
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return resizedBitmap;
        } else {
            return null;
        }
    }
```

### 3. ����ͼƬ���Ƶ�sufaceView��

���� ���ÿ�Ƭ����ʼ X ������ (������-��Ƭ���) / 2

```
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    @Override
    public void run() {
        while (gameRunFlag) {
            // ������Ҫ�����߳�ͬ��
            synchronized (mSurfaceHolder) {
                try {
                    // ��ס�������ܻ�ͼ
                    mCanvas = mSurfaceHolder.lockCanvas();
                    mCanvas.drawBitmap(Config.gameBg, 0, 0, mPaint);
                    // ���ÿ�Ƭ����ʼ X ������ (������-��Ƭ���) / 2
                    mCanvas.drawBitmap(Config.seekBank, (Config.screenWidth - Config.seekBank.getWidth()) /2 , 0, mPaint);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // �������ύ
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

### 4. ���� Demo ���ص�ַ

https://github.com/sufadi/BotanyWarZombies

