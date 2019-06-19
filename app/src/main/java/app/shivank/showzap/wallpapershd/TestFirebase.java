package app.shivank.showzap.wallpapershd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class TestFirebase extends AppCompatActivity {

    FirebaseStorage firebaseStorage;

    StorageReference storageReference;

    StorageReference ref;

    DatabaseReference databaseReference;

    ImageView imageview;
    static URL url;

    static String[] strings;
    static int a;

    ImageView icon;

    static List<URL> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);

        imageview = findViewById(R.id.img_firebase);

        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        ref = storageReference.child("Nature/y.jpg");

        //icon = (ImageView) findViewById(R.id.imageView);

        BackgroundTask backgroundTask = new BackgroundTask(TestFirebase.this);
        Glide.with(TestFirebase.this)
                .load(backgroundTask)
                .into(imageview);

        //Toast.makeText(this, backgroundTask + "", Toast.LENGTH_SHORT).show();

        // Toast.makeText(this, ref + "", Toast.LENGTH_SHORT).show();

        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Nature").child("Awesome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();

                while (items.hasNext()){
                    DataSnapshot item = items.next();

                     /*strings = new String[]{item.child("A").getValue().toString(), item.child("B").getValue().toString()
                             , item.child("C").getValue().toString(), item.child("D").getValue().toString()};
                     strings.notifyAll();
*/

                }




            /*    for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        try {
                            url = new URL((String) post.getValue());

                            strings = String.valueOf((url));

                            Log.i("LOOPS", url + "");
                            Toast.makeText(getApplicationContext(), url + "\n", Toast.LENGTH_SHORT).show();

                        } catch (MalformedURLException e) {
                            Log.d("MALFORMED", e.getLocalizedMessage());
                        }
                    }
                    break;
                }*/
                //databaseReference.child("Nature").child("Awesome").child("F").setValue(strings);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
