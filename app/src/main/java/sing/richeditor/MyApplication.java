package sing.richeditor;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import sing.imagepicker.ImagePicker;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        getImagePicker();//相当于初始化,必须设置
    }

    ImagePicker imagePicker;
    public ImagePicker getImagePicker() {
        if (imagePicker == null){
            imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new sing.richeditor.imageloader.ImageLoader());
            imagePicker.setMultiMode(false);
            imagePicker.setCrop(false);
            imagePicker.setShowCamera(false);
        }
        return imagePicker;
    }
}