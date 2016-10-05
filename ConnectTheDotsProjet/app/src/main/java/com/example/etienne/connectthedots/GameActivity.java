package com.example.etienne.connectthedots;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends Activity {

    Random r = new Random();
    //int points = 0;
    boolean isRunning = false;
    int nbCircles;
    private static RelativeLayout relLayout;
    private List<ImageView> imageViewList;
    private ArrayList<Integer> dimensions;
    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isRunning = true;
        nbCircles = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        dimensions = new ArrayList<>();
        getDisplayDims();
        this.relLayout = createLayout();
        canvasView.createCanvas(relLayout, dimensions);
        canvasView.setOnTouchListenerOnCircles();

        //thread.start();

        /*final String TAG = GameActivity.class.getSimpleName();
        final float[] initialX = new float[1];
        final float[] initialY = new float[1];

        //Pour chaque image view de la liste créée auparavant, faire ça. (Code à modifier un peu)(remplacer imgCircle par
        //un point de la liste? (foreach)
        for (final ImageView circle : imageViewList) {
            circle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    int action = event.getActionMasked();
                    initialX[0] = event.getX();
                    initialY[0] = event.getY();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            circle.setBackgroundColor(Color.GREEN);
                            canvasView.startTouch(initialX[0],initialY[0]);
                            break;

                        case MotionEvent.ACTION_MOVE:
                            canvasView.moveTouch(initialX[0],initialY[0]);
                            Log.d(TAG, "mouvement effectué!");
                            break;

                        case MotionEvent.ACTION_UP:
                            float finalX = event.getX();
                            float finalY = event.getY();

                            canvasView.clearCanvas();
                            circle.setBackgroundColor(Color.TRANSPARENT);

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
        }*/
    }

    public void getDisplayDims() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        dimensions.add(width);
        dimensions.add(height);
    }

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

        canvasView = (CanvasView) findViewById(R.id.signature_canvas);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        relativeLayout.setLayoutParams(rlp);

        if(pointsLabel.getParent()!=null)
            ((ViewGroup)pointsLabel.getParent()).removeView(pointsLabel); // <- fix
        relativeLayout.addView(pointsLabel);
        if(canvasView.getParent()!=null)
            ((ViewGroup)canvasView.getParent()).removeView(canvasView); // <- fix
        relativeLayout.addView(canvasView);

        setContentView(relativeLayout, rlp);

        return relativeLayout;
    }
}
