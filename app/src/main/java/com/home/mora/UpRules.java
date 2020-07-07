package com.home.mora;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpRules {
    private MainActivity activity;
    private Context context;

    public UpRules(Context context) {
        this.context = context;
        this.activity = (MainActivity) context;

    }



    public void upJudgmentSelf(int l, String pp) {
        if (pp.equals("hp")) {
            activity.controlMPHP(activity.txt_self_hp, l);
        } else if (pp.equals("mp")) {
            activity.controlMPHP(activity.txt_self_mp, l);
        }

    }

    public void upJudgmentCom(int l, String pp) {
        if (pp.equals("hp")) {
            activity.controlMPHP(activity.txt_com_hp, l);
        } else if (pp.equals("mp")) {
            activity.controlMPHP(activity.txt_com_mp, l);
        }
    }
}