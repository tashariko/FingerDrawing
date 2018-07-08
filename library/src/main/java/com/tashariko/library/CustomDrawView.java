package com.tashariko.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by tashariko on 19/05/17.
 */

public class CustomDrawView extends android.support.v7.widget.AppCompatImageView {

    private static int DEFAULT_COLOR;

    private static int DEFAULT_LINE_WIDTH = 10;
    private static int DEFAULT_LINE_WIDTH_SPAN = 10;
    private static int DEFAULT_LINE_MIN_WIDTH = 10;
    private static int DEFAULT_LINE_MAX_WIDTH = 50;


    private ArrayList<Paint> paintPenList =new ArrayList<>();
    private Path latestPath;
    private Paint latestPaint;
    private ArrayList<Path> pathPenList =new ArrayList<>();
    private GetCoordinateCallback callbackForCoordinate;
    private IsDebugEnabled debugCallback;

    private int lineWidth = DEFAULT_LINE_WIDTH;
    private int currentColor;


    public CustomDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        DEFAULT_COLOR=ContextCompat.getColor(getContext(), R.color.colorAccent);
        currentColor=DEFAULT_COLOR;

        initPaintNPen(currentColor);

    }

    private void initPaintNPen(int color){

        latestPaint=getNewPaintPen(color);
        latestPath=getNewPathPen();

        paintPenList.add(latestPaint);
        pathPenList.add(latestPath);

    }

    private Path getNewPathPen() {
        Path path=new Path();
        return path;
    }

    private Paint getNewPaintPen(int color){

        Paint mPaintPen =new Paint();

        mPaintPen.setStrokeWidth(lineWidth);
        mPaintPen.setAntiAlias(true);
        mPaintPen.setDither(true);
        mPaintPen.setStyle(Paint.Style.STROKE);
        mPaintPen.setStrokeJoin(Paint.Join.MITER);
        mPaintPen.setStrokeCap(Paint.Cap.ROUND);
        mPaintPen.setColor(color);

        return mPaintPen;

    }

    @Override
    public boolean onTouchEvent( MotionEvent event) {
        float x=event.getX();
        float y=event.getY();

        if (debugCallback.debuggable()) {
            //Log.i("CO-ordinate", event.getX() + " : " + event.getY());
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            callbackForCoordinate.start(x, y);
            startPath(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            callbackForCoordinate.moving(x, y);
            updatePath(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            callbackForCoordinate.end(x, y);
            endPath(x, y);
        }
        invalidate();
        return true;
    }

    private void startPath(float x, float y) {
        initPaintNPen(currentColor);
        latestPath.moveTo(x,y);
    }

    private void updatePath(float x, float y) {
        latestPath.lineTo(x,y);
    }

    private void endPath(float x, float y) {

    }

    public void setDrawColor(int color) {

        currentColor=color;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<paintPenList.size();i++){
            canvas.drawPath(pathPenList.get(i),paintPenList.get(i));
        }
    }

    public void setThisCallback(CustomCanvasForDraw callback) {
        this.callbackForCoordinate=callback;
        this.debugCallback =  callback;
    }

    public void adjustWidth(boolean decrease){

        if(decrease){
            if(lineWidth >DEFAULT_LINE_MIN_WIDTH) {
                lineWidth = lineWidth - DEFAULT_LINE_WIDTH_SPAN;
            }
        }else{
            if(lineWidth <DEFAULT_LINE_MAX_WIDTH) {
                lineWidth = lineWidth + DEFAULT_LINE_WIDTH_SPAN;
            }
        }

        invalidate();
    }

    public void resetView() {
        currentColor=DEFAULT_COLOR;

        latestPath.reset();
        latestPaint.reset();

        pathPenList.clear();
        paintPenList.clear();
        lineWidth = DEFAULT_LINE_WIDTH_SPAN;

        initPaintNPen(currentColor);

        invalidate();
    }


    public void undoPath() {

        if(paintPenList.size()>1) {
            latestPaint = paintPenList.get(paintPenList.size() - 2);
            latestPath = pathPenList.get(pathPenList.size() - 2);

            paintPenList.remove(paintPenList.size() - 1);
            pathPenList.remove(pathPenList.size() - 1);

            currentColor=latestPaint.getColor();
            lineWidth= (int) latestPaint.getStrokeWidth();
        }else{
            resetView();
        }

        invalidate();
    }

    public void setLineParameters(int defaultLineSpan, int defaultLineWidth, int minLineWidth, int maxLineWidth){
        this.DEFAULT_LINE_WIDTH = defaultLineWidth;
        this.DEFAULT_LINE_WIDTH_SPAN = defaultLineSpan;
        this.DEFAULT_LINE_MAX_WIDTH = maxLineWidth;
        this.DEFAULT_LINE_MIN_WIDTH = minLineWidth;
    }

    public void setLineParameters(int defaultLineWidth) {
        this.DEFAULT_LINE_WIDTH = defaultLineWidth;
    }

    public interface GetCoordinateCallback {
        void moving(float x, float y);
        void start(float x, float y);
        void end(float x, float y);
    }

    public interface IsDebugEnabled {
        boolean debuggable();
    }

}

