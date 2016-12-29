package sing.richeditor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import sing.richeditor.R;
import sing.richeditor.bean.MyBean;
import sing.richeditor.imageloader.ImageLoader;
import sing.richeditor.util.CommonUtil;

/**
 * @author: LiangYX
 * @ClassName: SecondActivity
 * @date: 2016/12/28 下午4:47
 * @Description: 编辑完成后的展示界面
 */
public class SecondActivity extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        init();
    }

    private void init() {
        int width = CommonUtil.getScreenWidth(this)/4*3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,width);

        inflater = LayoutInflater.from(this);

        String title = getIntent().getExtras().getString("title", "");
        List<MyBean> list = (List<MyBean>) getIntent().getExtras().getSerializable("list");

        ((TextView) findViewById(R.id.tv_title)).setText(title);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        for (MyBean bean : list) {
            if (!TextUtils.isEmpty(bean.txt)) {
                View view = inflater.inflate(R.layout.row_item, null);
                EditText et = (EditText) view.findViewById(R.id.et_txt);
                ImageView iv = (ImageView) view.findViewById(R.id.image);
                if (bean.type.equals("0")) {
                    iv.setVisibility(View.GONE);
                    et.setFocusable(false);
                    et.setText(bean.txt);
                } else {
                    et.setVisibility(View.GONE);
                    iv.setLayoutParams(params);
                    ImageLoader.getInstance(0).ImageLoaders(bean.txt,iv);
                }
                mainLayout.addView(view);
            }
        }
    }
}