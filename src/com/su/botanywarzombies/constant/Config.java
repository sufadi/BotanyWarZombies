package com.su.botanywarzombies.constant;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Config {

    // 缩放比
    public static float scaleWidth;

    public static float scaleHeight;

    // 屏幕宽高
    public static int screenWidth;

    public static int screenHeight;

    // 图片背景
    public static Bitmap gameBg;

    // 阳光银行
    public static Bitmap seekBank;

    // 花
    public static Bitmap seedFlower;

    // 豌豆射手
    public static Bitmap seedPea;

    // 花的图片数组
    public static Bitmap[] flowerFlames = new Bitmap[8];
    // 豌豆射手的图片数组
    public static Bitmap[] peaFlames = new Bitmap[8];

    // 植物可安放的坐标集合
    public static HashMap<Integer, Point> plantPoint = new HashMap<Integer, Point>();

    // 记录每一个可安放区域的跑道Y坐标
    public static int[] racWayYpoint = new int[5];
}
