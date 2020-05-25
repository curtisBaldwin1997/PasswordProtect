package fv017739.PasswordProtect;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fonts.cairoButton;
import fonts.cairoTextView;

/**
 * Class to update adapter and add password to recycler view
 */
public class PasswordUpdateAdapter extends RecyclerView.Adapter<PasswordUpdateAdapter.ViewHolder> {

    ArrayList<UpdatePassword> updateMessageList; //Arraylist of passwords

    private Context context;
    public String TAG = "PasswordUpdateAdapter"; //TAG message

    /**
     * Method to get arraylist of passwords
     *
     * @param data
     * @param context
     */
    public PasswordUpdateAdapter(ArrayList<UpdatePassword> data, Context context) {
        this.updateMessageList = data; //arraylist equals data
        this.context = context; //Defining the context
    }

    /**
     * Class to handle contents of recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        cairoTextView textViewTitle; //Define title
        cairoTextView textViewUsername; //Define username
        cairoTextView textViewPassword; // Define password
        cairoTextView textViewWebAddress; //Define webaddres
        cairoTextView textViewNote; //Define note
        cairoButton copyPassword; //Define button for copy password
        cairoButton copyUsername; //Define username for copy username
        cairoButton DeleteEntry; //Define entry to delete a password

        /**
         * @param itemView Display contents of password
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.title1); //Find text view ID in XML
            this.textViewUsername = itemView.findViewById(R.id.user1); //Find username ID in XML
            this.textViewPassword = itemView.findViewById(R.id.password1); //Find password ID in XML
            this.textViewWebAddress = itemView.findViewById(R.id.webAddress1); //Find webaddress ID in XML
            this.textViewNote = itemView.findViewById(R.id.Note1); // Find note ID in XML
            this.copyPassword = itemView.findViewById(R.id.copyPass1); // Find password button ID in XML
            this.copyUsername = itemView.findViewById(R.id.copyUser); //Find username button ID in XML
            this.DeleteEntry = itemView.findViewById(R.id.deletePass); //Find delete entry button ID in XML

        }
    }

    /**
     * Method used to inflate password update layout to be displayed
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public PasswordUpdateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_update_item, parent, false); //Inflate layout
        PasswordUpdateAdapter.ViewHolder viewHolder = new PasswordUpdateAdapter.ViewHolder(view); //New password view
        return viewHolder; //Return layout
    }

    /**
     * Method to handle the retrival of data
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final PasswordUpdateAdapter.ViewHolder holder, final int position) {


        holder.textViewTitle.setText(updateMessageList.get(position).getTitle()); //Get title text
        holder.textViewUsername.setText(updateMessageList.get(position).getUsername()); //Get username text
        holder.textViewPassword.setText(updateMessageList.get(position).getPassword()); //Get password text
        holder.textViewWebAddress.setText(updateMessageList.get(position).getWebAddress()); //Get webaddress text
        holder.textViewNote.setText(updateMessageList.get(position).getNote()); //Get note text
        //Method to copy password to clipboard
        holder.copyPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE); //Call clipboard service
                ClipData clip = ClipData.newPlainText("label", (updateMessageList.get(position).getPassword())); //Get password data
                clipboard.setPrimaryClip(clip); //Copy to clip board
                Toast.makeText(v.getContext(), "Password copied to clip board", Toast.LENGTH_SHORT).show(); //Inform user password copied to clip board
            }
        });
        //Method to copy username to clipboard
        holder.copyUsername.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);  //Call clipboard service
                ClipData clip = ClipData.newPlainText("label", (updateMessageList.get(position).getUsername())); //Get username data
                clipboard.setPrimaryClip(clip); //Copy to clip board
                Toast.makeText(v.getContext(), "Username copied to clip board", Toast.LENGTH_SHORT).show(); //Inform user password copied to clip board
            }
        });
        //Method to delete an entry
        holder.DeleteEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateMessageList.remove(position); //Remove selected entry
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, updateMessageList.size()); //notify array of remove
                holder.itemView.setVisibility(View.GONE); //Set item to deleted
            }
        });

    }


    /**
     * @return size of the array list
     */
    @Override
    public int getItemCount() {

        return updateMessageList.size(); //return the size of updateMessageList
    }
}
