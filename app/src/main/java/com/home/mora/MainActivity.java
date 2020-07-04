package com.home.mora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {
    /**0626讓冠宇看懂的連線版本 */
    /**
     * 0627 :
     * 1.增加按鈕可以重新連線 initConnect
     * 2.
     */
    private TextView txt_self_name;
    private Button btnInitStart, btnInitConnect;
    public ImageView imagePlayer, imageCom;
    private EditText roomEditText;
    public int step;
    WebSocket webSocket;
    ConnectManager connectManager = new ConnectManager(this);
    public MoveRules moveRules = new MoveRules(this);
    public AtkRules atkRules = new AtkRules(this);

    /**
     * 命名用途 只有不同名字的人才可以連線成功　TODO
     */
    String[] text1 = {"預言家", "女巫", "獵人", "騎士", "守衛", "禁言長老",
            "魔術師", "通靈師", "熊", "白癡", "炸彈人", "守墓人", "九尾妖狐"};
    public String userName = text1[(int) (Math.random() * text1.length)] + (int) (Math.random() * 9999 + 1);

    public int locationXSelf = 0;
    public int locationYSelf = 1;
    public int locationXCom = 4;
    public int locationYCom = 1;
    private View lineX0, lineX1, lineX2, lineX3, lineX4, lineY0, lineY1, lineY2, lineY3, lineY4;
    public View atkKJ00, atkKJ10, atkKJ20, atkKJ30, atkKJ40;
    public View atkKJ01, atkKJ11, atkKJ21, atkKJ31, atkKJ41;
    public View atkKJ02, atkKJ12, atkKJ22, atkKJ32, atkKJ42;
    public Button initGame;
    public TextView txt_self_hp, txt_self_mp, txt_com_hp, txt_com_mp;
    public View button, button2, button3, button4, button5, button6;
    public View[] locationX;
    public View[] locationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("---------重新啟動---------");
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
        initGame = findViewById(R.id.initGame);
        initGame.setVisibility(View.INVISIBLE);
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
        button.setAlpha(0.2f);
        button2.setAlpha(0.2f);
        button3.setAlpha(0.2f);
        button4.setAlpha(0.2f);
        button5.setAlpha(0.2f);
        button6.setAlpha(0.2f);
    }

    public void openBtn() {
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button.setAlpha(1.0f);
        button2.setAlpha(1.0f);
        button3.setAlpha(1.0f);
        button4.setAlpha(1.0f);
        button5.setAlpha(1.0f);
        button6.setAlpha(1.0f);
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


    public void moveRight(View view) {
        moveRules.moveCommon(1, 0, "向右");
    }

    public void moveLeft(View view) {
        moveRules.moveCommon(-1, 0, "向左");
    }

    public void moveTop(View view) {
        moveRules.moveCommon(0, 1, "向上");
    }

    public void moveDown(View view) {
        moveRules.moveCommon(0, -1, "向下");
    }

    public void tool1(View view) {
        sendMessageMoveAtk(1);
    }

    public void tool2(View view) {
        sendMessageMoveAtk(2);
    }




    public void initConnect(View view) {
        connectManager.initiateSocketConnection();
        Toast.makeText(this, "重新連線", Toast.LENGTH_SHORT).show();
    }

    public void initStart(View view) {
        Toast.makeText(this, "重新開始", Toast.LENGTH_SHORT).show();
    }


    public void confirmPlace() {
        /**重新繪製位置*/
        imagePlayer.layout(locationX[locationXSelf].getLeft() + 30, locationY[locationYSelf].getTop() - 200, locationX[locationXSelf].getLeft() + 100 + 30, locationY[locationYSelf].getBottom());
        imageCom.layout(locationX[locationXCom].getLeft() + 30, locationY[locationYCom].getTop() - 200, locationX[locationXCom].getLeft() + 100 + 30, locationY[locationYCom].getBottom());
    }



    /**
     * 每次移動之後的初始化
     * 1.增加雙發完家的MP以及HP
     * 2.判斷攻擊技能是否可以使用
     * 3.判斷遊戲是否結束
     * 4.把攻擊範圍初始化
     */
    public void init() {
        openBtn();
        controlMPHP(txt_self_hp, 1);
        controlMPHP(txt_com_hp, 1);
        controlMPHP(txt_self_mp, 2);
        controlMPHP(txt_com_mp, 2);
        atkRules.MPLimit(1, button5);
        atkRules.MPLimit(2, button6);
        gameEnd();
        atkRules.initAtkRange();
    }


    public void controlMPHP(TextView PP, int add) {
        int MpBefore = Integer.parseInt(PP.getText().toString());
        int MpAfter = MpBefore + add;
        PP.setText(MpAfter + "");
        if (MpAfter >= 10) {
            PP.setText(10 + "");
        }
    }

    public void gameEnd() {
        if (Integer.parseInt(txt_self_hp.getText().toString()) <= 0 || Integer.parseInt(txt_com_hp.getText().toString()) <= 0) {
            initGame.setVisibility(View.VISIBLE);
            Toast.makeText(this, "遊戲結束", Toast.LENGTH_LONG).show();
            lockBtn();
        }
    }

    /**
     * 重新開始 初始化遊戲
     */
    public void initGame(View v) {
        initGame.setVisibility(View.INVISIBLE);
        txt_self_hp.setText("10");
        txt_com_hp.setText("10");
        txt_self_mp.setText("10");
        txt_com_mp.setText("10");
        locationXSelf = 0;
        locationYSelf = 1;
        locationXCom = 4;
        locationYCom = 1;
        visibility();
        openBtn();
        imagePlayer.layout(locationX[locationXSelf].getLeft() + 30, locationY[locationYSelf].getTop() - 200, locationX[locationXSelf].getLeft() + 100 + 30, locationY[locationYSelf].getBottom());
        imageCom.layout(locationX[locationXCom].getLeft() + 30, locationY[locationYCom].getTop() - 200, locationX[locationXCom].getLeft() + 100 + 30, locationY[locationYCom].getBottom());
    }


    public void sendMessageMove() {
        lockBtn();
        Toast.makeText(this, "移動", Toast.LENGTH_SHORT).show();
        connectManager.sendMessage(0, locationXSelf, locationYSelf, "move");
    }

    public void sendMessageMoveAtk(int atk) {
        lockBtn();
        Toast.makeText(this, "攻擊", Toast.LENGTH_SHORT).show();
        connectManager.sendMessage(atk, "atk");
    }

}