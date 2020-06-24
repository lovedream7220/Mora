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
    private static final String SERVER_PATH = "http://6c6bc0455966.ngrok.io";

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
                    JSONObject jsonObject = new JSONObject(text);
                    receiveMessage(jsonObject);
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
     * @param jsonObject
     */
    private void receiveMessage(JSONObject jsonObject) {

        isReceiving = true;
        try {
            switch (jsonObject.getString("kind")) {
                case "mora":
                    int opponent = jsonObject.getInt("choose");
                    System.out.println(opponent);
                    txtCom = findViewById(R.id.txt_com);
                    txtWinLose = findViewById(R.id.txt_winLose);
                    if (activity.playerMora == 0) {
                        switch (opponent) {
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
                    } else if (activity.playerMora == 1) {
                        switch (opponent) {
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
                    } else if (activity.playerMora == 2) {
                        switch (opponent) {
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

                    isReceiving = false;
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * bind with GameActivity
     *
     * @param num
     * @param bool
     * @param kind
     */
    public void sendMessage(int num, boolean bool, String kind) {
        if (!isReceiving) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("kind", kind);
                switch (kind) {
                    case "mora":
                        jsonObject.put("choose", num);
                        webSocket.send(jsonObject.toString());
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
