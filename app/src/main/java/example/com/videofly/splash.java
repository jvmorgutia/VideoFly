package example.com.videofly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;




public class splash extends Activity {

    private static int SPLASH_TIME_OUT = 1000;

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "I7zuvwYhjC8TglLLHeLkUPEbW5Ppx2XS67MUCRDm", "MRXLaui8yk4oBJZoTYjCMsB06n5BVk9iZYot10v6");
        ParseFacebookUtils.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                StartSplashScreenAnim();


            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void StartSplashScreenAnim() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.splashLinLay);
        layout.clearAnimation();
        layout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView imageView = (ImageView) findViewById(R.id.logo);
        imageView.clearAnimation();
        imageView.startAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(currentUser != null && (ParseFacebookUtils.isLinked(currentUser))){
                    Intent i = new Intent(splash.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(splash.this, login.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
