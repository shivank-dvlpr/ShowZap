package app.shivank.showzap.wallpapershd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WallInfo extends AppCompatActivity {

    public static TextView txtName, txtArt, txtWebsite, txtHead;
    String txtName1, txtArt1, txtWebsite1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_info);

        txtName = findViewById(R.id.txtName);
        txtArt = findViewById(R.id.txtArt);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtHead = findViewById(R.id.txtHead);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);

        Intent intent = getIntent();
        String key = intent.getStringExtra("Data");
        String Bundle = intent.getStringExtra("Bundle");
        String Value = intent.getStringExtra("Value");

        if (Bundle != null && Value != null){
            if (Bundle.equals(Value)) {
                databaseReference.child("WallInfo").child("Categories").child("Abstract").child(key).child("Name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        txtName1 = dataSnapshot.getValue(String.class);
                        Log.d("NAMEE", Bundle + " - " + Value);
                        txtName.setText(txtName1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }



        txtWebsite.setText(ImageShow.website);
        txtArt.setText("12");
        //txtHead.setText(ImageShow.a);

    }
}
