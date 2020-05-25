package fv017739.PasswordProtect;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Class to handle the dialog of fingerprint authentication
 */
public class BioDialog extends BottomSheetDialog implements View.OnClickListener {

    private Context context; //declare private variable context

    private Button btnCancel; //declare cancel button
    private ImageView imgLogo; //declare an image view
    private TextView itemTitle, itemDescription, itemSubtitle, itemStatus; //declare TextViews

    private BioCallback biometricCallback; //call biocallback interface

    /**
     * Retrieving the dialog theme
     *
     * @param context
     */
    public BioDialog(@NonNull Context context) {
        super(context, R.style.BottomSheetDialogTheme); //Call the created XML dialog theme
        this.context = context.getApplicationContext(); //declare this page and context
        setDialogView();
    }

    /**
     * calling biocallback interface
     *
     * @param context
     * @param biometricCallback
     */
    public BioDialog(@NonNull Context context, BioCallback biometricCallback) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context.getApplicationContext();
        this.biometricCallback = biometricCallback; //call bio interface
        setDialogView();
    }

    /**
     * @param context
     * @param theme
     */
    public BioDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    /**
     * @param context
     * @param cancelable
     * @param cancelListener
     */
    protected BioDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * set what the dialog view will include
     */
    private void setDialogView() {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.finger_print_sign_in, null); //inflate finger print sign in XML file
        setContentView(bottomSheetView);

        btnCancel = findViewById(R.id.btn_cancel); //Find the cancel button ID in XML file
        btnCancel.setOnClickListener(this); // make it listen to clicks

        imgLogo = findViewById(R.id.img_logo); // Find the logo ID in XML file
        itemTitle = findViewById(R.id.item_title); //fine the title ID in XML file
        itemStatus = findViewById(R.id.item_status); //find the item status ID in XML file
        itemSubtitle = findViewById(R.id.item_subtitle); //find the subtitle ID in XML file
        itemDescription = findViewById(R.id.item_description); //find the item description ID in XML file

        updateLogo();
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        itemTitle.setText(title); //set item title in dialog
    }

    /**
     * @param status
     */
    public void updateStatus(String status) {
        itemStatus.setText(status); //set item status in dialog
    }

    /**
     * @param subtitle
     */
    public void setSubtitle(String subtitle) {
        itemSubtitle.setText(subtitle); //set subtitle in dialog
    }

    /**
     * @param description
     */

    public void setDescription(String description) {
        itemDescription.setText(description); //set item description in dialog
    }


    /**
     * @param negativeButtonText
     */
    public void setButtonText(String negativeButtonText) {
        btnCancel.setText(negativeButtonText); //set button text in dialog
    }

    /**
     * Draw logo on to dialog
     */
    private void updateLogo() {
        try {
            Drawable drawable = getContext().getPackageManager().getApplicationIcon(context.getPackageName());
            imgLogo.setImageDrawable(drawable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * OnClick method for user to cancel authentication if they wish
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        dismiss();
        biometricCallback.onAuthenticationCancelled(); //Fingerprint authentication cancelled
    }

}
