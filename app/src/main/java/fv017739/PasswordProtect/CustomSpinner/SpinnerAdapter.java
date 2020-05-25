package fv017739.PasswordProtect.CustomSpinner;

import android.content.Context;
//import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import fv017739.PasswordProtect.R;
//import materialuiux.PasswordProtect.R;
/**
 * for more visit http://materialuiux.com
 */

public class SpinnerAdapter extends ArrayAdapter<String> {
    private String[] objects;

    public SpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.objects=objects;
    }
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
        final TextView label=(TextView)row.findViewById(R.id.id_spinner_TextView);
        label.setText(objects[position]);
        return row;
    }

    public void setError(View v, CharSequence s) {
        TextView name = (TextView) v.findViewById(R.id.id_spinner_TextView);
        name.setError(s);
    }



}
