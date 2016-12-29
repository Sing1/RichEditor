package sing.richeditor.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class LoaderImage {
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
		if (url.startsWith("/")){
			url = "file://" + url;
		}
		DisplayImageOptions	options = new DisplayImageOptions.Builder()
				.showImageOnLoading(image)
				.showImageForEmptyUri(image)
				.showImageOnFail(image)
				.cacheInMemory(true)
 				.cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoader.getInstance().displayImage(url, iv, options );
	}
}
