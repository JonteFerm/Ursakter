package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-06-12.
 */
public class ShareButton extends Button {

    public ShareButton(Context context) {
        super(context);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_share_grey);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }

    public ShareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_share_grey);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }

    public ShareButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_share_grey);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }

    public void setNeg(){
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_share_grey);
        bmp = Bitmap.createScaledBitmap(initialBmp, BUTTON_WIDTH, BUTTON_HEIGHT, false);
    }
}
