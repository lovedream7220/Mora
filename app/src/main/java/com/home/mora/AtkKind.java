package com.home.mora;


public class AtkKind {

    private static int[][] atk0 = {{-1, 0}, {0, 0}, {1, 0}};
    private static int[][] atk1 = {{-1, -1}, {-1, 1}, {0, 0}, {1, 1}, {1, -1}};


    public static int[][][] atk = new int[][][]{atk0, atk1};

    public static int[] atkHP = new int[]{5, 5};
    public static int[] atkMP = new int[]{3, 4};

    public static int[][] getAtk(int x) {
        return atk[x];

    }

    public static int getAtkHP(int x) {
        return atkHP[x];
    }

    public static int getAtkMP(int x) {
        return atkMP[x];
    }
}


