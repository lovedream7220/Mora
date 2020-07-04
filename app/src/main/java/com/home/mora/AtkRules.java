package com.home.mora;

import android.content.Context;
import android.content.res.Resources;
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

    /**
     * 攻擊範圍(根據座標重繪後)
     * atkRangeSelf
     * atkRangeCom
     */
    public int[][] getAtkRange(String who, int atkKindNum) {
        /**會記錄玩家攻擊的位置方便後續計算*/
        atkKindNum = atkKindNum - 1;
        int[][] atkRangeO = AtkKind.getAtk(atkKindNum); // 初始位置
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
                atkRangeX[i][0] = activity.locationXCom + atkRangeO[i][0];
                atkRangeX[i][1] = activity.locationYCom + atkRangeO[i][1];
            }
            atkRangeCom = new int[atkRangeX.length][];
            for (int k = 0; k < atkRangeX.length; k++) {
                atkRangeCom[k] = atkRangeX[k].clone();
            }
        }
        atkJudgmentCommonRange(who, atkRangeX);
        return atkRangeX;
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
                System.out.println("燃燒的地方 : " + range[i][0] + "," + range[i][1]);
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
            if (atkRangeDD != null) {
                atkJudgmentCommonRange("同個位置", atkRangeDD);
            }
        }
    }

    /**
     *
     */
    public void atkJudgmentSelf(int atkKindNum) {
        /**
         * 1.扣魔力
         * 2.自己是否攻擊成功
         * 3.
         * */
        activity.controlMPHP(activity.txt_self_mp, -getAtkMP(atkKindNum));//1.扣自己魔力
        int[] XY = {activity.locationXCom, activity.locationYCom};
        boolean success = false;
        for (int[] el : getAtkRange("自己", atkKindNum)) { // 獲取攻擊範圍時順便畫畫
            if (XY[0] == el[0] && XY[1] == el[1]) {
                success = true;
            }
        }
        if (success) {
            activity.controlMPHP(activity.txt_com_hp, -getAtkHP(atkKindNum));//1.扣對手血量
            Toast.makeText(context, "攻擊成功!! 扣對方 " + getAtkHP(atkKindNum) + " HP ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "攻擊失敗.. 消耗 " + getAtkMP(atkKindNum) + " MP ", Toast.LENGTH_LONG).show();
        }
        rangeSame();//如果攻擊位置重疊 變成其他圖片
    }

    public void atkJudgmentCom(int atkKindNum) {
        activity.controlMPHP(activity.txt_com_mp, -getAtkMP(atkKindNum));//1.扣對手魔力
        int[] XY = {activity.locationXSelf, activity.locationYSelf};
        boolean success = false;
        for (int[] el : getAtkRange("對手", atkKindNum)) {
            if (XY[0] == el[0] && XY[1] == el[1]) {
                success = true;
            }
        }
        if (success) {
            activity.controlMPHP(activity.txt_self_hp, -getAtkHP(atkKindNum));//1.扣自己血量
        }
        rangeSame();//如果攻擊位置重疊 變成其他圖片
    }

    public int getAtkHP(int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        return AtkKind.getAtkHP(atkKindNum);
    }

    public int getAtkMP(int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        return AtkKind.getAtkMP(atkKindNum);
    }

    public void MPLimit(int atkKindNum, View buttonX) {
        if (Integer.parseInt(activity.txt_self_mp.getText().toString()) < getAtkMP(atkKindNum)) {
            buttonX.setAlpha(0.2f);
            buttonX.setEnabled(false);
        }
    }

    public void initAtkRange() {
        atkRangeSelf = null;
        atkRangeCom = null;
        atkRangeDD = null;
    }
}