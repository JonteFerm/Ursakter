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
    public BitmapFactory.Options options;
    public int buttonWidth = 120;
    public int buttonHeight = 120;


    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);

        src = new Rect(0,0,buttonWidth, buttonHeight);
        dst = new Rect(0,0,buttonWidth, buttonHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(buttonWidth, buttonWidth);
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
