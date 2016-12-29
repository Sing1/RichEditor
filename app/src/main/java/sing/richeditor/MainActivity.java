package sing.richeditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sing.imagepicker.ImagePicker;
import sing.imagepicker.bean.ImageItem;
import sing.imagepicker.ui.ImageGridActivity;
import sing.richeditor.activity.SecondActivity;
import sing.richeditor.adapter.MyAdapter;
import sing.richeditor.bean.MyBean;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MyBean> list;
    private MyAdapter adapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDate();
        adapter.setList(list,title);
    }

    private void getDate() {
        list.add(new MyBean("0",""));
    }

    public void image(View v){
        ((MyApplication)getApplication()).getImagePicker().setSelectLimit(9);
        ((MyApplication)getApplication()).getImagePicker().setMultiMode(true);
        startActivityForResult(new Intent(this, ImageGridActivity.class),100);
    }

    public void ok(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("title",adapter.getTitle());
        intent.putExtra("list", (Serializable) list);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
            ArrayList<ImageItem> image = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            String[] infu = adapter.getInfo();
            String str = infu[0];
            int index = Integer.parseInt(infu[1]);
            int position = Integer.parseInt(infu[2]);

            if (index == 0) {// 光标在EditText最前
                list.add(position - 1, new MyBean("0", ""));
                for (int i = 0; i < image.size(); i++) {
                    list.add(position + i * 2, new MyBean("1", image.get(i).path));
                    if (i != image.size() - 1) {
                        list.add(position + (i * 2 + 1), new MyBean("0", ""));
                    }
                }
            }else if (index == str.length()){// 光标在EditText最后
                for (int i = 0; i < image.size(); i++) {
                    list.add(position + i * 2, new MyBean("1", image.get(i).path));
                    list.add(position + (i * 2 + 1), new MyBean("0", ""));
                }
            } else {// 光标在EditText中间
                list.remove(position - 1);
                list.add(position - 1, new MyBean("0", str.substring(0, index)));

                for (int i = 0; i < image.size(); i++) {
                    list.add(position + i * 2, new MyBean("1", image.get(i).path));
                    if (i != image.size() - 1) {
                        list.add(position + (i * 2 + 1), new MyBean("0", ""));
                    }
                }
                list.add(position + image.size() * 2 - 1, new MyBean("0", str.substring(index, str.length())));
            }
            adapter.notifyDataSetChanged();
        }
    }
}