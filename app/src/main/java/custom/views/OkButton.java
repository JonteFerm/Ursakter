package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-09-25.
 */
public class OkButton extends Button{

    public OkButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_new_btn_ok);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
    }
}
