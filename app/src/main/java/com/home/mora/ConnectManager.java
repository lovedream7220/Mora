package com.home.mora;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ConnectManager extends AppCompatActivity {

    private Context context;
    private WebSocket webSocket;
    private MainActivity activity;
    private TextView txtCom;
    private TextView txtWinLose;
    private static final String SERVER_PATH = "http://37a9bca594f8.ngrok.io";

    /**
     * 用來避免改變tgBtn狀態時不知道是收到還是發送的狀況
     */
    public boolean isReceiving = false;

    //TODO
//    private SkillUtil util = new SkillUtil();

    public ConnectManager(Context context) {
        this.context = context;
        activity = (MainActivity) context;
    }

    public ConnectManager() {
    }

    public void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        System.out.println("request : " + request);
        if (request != null) {
            webSocket = client.newWebSocket(request, new SocketListener());
        }
    }

    private class SocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            runOnUiThread(() -> {
                Toast.makeText(context, "連線成功 Socket Connection Successful",
                        Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(() -> {
                try {
                    System.out.println(text);
                    JSONArray jsonArray = new JSONArray("[" + text + "]");
//                    System.out.println(jsonArray.get(0).getClass());
//                    JSONObject jsonObject = new JSONObject(text);
//                    JSONObject jsonObject =
//                    System.out.println(jsonObject.getString());
                    receiveMessage(jsonArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            System.err.println("throwable:" + t);
            System.err.println("response:" + response);
        }
    }


    /**
     * bind with GameActivity
     *
     * @param
     */
    private void receiveMessage(JSONArray jsonArray) {
//        isReceiving = true;
        activity.visibility();//收到
        int totalTime = 4;

//        new Handler().postDelayed(() -> {
//            rm(0, 1, jsonArray);
//        }, 1500);
//        new Handler().postDelayed(() -> {
//            rm(1, 2, jsonArray);
//        }, 3000);
//        new Handler().postDelayed(() -> {
//            rm(2, 3, jsonArray);
//        }, 4500);
//        new Handler().postDelayed(() -> {
//            rm(3, 4, jsonArray);
//        }, 6000);

//        new CountDownTimer(totalTime * 1000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                int second = Math.round(millisUntilFinished / 1000);
//                    int startTime = totalTime - second;
//                    rm(startTime, startTime + 1, jsonArray);
//                    System.out.println("倒數計時 : " + Math.round(millisUntilFinished / 1000));
//            }
//            public void onFinish() {
//            }
//        }.start();




        for (int i = 0; i < 4; i++) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("JSON_STRING", jsonArray.toString());
            bundle.putInt("NUM", i);
            System.out.println("i : " + i);
            msg.setData(bundle);
            msg.what = JSON_STRING;
            handler.sendMessageDelayed(msg, 1000 * (i + 1));
        }
    }

    private static final int JSON_STRING = 1;
    private MyHandler handler = new MyHandler();


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case JSON_STRING:
                    String x = msg.getData().getString("JSON_STRING");
                    int start = msg.getData().getInt("NUM");
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(x);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    rm(start, start + 1, jsonArray);
                    break;
                default:
                    break;
            }
        }
    }


    public void rm(int start, int end, JSONArray jsonArray) {
        try {
            for (int i = start; i < end; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String sender = jsonObject.getString("USER");//被操作人是誰
                System.out.println("執行 : " + i);
                switch (jsonObject.getString("kind")) {
                    case "move":
                        int x = jsonObject.getInt("x");
                        int y = jsonObject.getInt("y");
                        System.out.println("是不是自己要移動 : " + sender.equals(activity.userName));
                        if (sender.equals(activity.userName)) {
                            activity.moveRules.moveJudgmentSelf(x, y);
                        } else {
                            activity.moveRules.moveJudgmentCom(x, y);
                        }
                        break;
                    case "up":
                        int l = jsonObject.getInt("l");
                        String pp = jsonObject.getString("pp");
                        System.out.println("是不是自己要回復 " + pp + " : " + sender.equals(activity.userName));
                        if (sender.equals(activity.userName)) {
                            activity.upRules.upJudgmentSelf(l, pp);
                        } else {
                            activity.upRules.upJudgmentCom(l, pp);
                        }
                        break;
                }
            }
            for (int i = start; i < end; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String sender = jsonObject.getString("USER");//被操作人是誰
                switch (jsonObject.getString("kind")) {
                    case "atk":
                        System.out.println("執行 : " + i);
                        System.out.println("是不是自己要攻擊 : " + sender.equals(activity.userName));
                        int atk = jsonObject.getInt("atk");
                        System.out.println("使用第 " + atk + " 招");
                        if (sender.equals(activity.userName)) {
                            activity.atkRules.atkJudgmentSelf(atk);
                        } else {
                            activity.atkRules.atkJudgmentCom(atk);
                        }
                        break;
                }
            }
            activity.init();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * bind with GameActivity
     *
     * @param num
     * @param kind
     */
    public void sendMessage(int num, String kind) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("kind", kind);
            jsonObject.put("USER", activity.userName);
            switch (kind) {
                case "mora":
                    jsonObject.put("choose", num);
                    webSocket.send(jsonObject.toString());
                    break;
                case "atk":
                    jsonObject.put("atk", num);
                    webSocket.send(jsonObject.toString());
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(int step, int x, int y, String kind) {
//        if (!isReceiving) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("kind", kind);
            jsonObject.put("USER", activity.userName);
            switch (kind) {
                case "move":
//                    jsonObject.put("step", step);
                    jsonObject.put("x", x);
                    jsonObject.put("y", y);
                    webSocket.send(jsonObject.toString());
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        }
    }


    public void sendMessage(int l, String pp, String kind) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("kind", kind);
            jsonObject.put("USER", activity.userName);
            switch (kind) {
                case "up":
                    jsonObject.put("l", l);
                    jsonObject.put("pp", pp);
                    webSocket.send(jsonObject.toString());
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * bind with GameActivity
     */
    public void cancelConnect() {
//        View layout_pair = findViewById(R.id.layout_pair);
//        View layout_pair1 = findViewById(R.id.layout_pair1);
//        layout_pair.setVisibility(View.INVISIBLE);
//        layout_pair1.setVisibility(View.INVISIBLE);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return activity.findViewById(id);
    }
}
