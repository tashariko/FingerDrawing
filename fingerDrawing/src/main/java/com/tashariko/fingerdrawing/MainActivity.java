package com.tashariko.fingerdrawing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tashariko.library.CustomCanvasForDraw;

public class MainActivity extends AppCompatActivity {

    CustomCanvasForDraw customCanvasForDraw;
    Button resetButton;
    Button sizeMinusButton;
    Button sizePlusButton;
    Button colorButton;
    Button undoButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        customCanvasForDraw.setDebugMode(true);

        customCanvasForDraw.setLineParameters(10,10,10,50);
        customCanvasForDraw.setLineParameters(10);

        listeners();
    }


    private void resetView() {
        customCanvasForDraw.resetView();
    }


    private void initViews() {
        customCanvasForDraw = findViewById(R.id.customCanvas);
        resetButton = findViewById(R.id.resetButton);
        sizeMinusButton = findViewById(R.id.sizeMinusButton);
        sizePlusButton = findViewById(R.id.sizePlusButton);
        colorButton = findViewById(R.id.colorButton);
        undoButton = findViewById(R.id.undoButton);
    }


    private void listeners() {
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
            }
        });

        sizeMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvasForDraw.adjustWidth(true);
            }
        });

        sizePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvasForDraw.adjustWidth(false);
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvasForDraw.changeColor(R.color.colorAccent);
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvasForDraw.undoView();
            }
        });
    }

}
