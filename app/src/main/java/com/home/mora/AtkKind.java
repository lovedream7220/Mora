package com.home.mora;


import java.util.ArrayList;

public class AtkKind {

    private static int[][] atk0 = {{-1, 0}, {0, 0}, {1, 0}};
    private static int[][] atk1 = {{-1, -1}, {-1, 1}, {0, 0}, {1, 1}, {1, -1}};
    private static int[][] atk2 = {{-1, -1}, {-1, 1}, {0, 0}, {1, 1}, {1, -1}};

    private void point(){
        ArrayList pointHere = null;


        switch (atk0[0][0]){
            case -1 :
                switch(atk0[0][1]){
                    case -1 :
                        pointHere.add(7);
                        break;
                    case 0 :
                        pointHere.add(8);
                        break;
                    case 1 :
                        pointHere.add(9);
                        break;
                }
                break;
            case 0 :
                switch(atk0[0][1]){
                    case -1 :
                        pointHere.add(4);
                        break;
                    case 0 :
                        pointHere.add(5);
                        break;
                    case 1 :
                        pointHere.add(6);
                        break;
                }
                break;
            case 1 :
                switch(atk0[0][1]){
                    case -1 :
                        pointHere.add(1);
                        break;
                    case 0 :
                        pointHere.add(2);
                        break;
                    case 1 :
                        pointHere.add(3);
                        break;
                }
                break;
        }
    }

    public static int[][][] atk = new int[][][]{atk0, atk1, atk2};

    public static int[] atkHP = new int[]{5, 6};
    public static int[] atkMP = new int[]{5, 6};

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


