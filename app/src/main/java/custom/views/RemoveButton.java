package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Jonathan on 2015-10-25.
 */
public class RemoveButton extends Button {
    public RemoveButton(Context context) {
        super(context);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_remove_33);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }

    public RemoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_remove_33);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }

    public RemoveButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_remove_33);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }
}
