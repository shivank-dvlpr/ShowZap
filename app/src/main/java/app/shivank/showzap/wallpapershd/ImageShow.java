package app.shivank.showzap.wallpapershd;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;
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

    private static int PERMISSION_CODE = 1000;
    private static int PERMISSION_CODE_SET = 2000;

    ArrayAdapter<String> arrayAdapter;

    static CharSequence[] screen;

    static AlertDialog.Builder builder;

    ArrayList<String> fav_images;

    ArrayList<String> stringArrayList = new ArrayList<>();

    static String favData;

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fab_download, fab_set_wallpaper, fab_fav;

    Intent intent;

    int idKey;

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

        Databasehandler databasehandler = new Databasehandler(this);

        checkImageInDB();

        //checkImageInDB();


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
                                PERMISSION_CODE);
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSION_CODE_SET);
                        return;

                    } else {
                        BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                        backgroundTask.execute(bundle);
                        sendData = "Set_Wallpaper";
                    }

                } else {
                    BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                    backgroundTask.execute(bundle);
                    sendData = "Set_Wallpaper";
                }

                break;

            case R.id.fab_fav:
                Databasehandler databasehandler = new Databasehandler(this);

                if (fab_fav.getLabelText() == "Remove from Fav.") {
                    databasehandler.deleteFav(bundle);
                    MainActivity.DRAWER_VALUES = 50;
                    fab_fav.setLabelText("Favourite");
                    fab_fav.setImageResource(R.drawable.not_fav);
                    View snackBar = findViewById(R.id.layout_rel);
                    Snackbar.make(snackBar, "Removed from Favourites.", Snackbar.LENGTH_SHORT).show();
                } else {
                    databasehandler.insertImage(bundle);
                    fab_fav.setLabelText("Remove from Fav.");
                    fab_fav.setImageResource(R.drawable.fav_nav_bottom);
                    View snackBar = findViewById(R.id.layout_rel);
                    Snackbar.make(snackBar, "Added to Favourites!", Snackbar.LENGTH_SHORT).show();
                }

                break;
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.animate_opening_intent, R.anim.animate_exit_intent);
    }

    private void checkImageInDB() {
        Databasehandler databasehandler = new Databasehandler(this);

        Cursor c = databasehandler.allData();
        while (c.moveToNext()) {
            String a = c.getString(1);

            if ((a.contains(bundle))) {
                //Toast.makeText(this, "Already in Fav.", Toast.LENGTH_SHORT).show();
                //fab_fav.setVisibility(View.GONE);
                fab_fav.setLabelText("Remove from Fav.");
                fab_fav.setImageResource(R.drawable.fav_nav_bottom);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Downloadtask downloadtask = new Downloadtask(this);
                downloadtask.execute(bundle);
            } else {
                FancyToast.makeText(this, "Permission Denied", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        } else if (requestCode == PERMISSION_CODE_SET) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BackgroundTask backgroundTask = new BackgroundTask(ImageShow.this);
                backgroundTask.execute(bundle);
                sendData = "Set_Wallpaper";
            } else {
                FancyToast.makeText(this, "Permission Denied", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        }

    }
}



