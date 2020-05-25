package fv017739.PasswordProtect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fonts.cairoTextView;

/**
 * Class to handle add password via use of adapter
 */
public class PasswordAdapter extends FirestoreRecyclerAdapter<UpdatePassword, PasswordAdapter.PasswordHolder> {

    /**
     * Define password adapter
     *
     * @param options
     */
    public PasswordAdapter(@NonNull FirestoreRecyclerOptions<UpdatePassword> options) {
        super(options); //Define options
    }

    /**
     * Bind password entrys to view holder
     *
     * @param holder
     * @param position
     * @param model
     */
    @Override
    protected void onBindViewHolder(@NonNull PasswordHolder holder, int position, @NonNull UpdatePassword model) {
        holder.textViewTitle.setText(model.getTitle()); //get text of title
        holder.textViewUsername.setText(model.getUsername()); //get text of username
        holder.textViewPassword.setText(model.getPassword()); //get text of password
        holder.textViewWebAddress.setText(model.getWebAddress()); //get text of webaddress
        holder.textViewNote.setText(model.getNote()); //get text of note
    }

    /**
     * Inflate layout reltated to class which is password update itme
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PasswordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_update_item,
                parent, false); //inflate layout
        return new PasswordHolder(v); //Return new password holder
    }

    /**
     * Method to extend password holder into recyclerView
     */
    class PasswordHolder extends RecyclerView.ViewHolder {
        cairoTextView textViewTitle; //define text view title
        cairoTextView textViewUsername; //define text view username
        cairoTextView textViewPassword; //define text view password
        cairoTextView textViewWebAddress; //define text view webaddress
        cairoTextView textViewNote; //define text view note

        /**
         * Method to find IDs of text views
         *
         * @param itemView
         */
        public PasswordHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title1); //Find title ID in XML file
            textViewUsername = itemView.findViewById(R.id.user1); //Find username ID in XML file
            textViewPassword = itemView.findViewById(R.id.password1); //Find password ID in XML file
            textViewWebAddress = itemView.findViewById(R.id.webAddress1); //Find webaddress ID in XML file
            textViewNote = itemView.findViewById(R.id.Note1); //Find note ID in XML file
        }

    }
}
