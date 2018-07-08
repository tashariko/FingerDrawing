package com.tashariko.library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;


/**
 * Created by tashariko on 26/05/17.
 */

public class CustomCanvasForDraw extends RelativeLayout implements CustomDrawView.GetCoordinateCallback, CustomDrawView.IsDebugEnabled{

    private CustomDrawView customDrawView;

    private boolean isDebugEnabled=true;

    public CustomCanvasForDraw(Context context) {
        this(context,null);
    }

    public CustomCanvasForDraw(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCanvasForDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_canvas_for_draw,this,true);

        customDrawView=findViewById(R.id.mainView);
        customDrawView.setThisCallback(this);

    }

    public void setDebugMode(boolean isDebugEnabled){
        this.isDebugEnabled=isDebugEnabled;
    }

    public void changeColor(int color){
        customDrawView.setDrawColor(color);
    }

    public void undoView(){
        customDrawView.undoPath();
    }

    public void adjustWidth(boolean decreaseWidth){
        customDrawView.adjustWidth(decreaseWidth);
    }

    public void resetView(){
        customDrawView.resetView();
    }

    public void setLineParameters(int defaultLineSpan, int defaultLineWidth, int minLineWidth, int maxLineWidth){
       customDrawView.setLineParameters(defaultLineSpan,defaultLineWidth,minLineWidth,maxLineWidth);
    }

    public void setLineParameters(int defaultLineWidth) {
        customDrawView.setLineParameters(defaultLineWidth);
    }

    @Override
    public void moving(float x, float y) {
        if(isDebugEnabled) {
            Log.i("Finger_Moving: ", String.format("%.02f", x) + ", " + String.format("%.02f", y));
        }
    }

    @Override
    public void start(float x, float y) {
        if(isDebugEnabled) {
            Log.i("Finger_Start: ", String.format("%.02f", x) + ", " + String.format("%.02f", y));
        }
    }

    @Override
    public void end(float x, float y) {
        if(isDebugEnabled) {
            Log.i("Finger_End: ", String.format("%.02f", x) + ", " + String.format("%.02f", y));
        }
    }

    @Override
    public boolean debuggable() {
        return isDebugEnabled;
    }

}