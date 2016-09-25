package com.example.etienne.connectthedots;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.logging.Handler;

public class GameActivity extends AppCompatActivity {

    Random r = new Random();
    //int points = 0;
    boolean isRunning = false;
    int nbCircles;
    private RelativeLayout relLayout;
    private View imgCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isRunning = true;
        nbCircles = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.relLayout = createLayout();
        createCircle();
        createCircle();

        //thread.start();

        final String TAG = GameActivity.class.getSimpleName();
        final float[] initialX = new float[1];
        final float[] initialY = new float[1];


        //Pour chaque image view de la liste créée auparavant, faire ça. (Code à modifier un peu)(remplacer imgCircle par
        //un point de la liste? (foreach)
        this.imgCircle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getActionMasked();
                boolean isSelected = false;

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        initialX[0] = event.getX();
                        initialY[0] = event.getY();

                        isSelected = true;
                        imgCircle.setBackgroundColor(Color.RED);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                        float finalX = event.getX();
                        float finalY = event.getY();

                        imgCircle.setBackgroundColor(Color.TRANSPARENT);

                        if (initialX[0] < finalX) {
                            Log.d(TAG, "Left to Right swipe performed");
                        }

                        if (initialX[0] > finalX) {
                            Log.d(TAG, "Right to Left swipe performed");
                        }

                        if (initialY[0] < finalY) {
                            Log.d(TAG, "Up to Down swipe performed");
                        }

                        if (initialY[0] > finalY) {
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
    }

    /*
    android.os.Handler mHandler = new android.os.Handler();
    Thread thread = new Thread() {
            @Override
            public void run() {
                final RelativeLayout gameLayout = (RelativeLayout)findViewById(R.id.gameLayout);

                while(nbCircles <= 2){
                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayCircle(r.nextInt(gameLayout.getWidth()-100), r.nextInt(gameLayout.getHeight()-100), imgCircle); //Fait apparaitre des cercles
                                nbCircles = nbCircles + 1;
                            }
                        });
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };*/


    private int layoutWidth;
    private int layoutHeight;
    /**
     * Creates a RelativeLayout where the dots are going to appear.
     */
    private RelativeLayout createLayout() {

        final RelativeLayout relativeLayout = new RelativeLayout(this);

        TextView pointsLabel = new TextView(this);
        pointsLabel.setText("Points:");
        pointsLabel.setTextSize(20);

        RelativeLayout.LayoutParams pLParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        pLParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        pLParams.setMargins(10,10,0,0);

        pointsLabel.setLayoutParams(pLParams);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        relativeLayout.addView(pointsLabel);

        setContentView(relativeLayout, rlp);

        /**
         * This code is to get the width and height of the RelativeLayout.
         */
        /*ViewTreeObserver vto = relativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width  = relativeLayout.getMeasuredWidth();
                int height = relativeLayout.getMeasuredHeight();
            }
        });*/

        return relativeLayout;
    }

    /**
     * Displays the ImageView according to the X and Y positions given in arguments.
     **/
    private void displayCircle(int posX, int posY, ImageView imgView) {
        imgView.setX(posX);
        imgView.setY(posY);
    }

    /**
     * Creates an ImageView adding his Bitmap and a Canvas for it and adds it to the relative layout
     * passed in the argument.
     * **/
    private void createCircle(){

        ImageView imgView = new ImageView(this);
        this.imgCircle = imgView;

        int randomColor = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));

        Paint paint = new Paint();
        paint.setColor(randomColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Bitmap bmp = Bitmap.createBitmap(125, 125, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(bmp.getWidth()/2, bmp.getHeight()/2, 60, paint);

        Drawable drawable = new BitmapDrawable(getResources(), bmp);

        imgView.setImageDrawable(drawable);

        relLayout.addView(imgView);

        //Ajouter l'image view dans une liste disponible pour toute la classe.

        displayCircle(r.nextInt(500), r.nextInt(500), imgView);
    }

    //createCircle(r.nextInt(500), r.nextInt(500));
    //points = points + 1;
    //nbPtsLabel.setText(String.valueOf(points));
    //TextView nbPtsLabel = (TextView)findViewById(R.id.nbPts);
}
