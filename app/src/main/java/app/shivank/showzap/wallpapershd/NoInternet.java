package app.shivank.showzap.wallpapershd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class NoInternet extends AppCompatActivity {

    LottieAnimationView network;
    LottieAnimationView internet;

    int count = 1;
    int doubleCount = 1;

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

        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (count == 1){
                    count = 2;
                    network.setAnimation("dino.json");
                    network.playAnimation();
                    network.loop(true);
                }else if (count == 2){
                    count = 3;
                    network.setAnimation("time.json");
                    network.playAnimation();
                    network.loop(true);
                }else if (count == 3){
                    count = 4;
                    network.setAnimation("ufo.json");
                    network.playAnimation();
                    network.loop(true);
                }else if(count == 4){
                    count = 5;
                    network.setAnimation("color.json");
                    network.playAnimation();
                    network.loop(true);

                } else if (count == 5){
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
                if (doubleCount == 1){
                    doubleCount = 2;
                    internet.setAnimation("neon.json");
                    internet.playAnimation();
                    internet.loop(true);
                }else if (doubleCount == 2){
                    doubleCount = 1;
                    internet.setAnimation("no_internet.json");
                    internet.setColorFilter(Color.parseColor("#9A000000"));
                    internet.playAnimation();
                    internet.loop(true);
                }

                return true;
            }
        });

    }

}
