package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-10-12.
 */
public class AddButton extends Button{

    public AddButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_btn_add_25);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);

    }

}
