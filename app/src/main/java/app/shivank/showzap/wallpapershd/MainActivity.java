package app.shivank.showzap.wallpapershd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ImageView img11;
    static int DRAWER_VALUES;
    static Toolbar toolbar;
    static NavigationView navigationView;
    static Window window;
    static ActionBarDrawerToggle toggle;
    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img11 = findViewById(R.id.img11);

        if (!checkInternetConnection(this)){
            startActivity(new Intent(this, NoInternet.class));
            finish();
        }

        DRAWER_VALUES = 0; // HomeActivity
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();


        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_abstract:
                DRAWER_VALUES = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_animals:
                DRAWER_VALUES = 2;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_automotive:
                DRAWER_VALUES = 3;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_cities:
                DRAWER_VALUES = 4;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_games:
                DRAWER_VALUES = 5;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_graphics:
                DRAWER_VALUES = 6;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_minimalist:
                DRAWER_VALUES = 7;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_movies:
                DRAWER_VALUES = 8;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_nature:
                DRAWER_VALUES = 9;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_quotes:
                DRAWER_VALUES = 10;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_technology:
                DRAWER_VALUES = 11;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Gridview()).commit();
                break;

            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ShowZap 4k,HD Wallpapers");
                shareIntent.putExtra(Intent.EXTRA_TEXT,  "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                startActivity(Intent.createChooser(shareIntent, "Share Using"));
                break;

            case R.id.nav_rating:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
                } catch (android.content.ActivityNotFoundException activityNotFoundE) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                }
                break;

            case R.id.nav_feedback:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"appmania0011@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Post for ShowZap");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No app can perform this action!", Toast.LENGTH_SHORT).show();
                }
                /*Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "appmania0011@gmail.com", null));
                // For Selection (Email or Gmail)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Post for ShowZap");
                this.startActivity(emailIntent, null);*/
                break;

            case R.id.nav_wall_owner:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Owner of Wallpaper?");
                alert.setMessage(R.string.wall_owner_text);
                alert.setPositiveButton("Contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"appmania0011@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Owner of Wallpaper Query");
                        intent.putExtra(Intent.EXTRA_TEXT, "Your Name - \n\n" +"Website or (Where i can see your Original Art) - \n\n" +"Your Query -");
                        intent.setPackage("com.google.android.gm");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "No app found to perform this action", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .setCancelable(false)
                        .show();
                break;

            case R.id.nav_about:
                startActivity(new Intent(this, About.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean checkInternetConnection(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null){
            networkInfo.isConnected();
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }
}


