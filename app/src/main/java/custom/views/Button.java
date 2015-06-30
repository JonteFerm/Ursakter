package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-06-05.
 */
public abstract class Button extends View {
    Rect src;
    Rect dst;
    public Bitmap initialBmp;
    public Bitmap bmp;
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 150;

    public Button(Context context) {
        super(context);

        src = new Rect(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
        dst = new Rect(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);

        src = new Rect(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
        dst = new Rect(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
    }

    public Button(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        src = new Rect(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);
        dst = new Rect(0,0,BUTTON_WIDTH,BUTTON_HEIGHT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try{
            canvas.drawBitmap(bmp,src,dst,null);
        }catch(Exception e){
            throw e;
        }

    }

}
