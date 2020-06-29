package com.home.mora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.WebSocket;

import static com.home.mora.playerMoraList.*;

public class MainActivity extends AppCompatActivity {
    /**0626讓冠宇看懂的連線版本 */
    /**
     * 0627 :
     * 1.增加按鈕可以重新連線 initConnect
     * 2.
     */
    private TextView txtCom, txtWinLose, txt_self;

    private EditText roomEditText;
    //    public String userName;
    WebSocket webSocket;
    ConnectManager connectManager = new ConnectManager(this);
    /**
     * 命名用途 只有不同名字的人才可以連線成功　TODO
     */
    String[] text1 = {"預言家", "女巫", "獵人", "騎士", "守衛", "禁言長老",
            "魔術師", "通靈師", "熊", "白癡", "炸彈人", "守墓人", "九尾妖狐"};
    public String userName = text1[(int) (Math.random() * text1.length)] + (int) (Math.random() * 999 + 1);

//    public enum playerMoraList {
//        石頭, 剪刀, 布, 還沒出
//    }

    public playerMoraList playerMora = 還沒出;
    /**
     * 紀錄自己的出拳
     */
    public playerMoraList comMora = 還沒出;

    /**
     * 紀錄別人的出拳
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCom = findViewById(R.id.txt_com);
        txtWinLose = findViewById(R.id.txt_winLose);
        connectManager.initiateSocketConnection();
        txt_self = findViewById(R.id.txt_self);
        roomEditText = findViewById(R.id.roomEditText);
        roomEditText.setHint(userName);
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

    public void moraCommon(int mora, playerMoraList playerMoraList, String txt) {
        connectManager.sendMessage(mora, true, "mora");
        playerMora = playerMoraList;
        txt_self.setText(txt);
        if (txtCom.getText().equals("對手已出拳...")) {
            mainJudgment(comMora.getId());
        }
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();

    }

    public void stone(View view) {
        moraCommon(0, 石頭, "出拳頭");
    }

    public void scissors(View view) {
        moraCommon(1, 剪刀, "出剪刀");
    }

    public void cloth(View view) {
        moraCommon(2, 布, "出布");
    }

    public void initConnect(View view) {
        connectManager.initiateSocketConnection();
        txt_self.setText("");
        txtCom.setText("");
        txtWinLose.setText("");
        Toast.makeText(this, "重新連線", Toast.LENGTH_SHORT).show();
    }

    public void initStart(View view) {
        txt_self.setText("");
        txtCom.setText("");
        txtWinLose.setText("");
        playerMora = 還沒出;
        comMora = 還沒出;
        Toast.makeText(this, "重新開始", Toast.LENGTH_SHORT).show();
    }


    public void mainJudgment(int opponent) {
        System.out.println("comMora : " + opponent);
        comMora = enumOfId(opponent);
        System.out.println("comMora : " + comMora);

        if (playerMora == 石頭) {
            switch (comMora) {
                case 石頭:
                    txtCom.setText("對手出拳頭");
                    txtWinLose.setText("平手");
                    break;
                case 剪刀:
                    txtCom.setText("對手出剪刀");
                    txtWinLose.setText("你贏了");
                    break;
                case 布:
                    txtCom.setText("對手出布");
                    txtWinLose.setText("你輸了");
                    break;
            }
        } else if (playerMora == 剪刀) {
            switch (comMora) {
                case 石頭:
                    txtCom.setText("對手出拳頭");
                    txtWinLose.setText("你輸了");
                    break;
                case 剪刀:
                    txtCom.setText("對手出剪刀");
                    txtWinLose.setText("平手");
                    break;
                case 布:
                    txtCom.setText("對手出布");
                    txtWinLose.setText("你贏了");
                    break;
            }
        } else if (playerMora == 布) {
            switch (comMora) {
                case 石頭:
                    txtCom.setText("對手出拳頭");
                    txtWinLose.setText("你贏了");
                    break;
                case 剪刀:
                    txtCom.setText("對手出剪刀");
                    txtWinLose.setText("你輸了");
                    break;
                case 布:
                    txtCom.setText("對手出布");
                    txtWinLose.setText("平手");
                    break;
            }
        } else if (playerMora == 還沒出) {
            txtCom.setText("對手已出拳...");
            txtWinLose.setText("對手已出拳...");
        }
    }

}