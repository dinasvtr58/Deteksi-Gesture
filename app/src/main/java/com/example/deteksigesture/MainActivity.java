package com.example.deteksigesture;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class MainActivity<gestureDetector> extends AppCompatActivity {
    ImageView imageView;
    Matrix matrix = new Matrix();
    Float scale = 1f;
    ScaleGestureDetector SGD;
    private GestureDetectorCompat mGestureDetector;
    private ImageView gestureType;
    private float x1, x2, y1, y2, a1, a2;
    private static int MIN_DISTANCE = 150;
    final static float move = 200;
    float ratio = 1.0f;
    int baseDist;
    float baseRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetector = new GestureDetectorCompat(this, new GestureListener());
        imageView=findViewById(R.id.image_view);
        SGD = new ScaleGestureDetector(this, new ScaleListener());

        //drag
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float xDown = 0, yDown = 0;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();

                        float distanceX=movedX-xDown;
                        float distanceY=movedY-yDown;

                        imageView.setX(imageView.getX()+distanceX);
                        imageView.setY(imageView.getY()+distanceY);

                        xDown=movedX;
                        yDown=movedY;

                        break;
                }
                return true;

            }
        });

    }
    //pinch
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale * detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 10f));
            matrix.setScale(scale, scale);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }
    //swipe
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);
        mGestureDetector.onTouchEvent(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x1 = Math.round(event.getX());
                y1 = Math.round(event.getY());
                break;
            case MotionEvent.ACTION_UP:
                x2 = Math.round(event.getX());
                y2 = Math.round(event.getY());

                float Xawal = x1;
                float Yawal = y1;
                float valueX = x2 - x1;
                float valueY = y2 - y1;

                if (Math.abs(valueX) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        Toast.makeText(MainActivity.this, "Right Swipe\nKoordinat Awal: " +Xawal + ", " + Yawal + "\nJarak Perpindahan X: "+ valueX + ", Y: " + valueY, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Left Swipe\nKoordinat Awal: " +Xawal + ", " + Yawal + "\nJarak Perpindahan X: "+ valueX + ", Y: " + valueY, Toast.LENGTH_LONG).show();

                    }
                } else if (Math.abs(valueY) > MIN_DISTANCE) {
                    if (y2 > y1) {
                        Toast.makeText(MainActivity.this, "Bottom Swipe\nKoordinat Awal: " +Xawal + ", " + Yawal + "\nJarak Perpindahan X: "+ valueX + ", Y: " + valueY, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Top Swipe\nKoordinat Awal: " +Xawal + ", " + Yawal + "\nJarak Perpindahan X: "+ valueX + ", Y: " + valueY, Toast.LENGTH_LONG).show();
                    }
                }
        }
        mGestureDetector.onTouchEvent(event);
        SGD.onTouchEvent(event);


        return super.onTouchEvent(event);
    }
    private int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx*dx+dy*dy));
    }

   private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        //fling
       @Override
       public boolean onFling(MotionEvent e, MotionEvent e2, float velocityX, float velocityY){
            int x = (int) e.getX();
            int y = (int) e.getY();
            Toast.makeText(MainActivity.this,"Fling\nKoordinate: " + x + ", " + y,Toast.LENGTH_SHORT).show();
            return false;
        }
        //double tap
       @Override
        public boolean onDoubleTap(MotionEvent e){
            int x = (int) e.getX();
            int y = (int) e.getY();
            Toast.makeText(MainActivity.this,"Double Tap\nKoordinate: " + x + ", " + y,Toast.LENGTH_SHORT).show();
            return false;
        }
        //touch
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            Toast.makeText(MainActivity.this,"Touch\nKoordinate: " + x + ", " + y,Toast.LENGTH_SHORT).show();
            return false;
        }
        //long press
        @Override
        public void onLongPress(MotionEvent e){
            int x = (int) e.getX();
            int y = (int) e.getY();
            Toast.makeText(MainActivity.this,"Long Press\nKoordinate: " + x + ", " + y,Toast.LENGTH_SHORT).show();
            super.onLongPress(e);
        }

    }
    public boolean onTouchEvent(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        SGD.onTouchEvent(event);
        return true;
    }



}
