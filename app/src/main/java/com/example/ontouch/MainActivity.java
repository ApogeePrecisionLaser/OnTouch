package com.example.ontouch;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
//    float downx=0,downy=0, rx=0,upy=0;
//    EditText xaxis,yaxis;
    TextView xaxis,yaxis,xcoordinate1,ycoordinate1;

    TextInputEditText xco,yco,colorcode;
    AppCompatButton button,clear;
    float xv,yv;
    ScrollView scrollView;
//    HorizontalScrollView scrollView;

    private ScaleGestureDetector mscalegesture;
    private float mScalefactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        scrollView = findViewById(R.id.scrollview);

        xaxis = findViewById(R.id.x);
        yaxis = findViewById(R.id.y);

        xco = findViewById(R.id.x1);
        yco = findViewById(R.id.y1);
        colorcode = findViewById(R.id.color);

        button = findViewById(R.id.press);
        colorcode = findViewById(R.id.color);
        clear = findViewById(R.id.clear);

        xcoordinate1 = findViewById(R.id.xcoordinate);
        ycoordinate1 = findViewById(R.id.ycoordinate);


        Log.e("value of Color",""+colorcode);
        String str=colorcode.toString();

        Display currentdisplay = getWindowManager().getDefaultDisplay();
        float dw = currentdisplay.getWidth();
        float dh = currentdisplay.getHeight();

        xaxis.setText(Float.toString(dw));
        yaxis.setText(Float.toString(dh));



//      On touch get the coordinates
//      bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);
//      canvas = new Canvas(bitmap);
//      paint = new Paint();
//      paint.setColor(Color.BLACK);
//      imageView.setImageBitmap(bitmap);
//
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                float x = event.getX();
//                float y = event.getY();
//
//                xaxis.setText(Float.toString(x));
//                yaxis.setText(Float.toString(y));
//
//                canvas.drawCircle(x,y,10,paint);
//                // Or //
//
//                int x1=0;
//                int y1=0;
//                Toast.makeText(MainActivity.this, "Touch Coordinates : " +String.valueOf(event.getX()) + "x1" + String.valueOf(event.getY()), Toast.LENGTH_SHORT).show();
////                xaxis.setText("Touch coordinates : " + String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
//                return true;
//            }
//        });

        mscalegesture = new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScalefactor *= detector.getScaleFactor();
                mScalefactor = Math.max(0.1f,Math.min(mScalefactor,10.0f));
                imageView.setScaleX(mScalefactor);
                imageView.setScaleY(mScalefactor);
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });

        bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              For integer value only
//              xv=Integer.parseInt(String.valueOf(xco.getText()));
//              yv=Integer.parseInt(String.valueOf(yco.getText()));

                String str=xco.getText().toString();
                xv = Float.parseFloat(str);
                String str1=yco.getText().toString();
                yv = Float.parseFloat(str1);

                paint = new Paint();
                paint.setColor(Color.parseColor(colorcode.getText().toString()));

                canvas.drawCircle(xv,yv,15,paint);
//                canvas.drawRect(10,10,10,10,paint);
                imageView.setImageBitmap(bitmap);

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                paint.setColor(Color.TRANSPARENT);
                imageView.setImageBitmap(bitmap);
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = scrollView.getScrollX();
                int scrollY = scrollView.getScrollY();
//                xcoordinate1.setText(Integer.toString(scrollX));
//                ycoordinate1.setText(Integer.toString(scrollY));
//                Log.d(TAG, "scrollX: "+"scrollY:" + scrollX +scrollY);
//                Toast.makeText(getApplicationContext(),"Current X is :"+scrollX + "\n" +"Current Y is : "+scrollY,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mscalegesture.onTouchEvent(event);
        return true;
    }
}