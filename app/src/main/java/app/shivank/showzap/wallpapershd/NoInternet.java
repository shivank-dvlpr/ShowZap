package app.shivank.showzap.wallpapershd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancytoastlib.FancyToast;

public class NoInternet extends AppCompatActivity {

    LottieAnimationView network;
    LottieAnimationView internet;

    int count = 1;
    int doubleCount = 1;

    boolean active = false;

    RelativeLayout no_internet_rel;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.no_internet_activity);
        getSupportActionBar().hide();

        network = findViewById(R.id.network);
        internet = findViewById(R.id.internet);

        no_internet_rel = findViewById(R.id.no_internet_rel);

        Snackbar.make(no_internet_rel, "No Internet!", Snackbar.LENGTH_SHORT).show();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 10000);

                if (MainActivity.checkInternetConnection(NoInternet.this)) {
                    handler.removeCallbacks(this);
                    if (!MainActivity.active && active) {
                        FancyToast.makeText(NoInternet.this, "Internet Connected", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        startActivity(new Intent(NoInternet.this, MainActivity.class));
                        finish();
                    }
                }
            }
        }, 10000);


        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count == 1) {
                    count = 2;
                    network.setAnimation("dino.json");
                    network.playAnimation();
                    network.loop(true);
                } else if (count == 2) {
                    count = 3;
                    network.setAnimation("ufo.json");
                    network.playAnimation();
                    network.loop(true);
                } else if (count == 3) {
                    count = 4;
                    network.setAnimation("color.json");
                    network.playAnimation();
                    network.loop(true);
                } else if (count == 4) {
                    count = 5;
                    network.setAnimation("laod.json");
                    network.playAnimation();
                    network.loop(true);
                } else if (count == 5) {
                    count = 1;
                    network.setAnimation("no_network.json");
                    network.playAnimation();
                    network.loop(true);
                }


            }
        });

        network.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                startActivity(new Intent(NoInternet.this, About.class));

                return true;
            }
        });

        internet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (doubleCount == 1) {
                    doubleCount = 2;
                    internet.setAnimation("neon.json");
                    internet.playAnimation();
                    internet.loop(true);
                } else if (doubleCount == 2) {
                    doubleCount = 1;
                    internet.setAnimation("no_internet.json");
                    internet.playAnimation();
                    internet.loop(true);
                }

                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.checkInternetConnection(NoInternet.this)) {
            Snackbar.make(no_internet_rel, "Internet Connected Restart your App.", Snackbar.LENGTH_LONG).show();
            active = false;
        }
    }
}
