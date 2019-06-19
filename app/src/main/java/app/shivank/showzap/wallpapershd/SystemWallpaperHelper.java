package app.shivank.showzap.wallpapershd;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class SystemWallpaperHelper {

    public static void setWallpaper(Context context, BitmapDrawable wallpaper) {
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            if(wallpaper != null) {
                Bitmap bmp = wallpaper.getBitmap();
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                assert windowManager != null;
                windowManager.getDefaultDisplay().getMetrics(metrics);
                int height = metrics.heightPixels;
                int width = metrics.widthPixels;
                wallpaperManager.setWallpaperOffsetSteps(1, 1);
                wallpaperManager.suggestDesiredDimensions(width, height);
                Bitmap bitmap = centerCropWallpaper(context, bmp, Math.min(wallpaperManager.getDesiredMinimumWidth(), wallpaperManager.getDesiredMinimumHeight()));
                wallpaperManager.setBitmap(bitmap);
            } else {
                Log.e("TAG", "wallpaper could not be set.");
            }
        } catch (Exception ex) {
            Log.e("TAG", "error setting wallpaper. " + ex.getMessage(), ex);
        }
    }

    public static Bitmap centerCropWallpaper(Context context, Bitmap wallpaper, int desiredHeight){
        float scale = (float) desiredHeight / wallpaper.getHeight();
        int scaledWidth = (int) (scale * wallpaper.getWidth());
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int deviceWidth = metrics.widthPixels;
        int imageCenterWidth = scaledWidth /2;
        int widthToCut = imageCenterWidth - deviceWidth / 2;
        int leftWidth = scaledWidth - widthToCut;
        Bitmap scaledWallpaper = Bitmap.createScaledBitmap(wallpaper, scaledWidth, desiredHeight, false);
        return Bitmap.createBitmap(scaledWallpaper, widthToCut, 0, leftWidth, desiredHeight);
    }
}
