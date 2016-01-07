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

    public ShareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_share_grey);
        this.buttonWidth = initialBmp.getWidth();
        this.buttonHeight = initialBmp.getHeight();
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight, false);
    }

    public void setNeg(){
        initialBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ui_app_btn_share_grey);
        this.buttonWidth = initialBmp.getWidth();
        this.buttonHeight = initialBmp.getHeight();
        bmp = Bitmap.createScaledBitmap(initialBmp, this.buttonWidth, this.buttonHeight, false);
    }
}
