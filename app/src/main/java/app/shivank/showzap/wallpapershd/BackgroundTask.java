package app.shivank.showzap.wallpapershd;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BackgroundTask extends AsyncTask<String, ProgressDialog, Bitmap> {

    DatabaseReference databaseReference;
    public static Bitmap bitmap;

    static ProgressDialog progressDialog;

    static Context context;

    Bitmap dstBmp;

    Intent intent;

    static WallpaperManager manager;

    File deleteImageAfterSet;

    URL url;


    public BackgroundTask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {

     /*   progressDialog.setMessage("Working..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

     context.startActivity(new Intent(context, SetWall.class));


    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            url = new URL(strings[0]);
            bitmap = BitmapFactory.decodeStream(url.openStream());
            manager = WallpaperManager.getInstance(context);

            /*switch (ImageShow.sendData) {
                case "Home_Screen":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //manager.suggestDesiredDimensions(450, 500);
                        manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);

                    } else {
                        Toast.makeText(context, "Required Android 7 !", Toast.LENGTH_SHORT).show();
                        manager.setBitmap(bitmap);
                    }
                    break;
                case "Lock_Screen":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                    } else {
                        Toast.makeText(context, "Required Android 7 !", Toast.LENGTH_SHORT).show();
                        manager.setBitmap(bitmap);
                    }
                    break;
                *//*case "Both":
                    Uri ll = Uri.parse(url + "");
                    intent = new Intent(manager.getCropAndSetWallpaperIntent(ll));
                    context.startActivity(intent);
                    break;*//*
            }*/
        } catch (IOException e) {
            Log.i("SET_IMAGE", e.getLocalizedMessage() + "");
        }

        return bitmap;
    }

    private Uri getImageUri(Bitmap inImage, Context inContext) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);

        return Uri.parse(path);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1001);
                //progressDialog.dismiss();
                SetWall.setWallActivity.finish();
                return;

            } else {
                if (ImageShow.sendData == "Set_Wallpaper") {
                    intent = new Intent(manager.getCropAndSetWallpaperIntent(getImageUri(bitmap, context)));
                    context.startActivity(intent);
                    Toast.makeText(context, "Adjust Your Wallpaper", Toast.LENGTH_SHORT).show();
                    SetWall.setWallActivity.finish();
                }

            }
        } else {
            if (ImageShow.sendData == "Set_Wallpaper") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent = new Intent(manager.getCropAndSetWallpaperIntent(getImageUri(bitmap, context)));
                    context.startActivity(intent);
                    Toast.makeText(context, "Adjust Your Wallpaper", Toast.LENGTH_SHORT).show();
                    SetWall.setWallActivity.finish();

                } else {
                    try {
                        manager.setBitmap(bitmap);
                    } catch (IOException e) {
                        Log.d("TAG", e.getLocalizedMessage() + "");
                    }
                }
            }
        }


        SetWall.setWallActivity.finish();
       /* if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }*/

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FancyToast.makeText(context, "Wallpaper Set", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        } else {
            Toast.makeText(context, "Wallpaper Set!", Toast.LENGTH_SHORT).show();
        }*/


    }

}

class Downloadtask extends AsyncTask<String, ProgressDialog, Bitmap> {

    private static double SPACE_KB = 1024;
    private static double SPACE_MB = 1024 * SPACE_KB;
    private static double SPACE_GB = 1024 * SPACE_MB;
    private static double SPACE_TB = 1024 * SPACE_GB;

    Context context;

    Bitmap bitmap;

    ProgressDialog progressDialog;

    DatabaseReference databaseReference;

    File sdcard;

    File directory;

    long lengthbmp;

    URL url;

    public static String bytes2String(long sizeInBytes) {

        NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);

        try {
            if (sizeInBytes < SPACE_KB) {
                return nf.format(sizeInBytes) + " Byte(s)";
            } else if (sizeInBytes < SPACE_MB) {
                return nf.format(sizeInBytes / SPACE_KB) + " KB";
            } else if (sizeInBytes < SPACE_GB) {
                return nf.format(sizeInBytes / SPACE_MB) + " MB";
            } else if (sizeInBytes < SPACE_TB) {
                return nf.format(sizeInBytes / SPACE_GB) + " GB";
            } else {
                return nf.format(sizeInBytes / SPACE_TB) + " TB";
            }
        } catch (Exception e) {
            return sizeInBytes + " Byte(s)";
        }

    }

    public Downloadtask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {

        context.startActivity(new Intent(context, DownloadWall.class));

      /*  progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
   *//*     progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);*//*
        progressDialog.show();
*/
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            url = new URL(strings[0]);
            bitmap = BitmapFactory.decodeStream(url.openStream());


            sdcard = Environment.getExternalStorageDirectory();
            directory = new File(sdcard.getAbsoluteFile() + "/ShowZap");
            if (!directory.exists()) {
                directory.mkdir();
            }

            String fileName = System.currentTimeMillis() + ".jpg";
            File outFile = new File(directory, fileName);

            //For Showing Images in Gallery
            MediaScannerConnection.scanFile(context, new String[]{outFile.getPath()}, new String[]{"image/jpeg"}, null);

            OutputStream outputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            Log.i("DOWNLOAD_TASK", e.getLocalizedMessage());

        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        /*if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }*/

        /*DownloadWall downloadWall = new DownloadWall();
        downloadWall.instance().finish();*/

        DownloadWall.activity.finish();

        //((Activity)context).finish();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FancyToast.makeText(context, "Downloaded!  " + directory, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
        } else {
            Toast.makeText(context, "Downloaded! " + directory, Toast.LENGTH_LONG).show();
        }


    }



}

