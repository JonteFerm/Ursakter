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

    public PreviousButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_back);
        this.buttonWidth = initialBmp.getWidth();
        this.buttonHeight = initialBmp.getHeight();
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight, false);
    }

    public void setNeg(){
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_btn_back_neg);
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight, false);
    }

    public void setPos(){
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_btn_back);
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight, false);
    }
}
