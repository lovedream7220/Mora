package com.home.mora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {


    private TextView txtCom, txtWinLose;
    public int playerMora;
    private EditText roomEditText;
    WebSocket webSocket;
    ConnectManager connectManager = new ConnectManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCom = findViewById(R.id.txt_com);
        txtWinLose = findViewById(R.id.txt_winLose);
        connectManager.initiateSocketConnection();

        roomEditText = findViewById(R.id.roomEditText);
        roomEditText.setHint("----");
    }

    public void edit(View view) {

    }

    public void stone(View view) {
//        comMora = (int) (Math.floor(Math.random() * 3));
        connectManager.sendMessage(0, true, "mora");
        playerMora = 0;
        Toast.makeText(this, "出拳頭", Toast.LENGTH_SHORT).show();
//        switch (comMora) {
//            case 0:
//                txtCom.setText("電腦出拳頭");
//                txtWinLose.setText("平手");
//                break;
//            case 1:
//                txtCom.setText("電腦出剪刀");
//                txtWinLose.setText("你贏了");
//                break;
//            case 2:
//                txtCom.setText("電腦出布");
//                txtWinLose.setText("你輸了");
//                break;
//        }

    }

    public void scissors(View view) {
//        comMora = (int) (Math.floor(Math.random() * 3));
        connectManager.sendMessage(1, true, "mora");
        playerMora = 1;
        Toast.makeText(this, "出剪刀", Toast.LENGTH_SHORT).show();
//        switch (comMora) {
//            case 0:
//                txtCom.setText("電腦出拳頭");
//                txtWinLose.setText("你輸了");
//                break;
//            case 1:
//                txtCom.setText("電腦出剪刀");
//                txtWinLose.setText("平手");
//                break;
//            case 2:
//                txtCom.setText("電腦出布");
//                txtWinLose.setText("你贏了");
//                break;
//        }
    }

    public void cloth(View view) {
//        comMora = (int) (Math.floor(Math.random() * 3));
        connectManager.sendMessage(2, true, "mora");
        Toast.makeText(this, "出布", Toast.LENGTH_SHORT).show();
        playerMora = 2;
//        switch (comMora) {
//            case 0:
//                txtCom.setText("電腦出拳頭");
//                txtWinLose.setText("你贏了");
//                break;
//            case 1:
//                txtCom.setText("電腦出剪刀");
//                txtWinLose.setText("你輸了");
//                break;
//            case 2:
//                txtCom.setText("電腦出布");
//                txtWinLose.setText("平手");
//                break;
//        }
    }


}