package com.home.mora;

import android.content.Context;
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
    private static final String SERVER_PATH = "http://d5ccb4eaa732.ngrok.io";

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

        activity.visibility();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String sender = jsonObject.getString("USER");//被操作人是誰
                switch (jsonObject.getString("kind")) {
                    case "move":
                        System.out.println("執行 : " + i);
                        int x = jsonObject.getInt("x");
                        int y = jsonObject.getInt("y");
                        System.out.println("是不是自己要移動 : " + sender.equals(activity.userName));
                        if (sender.equals(activity.userName)) {
                            activity.moveJudgmentSelf(x, y);
                        } else {
                            activity.moveJudgmentCom(x, y);
                        }
                        break;
                }
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String sender = jsonObject.getString("USER");//被操作人是誰
                switch (jsonObject.getString("kind")) {
                    case "atk":
                        System.out.println("執行 : " + i);
                        System.out.println("是不是自己要攻擊 : " + sender.equals(activity.userName));
                        int atk = jsonObject.getInt("atk");
                        System.out.println("使用第 " + atk + " 招");
                        if (sender.equals(activity.userName)) {
                            activity.atkJudgmentSelf(atk);
                        } else {
                            activity.atkJudgmentCom(atk);
                        }
                        break;
                }
            }
            activity.confirmPlace();
            activity.initAtkRange();

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
