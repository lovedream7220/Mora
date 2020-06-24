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
//    private GameActivity activity;

    private static final String SERVER_PATH = "http://9262e4d681cb.ngrok.io";

    /**
     * 用來避免改變tgBtn狀態時不知道是收到還是發送的狀況
     */
    public boolean isReceiving = false;

    //TODO
//    private SkillUtil util = new SkillUtil();

    public ConnectManager(Context context) {
        this.context = context;
//        activity = (GameActivity) context;
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

        View btnNo = findViewById(R.id.btnNo);
        View btnYes = findViewById(R.id.btnYes);
        View toNight = findViewById(R.id.toNight);

        isReceiving = true;
        try {
            switch (jsonObject.getString("kind")) {
                case "createRoom":
                    /**
                     * 0.【kind】:createRoom
                     * 1.獲得房間號碼放到create1234 【roomNum】:9453
                     * **/
                    /** 1.*/
                    TextView create1234 = findViewById(R.id.create1234);
                    create1234.setText(jsonObject.getString("roomNum"));

                    isReceiving = false;
                    break;
                case "joinRoom":
                    /**
                     * 開房者 :
                     * 0.【kind】:joinRoom
                     * 1.獲得目前加入的人的名稱
                     * 2.獲得目前人數
                     * 進房者 :
                     * 1.獲得是否成功加入房間
                     * 2.獲得連上的人的名稱
                     * **/
                    TextView text1 = findViewById(R.id.textView1);
                    TextView text2 = findViewById(R.id.textView2);

                    if (jsonObject.getBoolean("joinBoolean")) {
                        JSONObject j = new JSONObject(jsonObject.getString("otherPeople"));
                        String roomerName = j.getString("roomerName");
                        System.out.println("roomerName : " + roomerName);
                        String roomNumber = j.getString("roomNumber");
                        JSONArray ip = j.getJSONArray("ip");

//                        roomerName = roomerName.substring(2,(roomerName.length()-2));

                        EditText rn = findViewById(R.id.roomEditText);
                        util.setAnnouncement(text1, text2, roomerName + "的房間(" + ip.length() + "人)", "連線房間號 : " + roomNumber);
                        Toast.makeText(context, "連線房間號 : " + rn.getText(), Toast.LENGTH_SHORT).show();
                        cancelConnect();
                    } else {
                        util.setAnnouncement(text1, text2, "查無此房間");
                        Toast.makeText(context, "查無此房間", Toast.LENGTH_SHORT).show();
                    }
                    isReceiving = false;
                    break;
                case "position":
                    int pos = 0;
                    boolean btnOn;
                    pos = jsonObject.getInt("position");
                    btnOn = jsonObject.getBoolean("btnOn");
                    activity.order.get(pos - 1).setChecked(btnOn);
                    isReceiving = false;
                    break;
                case "toNightClick":
                    activity.onClickDayNight(toNight);
                    isReceiving = false;
                    break;
                case "checkClick":
                    boolean bool = jsonObject.getBoolean("choose");
                    if (bool) {
                        activity.onClickCheck(btnYes);
                    } else {
                        activity.onClickCheck(btnNo);
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
                String userId;
                userId = activity.getSharedPreferences("name", MODE_PRIVATE)
                        .getString("USER", "");
                jsonObject.put("USER", userId);
                switch (kind) {
                    case "createRoom"://USER
                        /**
                         * 1.發送自己的名稱
                         * **/
                        /**使用者名稱 : if沒有使用者名稱 紀錄使用者名稱*/
                        webSocket.send(jsonObject.toString());
                        break;
                    case "joinRoom"://USER & roomNum
                        /**
                         * 1.發送自己的名稱
                         * 2.發送輸入的房間號碼
                         * **/
                        EditText roomEditText = findViewById(R.id.roomEditText);
                        jsonObject.put("roomNum", roomEditText.getText().toString());
                        webSocket.send(jsonObject.toString());
                        break;
                    case "position":
                        jsonObject.put("position", num);
                        jsonObject.put("btnOn", bool);
                        webSocket.send(jsonObject.toString());
                        break;
                    case "toNightClick":
                        webSocket.send(jsonObject.toString());
                        break;
                    case "checkClick":
                        jsonObject.put("choose", bool);
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
        View layout_pair = findViewById(R.id.layout_pair);
        View layout_pair1 = findViewById(R.id.layout_pair1);
        layout_pair.setVisibility(View.INVISIBLE);
        layout_pair1.setVisibility(View.INVISIBLE);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return activity.findViewById(id);
    }
}
