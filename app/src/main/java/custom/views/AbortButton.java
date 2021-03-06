package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-09-25.
 */
public class AbortButton extends Button{

    public AbortButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_new_btn_abort);
        this.buttonWidth = initialBmp.getWidth();
        this.buttonHeight = initialBmp.getHeight();
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight, false);
    }

}
