package custom.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

import com.example.ursakter.R;

/**
 * Created by Ferm on 2015-04-18.
 */
public class RatingButton extends Button {

    public RatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_0);
        bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
    }

    public void setCurrentRating(int rating){
        switch(rating){
            case 0:
                initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_0);
                bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
                break;
            case 1:
                initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_1);
                bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
                break;
            case 2:
                initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_2);
                bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
                break;
            case 3:
                initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_3);
                bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
                break;
            case 4:
                initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_4);
                bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
                break;
            case 5:
                initialBmp = BitmapFactory.decodeResource(getResources(),R.drawable.ui_app_menu_btn_rate_5);
                bmp = Bitmap.createScaledBitmap(initialBmp,BUTTON_WIDTH,BUTTON_HEIGHT,false);
                break;
        }

    }

}
