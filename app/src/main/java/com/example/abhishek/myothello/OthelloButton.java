package com.example.abhishek.myothello;

import android.content.Context;
import android.widget.Button;

public class OthelloButton extends Button{

    public int colorcode=0;
    public int backcolorcode=0;
    public int row =0;
    public int col =0;

    public OthelloButton(Context context) {
        super(context);
    }
    public void setColorcode(int backcolor,int buttoncolor){
        colorcode=backcolor;
        backcolorcode=buttoncolor;

    }


}
