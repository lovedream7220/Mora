package com.home.mora;


import java.util.ArrayList;

public class AtkKind {

    private static int[] atk0 = {4, 5, 6};
    private static int[] atk1 = {1, 3, 5, 7, 9};
    private static int[] atk2 = {1, 2, 3, 5};
    
    public static int[][][] atk = new int[][][]{pointJB(atk0), pointJB(atk1), pointJB(atk2)};
    public static int[] atkHP = new int[]{5, 6};
    public static int[] atkMP = new int[]{5, 6};

    private static int[][] pointJB(int[] atk0) {
        int[][] pointHere = null;
        for (int i = 0; i < atk0.length; i++) {
            int x = pointHere.length;
            switch (atk0[0]) {
                case 1:
                    pointHere[x][0] = -1;
                    pointHere[x][1] = 1;
                    break;
                case 2:
                    pointHere[x][0] = 0;
                    pointHere[x][1] = 1;
                    break;
                case 3:
                    pointHere[x][0] = 1;
                    pointHere[x][1] = 1;
                    break;
                case 4:
                    pointHere[x][0] = -1;
                    pointHere[x][1] = 0;
                    break;
                case 5:
                    pointHere[x][0] = 0;
                    pointHere[x][1] = 0;
                    break;
                case 6:
                    pointHere[x][0] = 1;
                    pointHere[x][1] = 0;
                    break;
                case 7:
                    pointHere[x][0] = -1;
                    pointHere[x][1] = -1;
                    break;
                case 8:
                    pointHere[x][0] = 0;
                    pointHere[x][1] = -1;
                    break;
                case 9:
                    pointHere[x][0] = 1;
                    pointHere[x][1] = -1;
                    break;
            }
        }
        return pointHere;
    }


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


