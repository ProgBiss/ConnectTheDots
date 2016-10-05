package com.example.etienne.connectthedots;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CanvasView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    Random r = new Random();
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 3;
    private List<ImageView> imageViewList;
    private ArrayList<Integer> dimensions;
    private RelativeLayout relLayout;
    private ArrayList<ArrayList<Integer>> circlePos;
    private ArrayList<Integer> circleXY;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(12f);
    }

    public void createCanvas(RelativeLayout relLayout, ArrayList<Integer> dimensions){
        this.relLayout = relLayout;
        this.dimensions = dimensions;
        mBitmap = Bitmap.createBitmap(dimensions.get(0), dimensions.get(1), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        imageViewList = new ArrayList<>();
        circlePos = new ArrayList<ArrayList<Integer>>();
        int i;
        for (i=0; i<2; i++) {
            createCircle();
        }
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);
    }

    // when ACTION_DOWN start touch according to the x,y values
    public void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    public void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    public void upTouch() {
        mPath.lineTo(mX, mY);
    }

    public void setOnTouchListenerOnCircles(){
        for (final ImageView circle : imageViewList)
            circle.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    float x = event.getRawX();
                    float y = event.getRawY();

                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            boolean is_selected = true;
                            startTouch(x, y);
                            Log.d("Pos X: ", String.valueOf(x));
                            Log.d("Pos Y: ", String.valueOf(y));
                            circle.setBackgroundColor(Color.GREEN);
                            invalidate();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            moveTouch(x, y);
                            invalidate();
                            break;
                        case MotionEvent.ACTION_UP:
                            upTouch();
                            clearCanvas();
                            circle.setBackgroundColor(Color.TRANSPARENT);
                            invalidate();
                            break;
                        case MotionEvent.ACTION_HOVER_MOVE:
                            Log.d("IS_ON_POINT: ", "YES");
                    }
                    return true;
                }
            });
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

        ImageView imgView = new ImageView(this.getContext());

        int randomColor = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));

        circleXY = new ArrayList<Integer>();

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
}
