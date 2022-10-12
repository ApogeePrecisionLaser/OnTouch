package com.example.ontouch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ZoomActivity extends AppCompatActivity  implements View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener {

//    int mBaseDistance;
//    float mRation = 1.0f;
//    float mBaseRatio;
//    final static float step = 200;

    View view;
    ImageView imageView;
    TextView xtext,ytext,t2x,t2y;
    RelativeLayout zoomLayout;
    ScaleGestureDetector scaleDetector;
    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 5.0f;
    private Display display;
    private Point size;
    private Mode mode = Mode.NONE;

    enum Mode {
        NONE,
        DRAG,
        ZOOM
    }
    int device_width;
    private int sWidth;
    private boolean isEnable = true;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;
    // Where the finger first  touches the screen
    private float startX = 0f;
    private float startY = 0f;
    // How much to translate the canvas
    private float dx = 0f;
    private float dy = 0f;
    private float prevDx = 0f;
    private float prevDy = 0f;

    Bitmap bitmap;
   // Canvas canvas;
//    Paint paint;

    private Rect rectangle;
    float sideLength = 100;
    Paint paint = new Paint();
    MyCanvas canvas1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
//        view = findViewById(R.id.textview);

//        xtext = findViewById(R.id.tx);
//        ytext = findViewById(R.id.ty);

        imageView = findViewById(R.id.imageView1);
        t2x = findViewById(R.id.t2x);
        t2y = findViewById(R.id.t2y);

        zoomLayout = findViewById(R.id.relative_layout);
/////////
        canvas1 = findViewById(R.id.myview);
/////////
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        sWidth = size.x;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        device_width = displayMetrics.widthPixels;
        canvas1.setOnTouchListener(this);
        scaleDetector = new ScaleGestureDetector(getApplicationContext(), this);
        canvas1.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = canvas1.getScrollX();
                int scrollY = canvas1.getScrollY();
//                xcoordinate1.setText(Integer.toString(scrollX));
//                ycoordinate1.setText(Integer.toString(scrollY));
//                Log.d(TAG, "scrollX: "+"scrollY:" + scrollX +scrollY);
                Toast.makeText(getApplicationContext(),"Current X is :"+scrollX + "\n" +"Current Y is : "+scrollY,Toast.LENGTH_SHORT).show();
            }
        });
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if(event.getPointerCount() == 2){
//            int action = event.getAction();
//            int puraction = action & MotionEvent.ACTION_MASK;
//            if(puraction == MotionEvent.ACTION_POINTER_DOWN){
//                mBaseDistance = getDistance(event);
//                mRation = mBaseRatio;
//            }
//            else{
//                float delta = (getDistance(event)-mBaseDistance)/step;
//                float multi = (float)(Math.pow(2,delta));
//                mRation = Math.min(1024.0f, Math.max(0.1f,mBaseRatio * multi));
//                text.setTextSize(mRation+13);
//            }
//        }
//        return true;
//    }

//    public boolean onTouchEvent(MotionEvent event){return true;}
//    int getDistance(MotionEvent motionEvent){
//        int dx = (int)(motionEvent.getX(0)-motionEvent.getX(1));
//        int dy = (int)(motionEvent.getX(0)-motionEvent.getX(1));
//        return  (int)(Math.sqrt(dx * dx + dy * dy));
//    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = scaleDetector.getScaleFactor();
        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
        } else {
            lastScaleFactor = 0;
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                if (scale > MIN_ZOOM) {
                    mode = Mode.DRAG;
                    startX = event.getX() - prevDx;
                    startY = event.getY() - prevDy;
                    int differencex = (int) (startX-prevDx);
                    int differencey = (int) (startY-prevDy);
//                    Toast.makeText(this, "Start X is :"+startX + "\n" + "Start Y is :"+startY, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this, "Prev X is :"+prevDx + "\n" + "Prev Y is :"+prevDy, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(this, "Difference X is :"+differencex + "\n" + "Difference Y is :"+differencey, Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isEnable = false;
                if (mode == Mode.DRAG) {
                    dx = event.getX() - startX;
                    dy = event.getY() - startY;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = Mode.ZOOM;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = Mode.DRAG;
                break;
            case MotionEvent.ACTION_UP:
                mode = Mode.NONE;
                prevDx = dx;
                prevDy = dy;
                break;
        }
        scaleDetector.onTouchEvent(event);
        if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM) {
            zoomLayout.requestDisallowInterceptTouchEvent(true);
            float maxDx = (child().getWidth() - (child().getWidth() / scale)) / 2 * scale;
            float maxDy = (child().getHeight() - (child().getHeight() / scale)) / 2 * scale;
            dx = Math.min(Math.max(dx, -maxDx), maxDx);
            dy = Math.min(Math.max(dy, -maxDy), maxDy);
            int differencex = (int) (maxDx-dx);
            int differencey = (int) (maxDy-dy);
            t2x.setText(Float.toString(maxDx));
            t2y.setText(Float.toString(maxDy));
//            Toast.makeText(this, "Difference x" +differencex + "\n" +"Difference y" +differencey, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),"Current X is :"+dx + "\n" +"Current Y is : "+dy,Toast.LENGTH_SHORT).show();
            applyScaleAndTranslation();
        }
        return true;
    }

    private void applyScaleAndTranslation() {
        child().setScaleX(scale);
        child().setScaleY(scale);
        child().setTranslationX(dx);
        child().setTranslationY(dy);
    }

    private View child() {
        return zoomLayout(0);
    }

    private View zoomLayout(int i) {
        return canvas1;
    }

}