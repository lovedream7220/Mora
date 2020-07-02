package com.home.mora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    AtkKind atkKind = new AtkKind();
    /**
     * 命名用途 只有不同名字的人才可以連線成功　TODO
     */
    String[] text1 = {"預言家", "女巫", "獵人", "騎士", "守衛", "禁言長老",
            "魔術師", "通靈師", "熊", "白癡", "炸彈人", "守墓人", "九尾妖狐"};
    public String userName = text1[(int) (Math.random() * text1.length)] + (int) (Math.random() * 999 + 1);

//    public enum playerMoraList {
//        石頭, 剪刀, 布, 還沒出
//    }


    public int locationXSelf = 0;
    public int locationYSelf = 1;

    public int locationXCom = 4;
    public int locationYCom = 1;
    private View lineX0, lineX1, lineX2, lineX3, lineX4, lineY0, lineY1, lineY2, lineY3, lineY4;
    private TextView txt_self_hp, txt_self_mp, txt_com_hp, txt_com_mp;
    public View button, button2, button3, button4, button5, button6;
    public View[] locationX;
    public View[] locationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                System.out.println("userName : " + userName);
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
        int[][] atkRange = {{locationXSelf - 1, locationYSelf - 1}, {locationXSelf - 1, locationYSelf + 1}, {locationXSelf, locationYSelf}, {locationXSelf + 1, locationYSelf - 1}, {locationXSelf + 1, locationYSelf + 1}};
    }

    public int[][] getAtkRange(String who, int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        int[][] atkRange = atkKind.getAtk(atkKindNum);

        if (who.equals("自己")) {
            for (int i = 0; i < atkRange.length; i++) {
                atkRange[i][0] = locationXSelf + atkRange[i][0];
                atkRange[i][1] = locationYSelf + atkRange[i][1];
            }
        } else if (who.equals("對手")) {
            for (int i = 0; i < atkRange.length; i++) {
                atkRange[i][0] = locationXCom + atkRange[i][0];
                atkRange[i][1] = locationYCom + atkRange[i][1];
            }
        }
        return atkRange;
    }

    public int getAtkHP(int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        return atkKind.getAtkHP(atkKindNum);
    }

    public int getAtkMP(int atkKindNum) {
        atkKindNum = atkKindNum - 1;
        return atkKind.getAtkMP(atkKindNum);
    }


    public void atkJudgmentSelf(int atkKindNum) {
        int[] XY = {locationXCom, locationYCom};
        boolean success = false;
        System.out.println("敵人所在位置" + XY);


        for (int[] el : getAtkRange("自己", atkKindNum)) {
            System.out.println("攻擊成功的位置 : " + el);
            if (String.valueOf(XY).equals(String.valueOf(el))) {
                success = true;
            }
        }
        System.out.println("自己是否攻擊成功 : " + success);
        if (success) {
            txt_com_hp.setText(Integer.parseInt(txt_com_hp.toString()) - getAtkHP(atkKindNum));
        }
    }

    public void atkJudgmentCom(int atkKindNum) {
        int[] XY = {locationXSelf, locationYSelf};
        boolean success = false;
        System.out.println("自己所在位置" + XY);
        for (int[] el : getAtkRange("敵人", atkKindNum)) {
            System.out.println("攻擊成功的位置 : " + el);
            if (String.valueOf(XY).equals(String.valueOf(el))) {//冠宇寫的
                success = true;
            }
        }
        System.out.println("敵人是否攻擊成功 : " + success);
        if (success) {
            txt_com_hp.setText(Integer.parseInt(txt_com_hp.toString()) - getAtkHP(atkKindNum));
        }
    }

    public void initConnect(View view) {
        connectManager.initiateSocketConnection();
        txt_self.setText("");
        txtCom.setText("");
        txtWinLose.setText("");
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
        System.out.println("移動的座標x : " + x);
        System.out.println("移動的座標y : " + y);
        imagePlayer.layout(locationX[x].getLeft() + 30, locationY[y].getTop() - 200, locationX[x].getLeft() + 100 + 30, locationY[y].getBottom());
    }


}