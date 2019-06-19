package app.shivank.showzap.wallpapershd;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class ImageShow extends AppCompatActivity implements View.OnClickListener {

    static ImageView img11;

    public static int SET;

    LinearLayout homeScreen;

    static String sendData;

    String bundle;

    static GridAdapter gridAdapter;

    Boolean recall;

    DisplayMetrics displayMetrics;

    ArrayList<String> aa;

    int width;
    int height;


    int counter;

    ArrayAdapter<String> arrayAdapter;

    static CharSequence[] screen;

    static AlertDialog.Builder builder;

    ArrayList<String> fav_images;

    ArrayList<String> stringArrayList = new ArrayList<>();

    static String favData;

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fab_download, fab_set_wallpaper, fab_fav;

    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.overridePendingTransition(R.anim.animate_opening_intent, R.anim.animate_exit_intent);

     /*   getWindow().setAllowEnterTransitionOverlap(false);
        Slide slide = new Slide(Gravity.RIGHT);
        getWindow().setReturnTransition(slide);*/

        setContentView(R.layout.image_show);

        fav_images = new ArrayList<>();

        getSupportActionBar().hide();

        img11 = findViewById(R.id.img11);

        stringArrayList = new ArrayList<>();
        aa = new ArrayList<>();

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_action_menu);
        fab_download = findViewById(R.id.fab_download);
        fab_set_wallpaper = findViewById(R.id.fab_set_wallpaper);
        fab_fav = (FloatingActionButton) findViewById(R.id.fab_fav);
        fab_download.setOnClickListener(this);
        fab_set_wallpaper.setOnClickListener(this);
        fab_fav.setOnClickListener(this);

        intent = getIntent();

        Model model = new Model();

        try {
            bundle = intent.getExtras().getString("KEY");
        } catch (NullPointerException e) {
            Log.d("ImageShowNull", e.getLocalizedMessage() + "");
        }

        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(ImageShow.this);
        progressDrawable.setStrokeWidth(5f);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        progressDrawable.start();

        Glide.with(ImageShow.this)
                .load(bundle)
                .placeholder(progressDrawable)
                .into(img11);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fab_download:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1000);
                        return;

                    } else {
                        Downloadtask downloadtask = new Downloadtask(this);
                        downloadtask.execute(bundle);
                    }

                } else {
                    Downloadtask downloadtask = new Downloadtask(this);
                    downloadtask.execute(bundle);
                }

                break;


            case R.id.fab_set_wallpaper:

             /*   String[] items = {"Home Screen", "Lock Screen", "Both"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(ImageShow.this);
                builder.setTitle("Set Wallpaper");
                builder.setIcon(R.drawable.home_lock);// TODO: Set your app icon here
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                            backgroundTask.execute(bundle);
                            sendData = "Home_Screen";
                        } else if (which == 1) {
                            BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                            backgroundTask.execute(bundle);
                            sendData = "Lock_Screen";
                        } else if (which == 2) {
                            BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                            backgroundTask.execute(bundle);
                            sendData = "Both";
                        }
                    }
                }).create().show();*/

                BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                backgroundTask.execute(bundle);
                sendData = "Set_Wallpaper";
                break;

            case R.id.fab_fav:
                Databasehandler databasehandler = new Databasehandler(this);
                //databasehandler.insertImage(bundle);
                databasehandler.insertImage(bundle);
              /*  if (databasehandler.insertImage(bundle)){
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                    //Log.d("ImageBunde",)
                }else {
                    Toast.makeText(this, "Not Successful", Toast.LENGTH_SHORT).show();
                }*/
                //FancyToast.makeText(ImageShow.this, "In Development!", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                //Create Loop
                //sendData = "fav";

                break;
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.animate_opening_intent, R.anim.animate_exit_intent);
    }

}



