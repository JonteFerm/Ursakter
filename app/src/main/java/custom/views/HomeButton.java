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
 * Created by Ferm on 2015-04-18.
 */
public class HomeButton extends Button {

    public HomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_btn_home);
        this.buttonWidth = initialBmp.getWidth();
        this.buttonHeight = initialBmp.getHeight();
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight,false);
    }

}
