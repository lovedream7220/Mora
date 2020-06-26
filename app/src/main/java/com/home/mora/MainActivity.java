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

public class MainActivity extends AppCompatActivity {
    /**0626讓冠宇看懂的連線版本 */
    /**
     * 0627 :
     * 1.增加按鈕可以重新連線 initConnect
     * 2.
     */
    private TextView txtCom, txtWinLose, txt_self;
    public int playerMora;
    private EditText roomEditText;
    public String userName;
    WebSocket webSocket;
    ConnectManager connectManager = new ConnectManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCom = findViewById(R.id.txt_com);
        txtWinLose = findViewById(R.id.txt_winLose);
        connectManager.initiateSocketConnection();
        txt_self = findViewById(R.id.txt_self);
        roomEditText = findViewById(R.id.roomEditText);
        roomEditText.setHint("----");
        int i = (int) Math.random() * 100;
        userName = Integer.toString(i);
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

    public void stone(View view) {
        connectManager.sendMessage(0, true, "mora");
        playerMora = 0;
        txt_self.setText("出拳頭");
        Toast.makeText(this, "出拳頭", Toast.LENGTH_SHORT).show();

    }

    public void scissors(View view) {
        connectManager.sendMessage(1, true, "mora");
        playerMora = 1;
        txt_self.setText("出剪刀");
        Toast.makeText(this, "出剪刀", Toast.LENGTH_SHORT).show();
    }

    public void cloth(View view) {
        connectManager.sendMessage(2, true, "mora");
        txt_self.setText("出布");
        Toast.makeText(this, "出布", Toast.LENGTH_SHORT).show();
        playerMora = 2;
    }

    public void initConnect(View view) {
//        connectManager.sendMessage(2, true, "mora");
//        txt_self.setText("出布");
        connectManager.initiateSocketConnection();
        Toast.makeText(this, "重新連線", Toast.LENGTH_SHORT).show();
//        playerMora = 2;
    }

}