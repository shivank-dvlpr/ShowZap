package app.shivank.showzap.wallpapershd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class About extends AppCompatActivity {

    ImageView about_cover, about_profile;
    TextView version,txtReadPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();

        about_cover = findViewById(R.id.about_cover);
        about_profile = findViewById(R.id.about_profile);
        version = (TextView) findViewById(R.id.txtVersion);
        txtReadPrivacyPolicy = (TextView) findViewById(R.id.txtReadPrivacyPolicy);
        txtReadPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        Glide.with(this)
                .load(R.drawable.about_cover)
                .centerCrop()
                .into(about_cover);

        Glide.with(this)
                .load(R.drawable.logo)
                .apply(RequestOptions.circleCropTransform())
                .into(about_profile);

        version.setText(String.format("%s%s","V",BuildConfig.VERSION_NAME));

    }
}
