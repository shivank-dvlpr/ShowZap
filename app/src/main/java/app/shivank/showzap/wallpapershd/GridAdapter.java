package app.shivank.showzap.wallpapershd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.LottieTask;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private int[] icons;
    static ImageView icon;
    static ImageView favImage;
    private Context context;
    private LayoutInflater inflater;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    static ArrayList<String> arrayList;
    Bitmap bitmap;
    Uri aaaa;
    static String[] strings;
    Object object[];
    DataSnapshot[] dataSnapshot;
    List<Object> list;
    StorageReference firebaseImages;
    StorageReference[] aa;
    URL url;
    static String get_fav_images;
    static int position;
    LottieAnimationView lottieAnimationView;
    LottieCompositionFactory lottieCompositionFactory;

    public GridAdapter(Context context, ArrayList<String> arrayList) {

        this.arrayList = new ArrayList<>();

        this.context = context;
        this.arrayList = arrayList;

    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        this.position = position;


        View gridView = convertView;

        if (convertView == null) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.custom_layout, null, false);

            LayoutInflater a = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = a.inflate(R.layout.fav_fragment, null);
            favImage = (ImageView) v.findViewById(R.id.fav_image);


        }
        icon = (ImageView) gridView.findViewById(R.id.imageView);
      /*  lottieAnimationView = new LottieAnimationView(context);
        //lottieAnimationView.setImageAssetsFolder("dino.json");
        lottieAnimationView.setAnimation("ufo.json");
        lottieAnimationView.setRepeatCount(10);
        lottieAnimationView.enableMergePathsForKitKatAndAbove(true);
        lottieAnimationView.playAnimation();*/

        final LottieDrawable lottieDrawable = new LottieDrawable();
        lottieDrawable.setRepeatCount(10);
        lottieDrawable.enableMergePathsForKitKatAndAbove(true);

        LottieComposition.Factory.fromAssetFileName(context, "fast.json", composition -> lottieDrawable.setComposition(composition));
        lottieDrawable.playAnimation();

        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(5f);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.setColorFilter(Color.parseColor("#FF0031"), PorterDuff.Mode.SRC_ATOP);
        progressDrawable.start();

        /*Glide.with(context)
                .load(arrayList.get(position))
                .preload(500, 500);*/


        Glide.with(context)
                .load(arrayList.get(position))
                .transition(GenericTransitionOptions.with(android.R.anim.slide_in_left))
                .placeholder(lottieDrawable)
                .error(progressDrawable)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(icon);

        return gridView;
    }

}
