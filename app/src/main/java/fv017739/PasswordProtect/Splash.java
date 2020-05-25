package fv017739.PasswordProtect;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class to handle intial loading screen and progress bar
 */
public class Splash extends AppCompatActivity {

    private ProgressBar _ProgressBar; //Define progressbar
    ImageView Image; //define image view
    private int progressStatus = 0; //define progress bar status
    private Handler handler = new Handler(); // define new handle
    Animation aniFade; //define animation

    /**
     * Intiate view and layout
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //find progress bar and image views in XML
        _ProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Image = (ImageView) findViewById(R.id.id_ImageView);

        //load animation amd start it
        aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Image.startAnimation(aniFade);
    }

    /**
     * Method to perform on start of app
     */
    @Override
    protected void onStart() {
        super.onStart();
        startloading(); //on start perform start loading function
    }

    /**
     * Method to perform on resume of app
     */
    @Override
    protected void onResume() {
        super.onResume();
        startloading(); // on resume perform start loading function

    }

    /**
     * Method to start progress bar
     */
    private void startloading() {
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 4;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {

                            _ProgressBar.setProgress(progressStatus);
                            // If the progress bar is equal to 100 launch the MainSignActivity class
                            if (progressStatus == 100) {
                                Intent i = new Intent(Splash.this, MainSignActivity.class);
                                startActivity(i);

                            }
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

}
