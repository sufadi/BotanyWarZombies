package com.su.botanywarzombies.constant;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Config {

    // ���ű�
    public static float scaleWidth;

    public static float scaleHeight;

    // ��Ļ���
    public static int screenWidth;

    public static int screenHeight;

    // ͼƬ����
    public static Bitmap gameBg;

    // ��������
    public static Bitmap seekBank;

    // ��
    public static Bitmap seedFlower;

    // �㶹����
    public static Bitmap seedPea;

    // ����ͼƬ����
    public static Bitmap[] flowerFlames = new Bitmap[8];
    // �㶹���ֵ�ͼƬ����
    public static Bitmap[] peaFlames = new Bitmap[8];

    // ֲ��ɰ��ŵ����꼯��
    public static HashMap<Integer, Point> plantPoint = new HashMap<Integer, Point>();

    // ��¼ÿһ���ɰ���������ܵ�Y����
    public static int[] racWayYpoint = new int[5];

    public static final int TYPE_PEA = 0;

    public static final int TYPE_FLOWER = 1;

}
