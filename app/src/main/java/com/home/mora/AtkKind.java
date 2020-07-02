package com.home.mora;


public class AtkKind {

    private int[][] atk0 = {{-1, 0}, {0, 0}, {1, 0}};
    private int[][] atk1 = {{-1, -1}, {-1, 1}, {0, 0}, {1, 1}, {1, -1}};


    public int[][][] atk = new int[][][]{atk0, atk1};

    public int[] atkHP = new int[]{5, 5};
    public int[] atkMP = new int[]{3, 4};

    public int[][] getAtk(int x) {
        return atk[x];

    }

    public int getAtkHP(int x) {
        return atkHP[x];
    }

    public int getAtkMP(int x) {
        return atkMP[x];
    }
}


