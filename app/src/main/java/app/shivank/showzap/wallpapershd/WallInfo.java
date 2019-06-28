package app.shivank.showzap.wallpapershd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WallInfo extends AppCompatActivity {

    public static TextView txtArtBy, txtSource, txtArtName, txtHead;
    String txtArtByString, txtArtSourceString, txtArtNameString;
    DatabaseReference databaseReference;
    Intent intent;
    String key;
    String Bundle;
    String Value;
    ImageView wallInfoBG;
    Bitmap bundleImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wall_info);

        getSupportActionBar().hide();

        txtArtBy = findViewById(R.id.txtArtBy);
        txtSource = findViewById(R.id.txtSource);
        txtArtName = findViewById(R.id.txtArtName);
        txtHead = findViewById(R.id.txtHead);
        wallInfoBG = (ImageView) findViewById(R.id.wallInfoBG);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        intent = getIntent();
        key = intent.getStringExtra("Data");
        Bundle = intent.getStringExtra("Bundle");
        Value = intent.getStringExtra("Value");


        Glide.with(this)
                .load(Bundle)
                .override(150,150)
                .into(wallInfoBG);

        if (Bundle != null && Value != null) {
            if (Bundle.equals(Value)) {


                if (ImageShow.WALLINFOVALUE == 1) {
                    retrieveWallInfo("Abstract");
                } else if (ImageShow.WALLINFOVALUE == 2) {
                    retrieveWallInfo("Animals");
                } else if (ImageShow.WALLINFOVALUE == 3) {
                    retrieveWallInfo("Automotive");
                } else if (ImageShow.WALLINFOVALUE == 4) {
                    retrieveWallInfo("Cities");
                } else if (ImageShow.WALLINFOVALUE == 5) {
                    retrieveWallInfo("Games");
                } else if (ImageShow.WALLINFOVALUE == 6) {
                    retrieveWallInfo("Graphics");
                } else if (ImageShow.WALLINFOVALUE == 7) {
                    retrieveWallInfo("Minimalist");
                } else if (ImageShow.WALLINFOVALUE == 8) {
                    retrieveWallInfo("Movies");
                } else if (ImageShow.WALLINFOVALUE == 9) {
                    retrieveWallInfo("Nature");
                } else if (ImageShow.WALLINFOVALUE == 10) {
                    retrieveWallInfo("Quotes");
                } else if (ImageShow.WALLINFOVALUE == 11) {
                    retrieveWallInfo("Technology");
                }


            }
        }


    }

    private void retrieveWallInfo(String categoryName) {
        databaseReference.child("WallInfo").child("Categories").child(categoryName).child(key).child("Art by").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                txtArtByString = dataSnapshot.getValue(String.class);
                Log.d("NAMEE", Bundle + " - " + Value);
                txtArtBy.setText(txtArtByString);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("WallInfo").child("Categories").child(categoryName).child(key).child("Source").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtArtSourceString = dataSnapshot.getValue(String.class);
                Log.d("NAMEE", Bundle + " - " + Value);
                if (!txtArtSourceString.equals("N/A")) {
                    txtSource.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href='" + txtArtSourceString + "'> " + txtArtSourceString + " </a>";
                    txtSource.setText(Html.fromHtml(text));
                } else {
                    txtSource.setLinksClickable(false);
                    txtSource.setText("N/A");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("WallInfo").child("Categories").child(categoryName).child(key).child("Art Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                txtArtNameString = dataSnapshot.getValue(String.class);
                txtArtName.setText(txtArtNameString);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
