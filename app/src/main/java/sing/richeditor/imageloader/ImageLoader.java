package sing.richeditor.imageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import java.io.File;

public class ImageLoader implements sing.imagepicker.loader.ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageSize size = new ImageSize(width, height);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(Uri.fromFile(new File(path)).toString(), imageView, size);
    }

    @Override
    public void clearMemoryCache() {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().clearMemoryCache();//清除内存
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().clearDiskCache();//清除sd卡
    }

    private static LoaderImage instance = null;
    private static int image;
    public static LoaderImage getInstance(int images) {
        image = images;
        if (null == instance) {
            instance = new LoaderImage();
        }
        return instance;
    }


    public void ImageLoaders(String url, ImageView iv) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(image)
                .showImageForEmptyUri(image)
                .showImageOnFail(image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(url, iv, options );
    }
}