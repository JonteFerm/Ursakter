package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-06-12.
 */
public class PreviousButton extends Button {
    public PreviousButton(Context context) {
        super(context);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.prev);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }

    public PreviousButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.prev);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
    }

    public PreviousButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.prev);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
    }

    public void setNeg(){
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.prev_neg);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
    }

    public void setPos(){
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.prev);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
    }
}
