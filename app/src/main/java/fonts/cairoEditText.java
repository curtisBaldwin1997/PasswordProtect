package fonts;

import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


/**
 * Class used for a predefined EditText which includes required font
 */
public class cairoEditText extends AppCompatEditText {


    /**
     * Define contect
     *
     * @param context
     */
    public cairoEditText(Context context) {
        super(context);
        init();
    }

    /**
     * Define attribites
     *
     * @param context
     * @param attrs
     */
    public cairoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * method to define attributes and style
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public cairoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Method to define font style
     */
    private void init() {
        if (!isInEditMode()) {
            //Define font style of EditText
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial Narrow.ttf");
            setTypeface(tf);
        }
    }

}
