package fonts;

import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Class used for a predefined textview style which includes required font
 */
public class cairoTextView extends AppCompatTextView {

    /**
     * define context
     *
     * @param context
     */
    public cairoTextView(Context context) {
        super(context);
        init();
    }

    /**
     * Define attributes
     *
     * @param context
     * @param attrs
     */
    public cairoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Method to define attributes and styles
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public cairoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Method to define font style
     */
    private void init() {
        if (!isInEditMode()) {
            //Define font style in Text view
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial Narrow.ttf");
            setTypeface(tf);
        }
    }
}
