package com.home.mora;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView txtCom;
    private int comMora;
    private TextView txtWinLose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCom = findViewById(R.id.txt_com);
        txtWinLose = findViewById(R.id.txt_winLose);
    }

    public void stone(View view) {
        comMora = (int) (Math.floor(Math.random() * 3));
        switch (comMora) {
            case 0:
                txtCom.setText("電腦出拳頭");
                txtWinLose.setText("平手");
                break;
            case 1:
                txtCom.setText("電腦出剪刀");
                txtWinLose.setText("你贏了");
                break;
            case 2:
                txtCom.setText("電腦出布");
                txtWinLose.setText("你輸了");
                break;
        }

    }

    public void scissors(View view) {
        comMora = (int) (Math.floor(Math.random() * 3));
        switch (comMora) {
            case 0:
                txtCom.setText("電腦出拳頭");
                txtWinLose.setText("你輸了");
                break;
            case 1:
                txtCom.setText("電腦出剪刀");
                txtWinLose.setText("平手");
                break;
            case 2:
                txtCom.setText("電腦出布");
                txtWinLose.setText("你贏了");
                break;
        }
    }

    public void cloth(View view) {
        comMora = (int) (Math.floor(Math.random() * 3));
        switch (comMora) {
            case 0:
                txtCom.setText("電腦出拳頭");
                txtWinLose.setText("你贏了");
                break;
            case 1:
                txtCom.setText("電腦出剪刀");
                txtWinLose.setText("你輸了");
                break;
            case 2:
                txtCom.setText("電腦出布");
                txtWinLose.setText("平手");
                break;
        }
    }
}