package fv017739.PasswordProtect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Class to handle  loading screen to Homescreen and progress bar
 */
public class SplashRegisterScreen extends AppCompatActivity {

    private ProgressBar ProgressBar; //Define progressbar
    private int progressStatus = 0; //define progress bar status
    private Handler handler = new Handler();// define new handle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_register_screen);

       //Find progress bar XML
        ProgressBar = (ProgressBar) findViewById (R.id.progressBarSplash);

    }

    /**
     * Method to perform on resume of app
     */
    @Override
    protected void onStart() {
        super.onStart();
        startloading();  //on start perform start loading function
    }

    /**
     * Method to start progress bar
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
                while (progressStatus < 200) {
                    progressStatus += 4;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {

                            ProgressBar.setProgress(progressStatus);
                            // If the progress bar is equal to 200 launch the HomeScreen class
                            if (progressStatus == 200){
                                Intent i = new Intent(SplashRegisterScreen.this, HomeScreen.class);
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
