package fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import fv017739.PasswordProtect.R;

/**
 * Class used for a predefined Button style which includes required font
 */
public class cairoButton extends AppCompatButton {
    /**
     * @param context
     */
    public cairoButton(Context context) {
        super(context);
        init();
    }

    /**
     * method to define attribute style and texy
     * @param context
     * @param attrs
     */
    public cairoButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.borderlessButtonStyle);
        init();
    }

    /**
     * Method to define attributes and style
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public cairoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *method used to define button font
     */
    private void init() {
        if (!isInEditMode()) {
            //Define the font style used for buttons
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial Narrow.ttf");
            setTypeface(tf);
        }
    }
}
