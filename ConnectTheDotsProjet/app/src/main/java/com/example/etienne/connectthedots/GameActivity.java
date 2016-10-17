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
    private int points = -1;
    boolean isRunning = false;
    int nbCircles;
    private static RelativeLayout relLayout;
    private List<ImageView> imageViewList = new ArrayList<>();
    private ArrayList<Integer> dimensions = new ArrayList<>();
    private List<List<Integer>> circlePos = new ArrayList<>();
    private float circleStartX, circleStartY;
    private boolean ggezwin;
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
        this.resetCanvas();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                this.canvasView.startTouch(x, y);
                Log.d("Pos X: ", String.valueOf(x));
                Log.d("Pos Y: ", String.valueOf(y));
                checkCircles(x, y);
                this.canvasView.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                checkCircles(x, y);
                this.canvasView.moveTouch(x, y);
                this.canvasView.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                this.canvasView.upTouch();
                this.canvasView.clearCanvas();
                if (this.ggezwin)
                    this.resetCanvas();
                else
                    this.resetTouched();
                this.canvasView.invalidate();
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d("IS_ON_POINT: ", "YES");
        }

        return true;
    }

    private void resetCanvas() {
        this.points = this.points + 1;
        this.ggezwin = false;
        this.circlePos.clear();
        this.circleStartX = 0;
        this.circleStartY = 0;

        for (ImageView circle : this.imageViewList) {
            relLayout.removeView(circle);
        }

        for (int i = 0; i < 2; i++) {
            this.createCircle();
        }
    }

    private void resetTouched() {
        for (ImageView circle : this.imageViewList) {
            circle.setBackgroundColor(Color.TRANSPARENT);
        }
        this.circleStartX = 0;
        this.circleStartY = 0;
    }

    private void checkCircles(float x, float y) {
        for (ImageView circle : this.imageViewList) {
            double dist = Math.sqrt(Math.pow(circle.getX() + 62.5d - x, 2.0d) + Math.pow(circle.getY() + 62.5d - y, 2.0d));
            if (dist < 62.5d) {
                if (this.circleStartX == 0 && this.circleStartY == 0) {
                    this.circleStartX = circle.getX();
                    this.circleStartY = circle.getY();
                    circle.setBackgroundColor(Color.GREEN);
                } else if (circle.getX() != this.circleStartX && circle.getY() != this.circleStartY) {
                    circle.setBackgroundColor(Color.GREEN);
                    this.ggezwin = true;
                }
            }
        }
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

        int randomColor = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));

        List<Integer> circleXY = new ArrayList<>();

        int circleX = r.nextInt(dimensions.get(0)-130);
        circleXY.add(circleX);
        int circleY = r.nextInt(dimensions.get(1)-230);
        circleXY.add(circleY);
        circlePos.add(circleXY);
        Log.d("circleXY: ", String.valueOf(circleXY));

        Log.d("circlePos: ", String.valueOf(circlePos));

        Paint paint = new Paint();
        paint.setColor(randomColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Bitmap bmp = Bitmap.createBitmap(125, 125, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(bmp.getWidth()/2, bmp.getHeight()/2, 60, paint);

        Drawable drawable = new BitmapDrawable(getResources(), bmp);

        imgView.setImageDrawable(drawable);

        relLayout.addView(imgView);

        this.imageViewList.add(imgView);

        displayCircle(circleX, circleY, imgView);
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
