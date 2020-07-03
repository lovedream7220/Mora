package com.home.mora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.WebSocket;

import static com.home.mora.playerMoraList.*;

public class MainActivity extends AppCompatActivity {
    /**0626讓冠宇看懂的連線版本 */
    /**
     * 0627 :
     * 1.增加按鈕可以重新連線 initConnect
     * 2.
     */
    private TextView txtCom, txtWinLose, txt_self, txtVs, txt_self_name;
    private Button btnInitStart, btnInitConnect;
    private ImageView imagePlayer, imageCom;
    private EditText roomEditText;
    public int step;
    //    public String userName;
    WebSocket webSocket;
    ConnectManager connectManager = new ConnectManager(this);
//    AtkKind atkKind = new AtkKind();
    /**
     * 命名用途 只有不同名字的人才可以連線成功　TODO
     */
    String[] text1 = {"預言家", "女巫", "獵人", "騎士", "守衛", "禁言長老",
            "魔術師", "通靈師", "熊", "白癡", "炸彈人", "守墓人", "九尾妖狐"};
    public String userName = text1[(int) (Math.random() * text1.length)] + (int) (Math.random() * 9999 + 1);

//    public enum playerMoraList {
//        石頭, 剪刀, 布, 還沒出
//    }


    public int locationXSelf = 0;
    public int locationYSelf = 1;

    public int locationXCom = 4;
    public int locationYCom = 1;
    private View lineX0, lineX1, lineX2, lineX3, lineX4, lineY0, lineY1, lineY2, lineY3, lineY4;
    private View atkKJ00, atkKJ10, atkKJ20, atkKJ30, atkKJ40;
    private View atkKJ01, atkKJ11, atkKJ21, atkKJ31, atkKJ41;
    private View atkKJ02, atkKJ12, atkKJ22, atkKJ32, atkKJ42;
    //    public View atkKJ;
    private TextView txt_self_hp, txt_self_mp, txt_com_hp, txt_com_mp;
    public View button, button2, button3, button4, button5, button6;
    public View[] locationX;
    public View[] locationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("重新啟動---------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectManager.initiateSocketConnection();
        findView();
        locationX = new View[]{lineX0, lineX1, lineX2, lineX3, lineX4};
        locationY = new View[]{lineY0, lineY1, lineY2};
        roomEditText.setText(userName);
        step = 0;
    }


    private void findView() {
        roomEditText = findViewById(R.id.roomEditText);
        btnInitStart = findViewById(R.id.initStart);
        btnInitConnect = findViewById(R.id.initConnect);
        imageCom = findViewById(R.id.image_com);
        imageCom.setImageResource(R.drawable.com);
        imagePlayer = findViewById(R.id.image_player);
        imagePlayer.setImageResource(R.drawable.player);
        lineX0 = findViewById(R.id.lineX0);
        lineX1 = findViewById(R.id.lineX1);
        lineX2 = findViewById(R.id.lineX2);
        lineX3 = findViewById(R.id.lineX3);
        lineX4 = findViewById(R.id.lineX4);
        lineY0 = findViewById(R.id.lineY0);
        lineY1 = findViewById(R.id.lineY1);
        lineY2 = findViewById(R.id.lineY2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        txt_self_name = findViewById(R.id.txt_self_name);
        txt_self_name.setText(userName);
        txt_self_hp = findViewById(R.id.txt_self_hp);
        txt_self_mp = findViewById(R.id.txt_self_mp);
        txt_com_hp = findViewById(R.id.txt_com_hp);
        txt_com_mp = findViewById(R.id.txt_com_mp);
        atkKJ00 = findViewById(R.id.atkKJ00);
        atkKJ10 = findViewById(R.id.atkKJ10);
        atkKJ20 = findViewById(R.id.atkKJ20);
        atkKJ30 = findViewById(R.id.atkKJ30);
        atkKJ40 = findViewById(R.id.atkKJ40);
        atkKJ01 = findViewById(R.id.atkKJ01);
        atkKJ11 = findViewById(R.id.atkKJ11);
        atkKJ21 = findViewById(R.id.atkKJ21);
        atkKJ31 = findViewById(R.id.atkKJ31);
        atkKJ41 = findViewById(R.id.atkKJ41);
        atkKJ02 = findViewById(R.id.atkKJ02);
        atkKJ12 = findViewById(R.id.atkKJ12);
        atkKJ22 = findViewById(R.id.atkKJ22);
        atkKJ32 = findViewById(R.id.atkKJ32);
        atkKJ42 = findViewById(R.id.atkKJ42);
//        atkKJ =
//        atkKJ = findViewById(R.id.atkKJ);
//        atkKJ.setBackgroundColor(Color.RED);
        visibility();
    }


    public void visibility() {
        atkKJ00.setVisibility(View.INVISIBLE);
        atkKJ10.setVisibility(View.INVISIBLE);
        atkKJ20.setVisibility(View.INVISIBLE);
        atkKJ30.setVisibility(View.INVISIBLE);
        atkKJ40.setVisibility(View.INVISIBLE);
        atkKJ01.setVisibility(View.INVISIBLE);
        atkKJ11.setVisibility(View.INVISIBLE);
        atkKJ21.setVisibility(View.INVISIBLE);
        atkKJ31.setVisibility(View.INVISIBLE);
        atkKJ41.setVisibility(View.INVISIBLE);
        atkKJ02.setVisibility(View.INVISIBLE);
        atkKJ12.setVisibility(View.INVISIBLE);
        atkKJ22.setVisibility(View.INVISIBLE);
        atkKJ32.setVisibility(View.INVISIBLE);
        atkKJ42.setVisibility(View.INVISIBLE);
    }

    public void lockBtn() {
        button.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
    }

    public void openBtn() {
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
    }


    public int step() {
        step = step + 1;
        return step;
    }

    public void edit(View view) {
        /** 使用者名稱 : 有人名稱就記錄下來*/
        EditText name = findViewById(R.id.roomEditText);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                userName = s.toString();
                showLog("userName : " + userName);
                /** 將使用者名稱紀錄進手機內存*/
            }
        });
    }

    public void moveCommon(int x, int y, String txt) {
        /**邊界限制*/
        if (locationXSelf + x < 0) {
            locationXSelf = 0;
        } else if (locationXSelf + x > 4) {
            locationXSelf = 4;
        } else {
            locationXSelf = locationXSelf + x;
        }
        if (locationYSelf + y < 0) {
            locationYSelf = 0;
        } else if (locationYSelf + y > 2) {
            locationYSelf = 2;
        } else {
            locationYSelf = locationYSelf + y;
        }
        lockBtn();
        connectManager.sendMessage(step(), locationXSelf, locationYSelf, "move");
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    public void moveRight(View view) {
        moveCommon(1, 0, "向右");
    }

    public void moveLeft(View view) {
        moveCommon(-1, 0, "向左");
    }

    public void moveTop(View view) {
        moveCommon(0, 1, "向上");
    }

    public void moveDown(View view) {
        moveCommon(0, -1, "向下");
    }

    public void atkCommon(int atk) {
        connectManager.sendMessage(atk, "atk");
    }

    public void tool1(View view) {
        atkCommon(1);
    }

    public void tool2(View view) {
        atkCommon(2);
    }

    public int[][] atkRangeO;
    public int[][] atkRangeSelf;
    public int[][] atkRangeCom;
    public int[][] atkRangeDD;

    public void initAtkRange(){
        atkRangeO = null;
        atkRangeSelf = null ;
        atkRangeCom = null;
        atkRangeDD = null;
    }

    public int[][] getAtkRange(String who, int atkKindNum) {
        /**會記錄玩家攻擊的位置方便後續計算*/
        atkKindNum = atkKindNum - 1;
        atkRangeO = AtkKind.getAtk(atkKindNum);
        int[][] atkRangeX = new int[atkRangeO.length][2];
        if (who.equals("自己")) {
            for (int i = 0; i < atkRangeO.length; i++) {
                atkRangeX[i][0] = locationXSelf + atkRangeO[i][0];
                atkRangeX[i][1] = locationYSelf + atkRangeO[i][1];
            }
            atkRangeSelf = new int[atkRangeX.length][];
            for (int k = 0; k < atkRangeX.length; k++) {
                atkRangeSelf[k] = atkRangeX[k].clone();
            }
        } else if (who.equals("敵人")) {
            for (int i = 0; i < atkRangeO.length; i++) {
                atkRangeX[i][0] = locationXCom + atkRangeO[i][0];
                atkRangeX[i][1] = locationYCom + atkRangeO[i][1];
            }
            atkRangeCom = new int[atkRangeX.length][];
            for (int k = 0; k < atkRangeX.length; k++) {
                atkRangeCom[k] = atkRangeX[k].clone();
            }
        }
        atkJudgmentCommonRange(who, atkRangeX);
        return atkRangeX;
    }

    public int getAtkHP(int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        return AtkKind.getAtkHP(atkKindNum);
    }

    public int getAtkMP(int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        return AtkKind.getAtkMP(atkKindNum);
    }


    public void atkJudgmentCommon(int atkKindNum) {
        /**先扣魔力*/
        int mp = Integer.parseInt(txt_self_mp.getText().toString()) - getAtkMP(atkKindNum);
        txt_self_mp.setText(String.valueOf(mp));
    }


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

    public void atkJudgmentCommonRange(String x, int[][] range) {
        Resources res = getResources();
        Drawable drawable;
        drawable = res.getDrawable(R.drawable.atk);
        if (x.equals("敵人")) {
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
                        atkKJ00.setBackgroundDrawable(drawable);
                        atkKJ00.setVisibility(View.VISIBLE);
                        break;
                    case 10:
                        atkKJ10.setBackgroundDrawable(drawable);
                        atkKJ10.setVisibility(View.VISIBLE);
                        break;
                    case 20:
                        atkKJ20.setBackgroundDrawable(drawable);
                        atkKJ20.setVisibility(View.VISIBLE);
                        break;
                    case 30:
                        atkKJ30.setBackgroundDrawable(drawable);
                        atkKJ30.setVisibility(View.VISIBLE);
                        break;
                    case 40:
                        atkKJ40.setBackgroundDrawable(drawable);
                        atkKJ40.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        atkKJ01.setBackgroundDrawable(drawable);
                        atkKJ01.setVisibility(View.VISIBLE);
                        break;
                    case 11:
                        atkKJ11.setBackgroundDrawable(drawable);
                        atkKJ11.setVisibility(View.VISIBLE);
                        break;
                    case 21:
                        atkKJ21.setBackgroundDrawable(drawable);
                        atkKJ21.setVisibility(View.VISIBLE);
                        break;
                    case 31:
                        atkKJ31.setBackgroundDrawable(drawable);
                        atkKJ31.setVisibility(View.VISIBLE);
                        break;
                    case 41:
                        atkKJ41.setBackgroundDrawable(drawable);
                        atkKJ41.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        atkKJ02.setBackgroundDrawable(drawable);
                        atkKJ02.setVisibility(View.VISIBLE);
                        break;
                    case 12:
                        atkKJ12.setBackgroundDrawable(drawable);
                        atkKJ12.setVisibility(View.VISIBLE);
                        break;
                    case 22:
                        atkKJ22.setBackgroundDrawable(drawable);
                        atkKJ22.setVisibility(View.VISIBLE);
                        break;
                    case 32:
                        atkKJ32.setBackgroundDrawable(drawable);
                        atkKJ32.setVisibility(View.VISIBLE);
                        break;
                    case 42:
                        atkKJ42.setBackgroundDrawable(drawable);
                        atkKJ42.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }


    public void atkJudgmentSelf(int atkKindNum) {
        /**
         * 1.扣魔力
         * 2.自己是否攻擊成功
         * 3.
         * */
        atkJudgmentCommon(atkKindNum);

        int[] XY = {locationXCom, locationYCom};
        boolean success = false;
        showLog("敵人所在位置" + XY[0] + " " + XY[1]);
        for (int[] el : getAtkRange("自己", atkKindNum)) { // 獲取攻擊範圍時順便畫畫
            showLog("攻擊成功的位置 : " + el[0] + " " + el[1]);
            if (XY[0] == el[0] && XY[1] == el[1]) {
                success = true;
            }
        }
        showLog("自己是否攻擊成功 : " + success + "");
        if (success) {
            int hp = Integer.parseInt(txt_com_hp.getText().toString()) - getAtkHP(atkKindNum);
            txt_com_hp.setText(String.valueOf(hp));
        }
        rangeSame();//如果攻擊位置重疊 變成其他圖片
    }

    public void atkJudgmentCom(int atkKindNum) {
        atkJudgmentCommon(atkKindNum);
        int[] XY = {locationXSelf, locationYSelf};
        boolean success = false;
//        showLog("敵人所在位置" + XY[0] + " " + XY[1]);
        for (int[] el : getAtkRange("敵人", atkKindNum)) {
//            showLog("攻擊成功的位置 : " + el[0] + " " + el[1]);
            if (XY[0] == el[0] && XY[1] == el[1]) {
                success = true;
            }
        }
        showLog("敵人是否攻擊成功 : ", success + "");
        if (success) {
            int hp = Integer.parseInt(txt_self_hp.getText().toString()) - getAtkHP(atkKindNum);
            txt_self_hp.setText(String.valueOf(hp));
            Toast.makeText(this, "成功攻擊!! 扣對方" + getAtkHP(atkKindNum) + " HP ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "成功失敗.. 消耗" + getAtkMP(atkKindNum) + " MP ", Toast.LENGTH_LONG).show();
        }


        rangeSame();//如果攻擊位置重疊 變成其他圖片
    }

    public void initConnect(View view) {
        connectManager.initiateSocketConnection();
        Toast.makeText(this, "重新連線", Toast.LENGTH_SHORT).show();
    }

    public void initStart(View view) {
        Toast.makeText(this, "重新開始", Toast.LENGTH_SHORT).show();
    }


    public void moveJudgmentCom(int x, int y) {
        /**重新繪製位置*/
        switch (x) {
            case 4:
                x = 0;
                break;
            case 3:
                x = 1;
                break;
            case 2:
                x = 2;
                break;
            case 1:
                x = 3;
                break;
            case 0:
                x = 4;
                break;
        }
        locationXCom = x;
        locationYCom = y;
        imageCom.layout(locationX[x].getLeft() + 30, locationY[y].getTop() - 200, locationX[x].getLeft() + 100 + 30, locationY[y].getBottom());
    }

    public void moveJudgmentSelf(int x, int y) {
        /**重新繪製位置*/
        showLog("移動的座標x : ", x + "");
        showLog("移動的座標y : ", y + "");
        locationXSelf = x;
        locationYSelf = y;
        imagePlayer.layout(locationX[x].getLeft() + 30, locationY[y].getTop() - 200, locationX[x].getLeft() + 100 + 30, locationY[y].getBottom());
    }

    public void confirmPlace() {
        /**重新繪製位置*/
        imagePlayer.layout(locationX[locationXSelf].getLeft() + 30, locationY[locationYSelf].getTop() - 200, locationX[locationXSelf].getLeft() + 100 + 30, locationY[locationYSelf].getBottom());
        imageCom.layout(locationX[locationXCom].getLeft() + 30, locationY[locationYCom].getTop() - 200, locationX[locationXCom].getLeft() + 100 + 30, locationY[locationYCom].getBottom());
    }

    public void showLog(String... x) {
//        System.out.println(x);
    }

}