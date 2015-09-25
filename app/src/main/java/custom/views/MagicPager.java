package custom.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ferm on 2015-09-17.
 */
public class MagicPager extends ViewPager{

    private boolean backSwipeEnabled = true;
    private float lastX;

    public MagicPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.backSwipeEnabled && super.onInterceptTouchEvent(event);
    }

    public void setBackSwipeEnabled(boolean b) {
        this.backSwipeEnabled = b;
    }


}
