package com.home.mora;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AtkRules {
    private MainActivity activity;
    private Context context;

    public AtkRules(Context context) {
        this.context = context;
        this.activity = (MainActivity) context;
    }

    public int[][] atkRangeSelf; // 自己的攻擊範圍(根據座標重繪後的)
    public int[][] atkRangeCom; //對手的攻擊範圍(根據座標重繪後的)
    public int[][] atkRangeDD; //重疊的範圍(根據座標重繪後的)
    public AtkDecide atkDecide = new AtkDecide();

    /**
     * 攻擊範圍(根據座標重繪後)
     * atkRangeSelf
     * atkRangeCom
     */
    public int[][] getAtkRange(String who, int[] atkKindNum) {
        /**會記錄玩家攻擊的位置方便後續計算*/
//        atkKindNum = atkKindNum - 1;
//        int[][] atkRangeO = AtkKind.getAtk(atkKindNum); // 初始位置
        if (atkKindNum[0] != 0) {
            int[][] atkRangeO = pointJB(atkKindNum);
            int[][] atkRangeX = new int[atkRangeO.length][2]; //根據座標重繪的位置
            if (who.equals("自己")) {
                for (int i = 0; i < atkRangeO.length; i++) {
                    atkRangeX[i][0] = activity.locationXSelf + atkRangeO[i][0];
                    atkRangeX[i][1] = activity.locationYSelf + atkRangeO[i][1];
                }
                atkRangeSelf = new int[atkRangeX.length][];
                for (int k = 0; k < atkRangeX.length; k++) {
                    atkRangeSelf[k] = atkRangeX[k].clone();
                }
            } else if (who.equals("對手")) {
                for (int i = 0; i < atkRangeO.length; i++) {
                    atkRangeX[i][0] = activity.locationXCom + -1 * atkRangeO[i][0];
                    atkRangeX[i][1] = activity.locationYCom + atkRangeO[i][1];
                }
                atkRangeCom = new int[atkRangeX.length][];
                for (int k = 0; k < atkRangeX.length; k++) {
                    atkRangeCom[k] = atkRangeX[k].clone();
                }
            }
            atkJudgmentCommonRange(who, atkRangeX);
            return atkRangeX;
        } else {
            int[][] atkRangeX = new int[0][0];
            return atkRangeX;
        }
    }

    /**
     * 繪製地圖
     * 1.畫自己的攻擊範圍
     * 2.畫對手的攻擊範圍
     * 3.畫共同的的攻擊範圍
     */
    public void atkJudgmentCommonRange(String x, int[][] range) {
        Resources res = activity.getResources();
        Drawable drawable;
        drawable = res.getDrawable(R.drawable.atk);
        if (x.equals("對手")) {
            drawable = res.getDrawable(R.drawable.atkc);
        }
        if (x.equals("同個位置")) {
            drawable = res.getDrawable(R.drawable.atkt);
        }
        for (int i = 0; i < range.length; i++) {
            if (range[i][0] >= 0 && range[i][1] >= 0 && range[i][0] < 5 && range[i][1] < 3) {
                System.out.println(x + "燃燒的地方 : " + range[i][0] + "," + range[i][1]);
                switch (range[i][0] * 10 + range[i][1]) {
                    case 0:
                        activity.atkKJ00.setBackgroundDrawable(drawable);
                        activity.atkKJ00.setVisibility(View.VISIBLE);
                        break;
                    case 10:
                        activity.atkKJ10.setBackgroundDrawable(drawable);
                        activity.atkKJ10.setVisibility(View.VISIBLE);
                        break;
                    case 20:
                        activity.atkKJ20.setBackgroundDrawable(drawable);
                        activity.atkKJ20.setVisibility(View.VISIBLE);
                        break;
                    case 30:
                        activity.atkKJ30.setBackgroundDrawable(drawable);
                        activity.atkKJ30.setVisibility(View.VISIBLE);
                        break;
                    case 40:
                        activity.atkKJ40.setBackgroundDrawable(drawable);
                        activity.atkKJ40.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        activity.atkKJ01.setBackgroundDrawable(drawable);
                        activity.atkKJ01.setVisibility(View.VISIBLE);
                        break;
                    case 11:
                        activity.atkKJ11.setBackgroundDrawable(drawable);
                        activity.atkKJ11.setVisibility(View.VISIBLE);
                        break;
                    case 21:
                        activity.atkKJ21.setBackgroundDrawable(drawable);
                        activity.atkKJ21.setVisibility(View.VISIBLE);
                        break;
                    case 31:
                        activity.atkKJ31.setBackgroundDrawable(drawable);
                        activity.atkKJ31.setVisibility(View.VISIBLE);
                        break;
                    case 41:
                        activity.atkKJ41.setBackgroundDrawable(drawable);
                        activity.atkKJ41.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        activity.atkKJ02.setBackgroundDrawable(drawable);
                        activity.atkKJ02.setVisibility(View.VISIBLE);
                        break;
                    case 12:
                        activity.atkKJ12.setBackgroundDrawable(drawable);
                        activity.atkKJ12.setVisibility(View.VISIBLE);
                        break;
                    case 22:
                        activity.atkKJ22.setBackgroundDrawable(drawable);
                        activity.atkKJ22.setVisibility(View.VISIBLE);
                        break;
                    case 32:
                        activity.atkKJ32.setBackgroundDrawable(drawable);
                        activity.atkKJ32.setVisibility(View.VISIBLE);
                        break;
                    case 42:
                        activity.atkKJ42.setBackgroundDrawable(drawable);
                        activity.atkKJ42.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    /**
     * 計算共同的攻擊範圍
     */
    public void rangeSame() {
        List<Integer> listX = new ArrayList<>();
        List<Integer> listY = new ArrayList<>();
        if ((atkRangeCom != null) && (atkRangeSelf != null)) {
            for (int i = 0; i < atkRangeCom.length; i++) {
                for (int j = 0; j < atkRangeSelf.length; j++) {
                    if (atkRangeCom[i][0] * 10 + atkRangeCom[i][1] == atkRangeSelf[j][0] * 10 + atkRangeSelf[j][1]) {
                        listX.add(atkRangeCom[i][0]);
                        listY.add(atkRangeCom[i][1]);
                    }
                }
            }
            atkRangeDD = new int[listX.size()][2];
            for (int i = 0; i < listX.size(); i++) {
                atkRangeDD[i][0] = listX.get(i);
                atkRangeDD[i][1] = listY.get(i);
            }
            System.out.println(atkRangeDD);
            if (atkRangeDD != null) {
                atkJudgmentCommonRange("同個位置", atkRangeDD);
            }
        }
    }

    /**
     *
     */
    public void atkJudgmentSelf(int[] atkKindNum, int hp, int mp) {
        /**
         * 1.扣魔力
         * 2.自己是否攻擊成功
         * 3.
         * */
        if (atkKindNum[0] != 0) { // 如果不攻擊
            activity.controlMPHP(activity.txt_self_mp, -mp);//1.扣自己魔力
            int[] XY = {activity.locationXCom, activity.locationYCom};
            boolean success = false;
            for (int[] el : getAtkRange("自己", atkKindNum)) { // 獲取攻擊範圍時順便畫畫
                if (XY[0] == el[0] && XY[1] == el[1]) {
                    success = true;
                }
            }
            if (success) {
                activity.controlMPHP(activity.txt_com_hp, -hp);//1.扣對手血量
                Toast.makeText(context, "攻擊成功!! 扣對方 " + hp + " HP ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "攻擊失敗.. 消耗 " + mp + " MP ", Toast.LENGTH_LONG).show();
            }
            rangeSame();//如果攻擊位置重疊 變成其他圖片
        } else {
            Toast.makeText(context, "自己站著不動", Toast.LENGTH_LONG).show();
        }
    }

    public void atkJudgmentCom(int[] atkKindNum, int hp, int mp) {
        if (atkKindNum[0] != 0) {// 如果不攻擊
            activity.controlMPHP(activity.txt_com_mp, -mp);//1.扣對手魔力
            int[] XY = {activity.locationXSelf, activity.locationYSelf};
            boolean success = false;
            for (int[] el : getAtkRange("對手", atkKindNum)) {
                if (XY[0] == el[0] && XY[1] == el[1]) {
                    success = true;
                }
            }
            if (success) {
                activity.controlMPHP(activity.txt_self_hp, -hp);//1.扣自己血量
            }
            rangeSame();//如果攻擊位置重疊 變成其他圖片
        } else {
            Toast.makeText(context, "對手站著不動", Toast.LENGTH_LONG).show();
        }
    }


//    public int getAtkMP(int atkKindNum) {
//        atkKindNum = atkKindNum - 1;
//        return AtkKind.getAtkMP(atkKindNum);
//    }

    public void MPLimit(int atkKindNum, View buttonX) {
        if (Integer.parseInt(activity.txt_self_mp.getText().toString()) <= atkKindNum) {
            buttonX.setBackgroundColor(Color.parseColor("#e0000000"));
            buttonX.setEnabled(false);
        }
    }

    public void initAtkRange() {
        atkRangeSelf = null;
        atkRangeCom = null;
        atkRangeDD = null;
    }

    public void atkDrawHPMP() {
        activity.HP1.setText(atkDecide.HP[0] + "");
        activity.HP2.setText(atkDecide.HP[1] + "");
        activity.HP3.setText(atkDecide.HP[2] + "");
        activity.HP4.setText(atkDecide.HP[3] + "");
        activity.HP5.setText(atkDecide.HP[4] + "");
        activity.MP1.setText(atkDecide.MP[0] + "");
        activity.MP2.setText(atkDecide.MP[1] + "");
        activity.MP3.setText(atkDecide.MP[2] + "");
        activity.MP4.setText(atkDecide.MP[3] + "");
        activity.MP5.setText(atkDecide.MP[4] + "");

    }

    public void atkDraw() {
        for (int i = 0; i < atkDecide.atk0[0].length; i++) {
            switch (atkDecide.atk0[0][i]) {
                case 1:
                    activity.line11.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity.line12.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    activity.line13.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    activity.line14.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    activity.line15.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    activity.line16.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    activity.line17.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    activity.line18.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    activity.line19.setVisibility(View.VISIBLE);
                    break;
            }
        }

        for (int i = 0; i < atkDecide.atk0[1].length; i++) {
            switch (atkDecide.atk0[1][i]) {
                case 1:
                    activity.line21.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity.line22.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    activity.line23.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    activity.line24.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    activity.line25.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    activity.line26.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    activity.line27.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    activity.line28.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    activity.line29.setVisibility(View.VISIBLE);
                    break;
            }
        }
        for (int i = 0; i < atkDecide.atk0[2].length; i++) {
            switch (atkDecide.atk0[2][i]) {
                case 1:
                    activity.line31.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity.line32.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    activity.line33.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    activity.line34.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    activity.line35.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    activity.line36.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    activity.line37.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    activity.line38.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    activity.line39.setVisibility(View.VISIBLE);
                    break;
            }
        }
        for (int i = 0; i < atkDecide.atk0[3].length; i++) {
            switch (atkDecide.atk0[3][i]) {
                case 1:
                    activity.line41.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity.line42.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    activity.line43.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    activity.line44.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    activity.line45.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    activity.line46.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    activity.line47.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    activity.line48.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    activity.line49.setVisibility(View.VISIBLE);
                    break;
            }
        }
        for (int i = 0; i < atkDecide.atk0[4].length; i++) {
            switch (atkDecide.atk0[4][i]) {
                case 1:
                    activity.line51.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    activity.line52.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    activity.line53.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    activity.line54.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    activity.line55.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    activity.line56.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    activity.line57.setVisibility(View.VISIBLE);
                    break;
                case 8:
                    activity.line58.setVisibility(View.VISIBLE);
                    break;
                case 9:
                    activity.line59.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public static int[][] pointJB(int[] atk0) {
        int[][] pointHere = new int[atk0.length][2];
        for (int i = 0; i < atk0.length; i++) {
            int x = i;
            switch (atk0[i]) {
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

}