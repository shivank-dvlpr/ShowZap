package app.shivank.showzap.wallpapershd;

import android.graphics.Bitmap;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Model extends ArrayList<Integer> {

    public int image;

    public ArrayList<Integer> arrayList;

    public ArrayList<Bitmap> arrayList1;

    Object[] object;

    ArrayList<Object> objectArrayList;

    ArrayList<String> list;

    Bitmap bitmap;

    StorageReference firebaseImages;

    String strings;

    Byte aByte;

    public Model() {
    }

    public Model(int image) {
        this.image = image;
    }

    public Model(ArrayList<String> list) {
        this.list = list;
    }

    public Model(String strings) {
        this.strings = strings;
    }


    public Model(Byte aByte) {
        this.aByte = aByte;
    }

    public Model(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
