package fv017739.PasswordProtect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fv017739.PasswordProtect.R;

/**
 * Class to handle homefragment features
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private ImageView Facebook; //Declare image view
    private ImageView Twitter; //Declare image view
    private ImageView Reddit; //Declare image view
    private ImageView Youtube; //Declare image view
    private ImageView Gmail; //Declare image view
    private ImageView Chrome; //Declare image view
    private ImageView Ebay; //Declare image view
    private ImageView Amazon; //Declare image view

    /**
     * On create method for fragment used to inflate layout and call methods for functionality
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        ((HomeScreen) getActivity()).setTitle("Home"); //Set title

        //Find Facebook ID in XML and listen to user clicks
        Facebook = view.findViewById(R.id.imageViewFacebook);
        Facebook.setOnClickListener(this);

        //Find Twitter ID in XML and listen to user clicks
        Twitter = view.findViewById(R.id.imageViewTwitter);
        Twitter.setOnClickListener(this);

        //Find Reddit ID in XML and listen to user clicks
        Reddit = view.findViewById(R.id.imageViewReddit);
        Reddit.setOnClickListener(this);

        //Find Youtube ID in XML and listen to user clicks
        Youtube = view.findViewById(R.id.imageViewYoutube);
        Youtube.setOnClickListener(this);

        //Find Gmail ID in XML and listen to user clicks
        Gmail = view.findViewById(R.id.imageViewGmail);
        Gmail.setOnClickListener(this);

        //Find Chrome ID in XML and listen to user clicks
        Chrome = view.findViewById(R.id.imageViewChrome);
        Chrome.setOnClickListener(this);

        //Find Ebay ID in XML and listen to user clicks
        Ebay = view.findViewById(R.id.imageViewEbay);
        Ebay.setOnClickListener(this);

        //Find Amazon ID in XML and listen to user clicks
        Amazon = view.findViewById(R.id.imageViewAmazon);
        Amazon.setOnClickListener(this);


        return view; //Return the above view
    }


    @Override

    /**
     * Method to handle all on Click for each image view
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewFacebook: //Get ID view Linked XML
                Intent facebook = new Intent(); //name the method to take user to relevant website
                facebook.setAction(Intent.ACTION_VIEW);
                facebook.addCategory(Intent.CATEGORY_BROWSABLE);
                facebook.setData(Uri.parse("https://www.facebook.com/")); //Take user to website Link
                startActivity(facebook);
                break;

            case R.id.imageViewTwitter: //Get ID view Linked XML
                Intent twitter = new Intent(); //name the method to take user to relevant website
                twitter.setAction(Intent.ACTION_VIEW);
                twitter.addCategory(Intent.CATEGORY_BROWSABLE);
                twitter.setData(Uri.parse("https://twitter.com/login?lang=en")); //Take user to website Link
                startActivity(twitter);
                break;

            case R.id.imageViewReddit: //Get ID view Linked XML
                Intent reddit = new Intent(); //name the method to take user to relevant website
                reddit.setAction(Intent.ACTION_VIEW);
                reddit.addCategory(Intent.CATEGORY_BROWSABLE);
                reddit.setData(Uri.parse("https://www.reddit.com/")); //Take user to website Link
                startActivity(reddit);
                break;

            case R.id.imageViewYoutube: //Get ID view Linked XML
                Intent youtube = new Intent(); //name the method to take user to relevant website
                youtube.setAction(Intent.ACTION_VIEW);
                youtube.addCategory(Intent.CATEGORY_BROWSABLE);
                youtube.setData(Uri.parse("https://www.youtube.com/")); //Take user to website Link
                startActivity(youtube);
                break;

            case R.id.imageViewGmail: //Get ID view Linked XML
                Intent gmail = new Intent();//name the method to take user to relevant website
                gmail.setAction(Intent.ACTION_VIEW);
                gmail.addCategory(Intent.CATEGORY_BROWSABLE);
                gmail.setData(Uri.parse("https://accounts.google.com/signin/v2/identifier?flowName=GlifWebSignIn&flowEntry=ServiceLogin")); //Take user to website Link
                startActivity(gmail);
                break;

            case R.id.imageViewChrome: //Get ID view Linked XML
                Intent chrome = new Intent(); //name the method to take user to relevant website
                chrome.setAction(Intent.ACTION_VIEW);
                chrome.addCategory(Intent.CATEGORY_BROWSABLE);
                chrome.setData(Uri.parse("https://www.google.co.uk/")); //Take user to website Link
                startActivity(chrome);
                break;

            case R.id.imageViewEbay: //Get ID view Linked XML
                Intent ebay = new Intent();//name the method to take user to relevant website
                ebay.setAction(Intent.ACTION_VIEW);
                ebay.addCategory(Intent.CATEGORY_BROWSABLE);
                ebay.setData(Uri.parse("https://www.ebay.co.uk/")); //Take user to website Link
                startActivity(ebay);
                break;

            case R.id.imageViewAmazon: //Get ID view Linked XML
                Intent amazon = new Intent();//name the method to take user to relevant website
                amazon.setAction(Intent.ACTION_VIEW);
                amazon.addCategory(Intent.CATEGORY_BROWSABLE);
                amazon.setData(Uri.parse("https://www.amazon.co.uk")); //Take user to website Link
                startActivity(amazon);

                break;

        }

    }
}
