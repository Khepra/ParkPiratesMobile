package hi.parkpirates.android;


import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.view.Display;
import android.view.View;

public class GameView extends View {

    Bitmap diamonds_portrait;
    Rect rect;
    int dWidth, dHeight;
    Bitmap rollingMap[] = new Bitmap[6];
    int rollingMapX, rollingMapY, velocity, rollingMapFrame;
    int getRollingMapWidth, getRollingMapHeight;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;

    public GameView(Context context) {
        super(context);
        diamonds_portrait = BitmapFactory.decodeResource(getResources(), R.drawable.dimonds_portrait);
        Display display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0,0,dWidth,dHeight);
        rollingMap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.rolled_map1);
        rollingMap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rolled_map2);
        rollingMap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.rolled_map3);
        rollingMap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.rolled_map4);
        rollingMap[4] = BitmapFactory.decodeResource(getResources(), R.drawable.rolled_map5);
        rollingMap[5] = BitmapFactory.decodeResource(getResources(), R.drawable.rolled_map6);
        rollingMapX = 0;
        rollingMapY = 0;
        velocity = 15;
        rollingMapFrame = 0;


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(diamonds_portrait, null, rect, null);
        canvas.drawBitmap(rollingMap[rollingMapFrame], rollingMapX, rollingMapY, null);
        rollingMapFrame++;
        if(rollingMapFrame > 5) {
            rollingMapFrame = 5;
        }
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }
}
