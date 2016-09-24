package com.example.etienne.connectthedots;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    Random r = new Random();
    //int points = 0;
    public ImageView imgCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imgCircle = (ImageView)findViewById(R.id.imgCircle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.imgCircle= (ImageView)this.findViewById(R.id.imgCircle);
        /* Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createCircle(r.nextInt(650), r.nextInt(650));
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.start(); */

        this.imgCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        initialY = event.getY();

                        imgCircle.setBackgroundColor(Color.RED);
                        createCircle(r.nextInt(650), r.nextInt(650)); //Fait apparaitre des cercles quand on en touche 1
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                        float finalX = event.getX();
                        float finalY = event.getY();

                        imgCircle.setBackgroundColor(Color.TRANSPARENT);

                        if (initialX < finalX) {
                            Log.d(TAG, "Left to Right swipe performed");
                        }

                        if (initialX > finalX) {
                            Log.d(TAG, "Right to Left swipe performed");
                        }

                        if (initialY < finalY) {
                            Log.d(TAG, "Up to Down swipe performed");
                        }

                        if (initialY > finalY) {
                            Log.d(TAG, "Down to Up swipe performed");
                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:
                        Log.d(TAG,"Action was CANCEL");
                        break;

                    case MotionEvent.ACTION_OUTSIDE:
                        Log.d(TAG, "Movement occurred outside bounds of current screen element");
                        break;
                }
                return true;
            }
        });
        createCircle(r.nextInt(650), r.nextInt(650));
    }

    public void createCircle(int posX, int posY) {

        int randomColor = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));

        imgCircle = (ImageView)findViewById(R.id.imgCircle);
        Paint paint = new Paint();
        paint.setColor(randomColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Bitmap bmp = Bitmap.createBitmap(125, 125, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(bmp.getWidth()/2, bmp.getHeight()/2, 60, paint);

        imgCircle.setImageBitmap(bmp);
        imgCircle.setX(posX);
        imgCircle.setY(posY);
    }

    private String TAG = GameActivity.class.getSimpleName();
    float initialX, initialY;

    //createCircle(r.nextInt(500), r.nextInt(500));
    //points = points + 1;
    //nbPtsLabel.setText(String.valueOf(points));
    //TextView nbPtsLabel = (TextView)findViewById(R.id.nbPts);
}
