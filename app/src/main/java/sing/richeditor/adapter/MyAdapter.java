package sing.richeditor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import sing.richeditor.R;
import sing.richeditor.bean.MyBean;
import sing.richeditor.imageloader.LoaderImage;
import sing.richeditor.util.CommonUtil;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private EditText edittext;
    private int positions;

    private Context context;
    public MyAdapter(Context context) {
        this.context = context;
    }

    private List<MyBean> list;
    private String title;
    public void setList(List<MyBean> list, String title) {
        this.list = list;
        this.title = title;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (viewType == 0) {
            layout = R.layout.row_title;
        } else {
            layout = R.layout.row_item;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 1;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position == 0) {
            holder.etTitle.setText(title);
            holder.etTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    title = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        } else {
            if (list.get(position - 1).type.equals("0")) {
                holder.image.setVisibility(View.GONE);
                holder.etTxt.setVisibility(View.VISIBLE);
                if (list != null && list.size() == 1) {
                    holder.etTxt.setHint("请输入");
                } else {
                    holder.etTxt.setHint("");
                }
                holder.etTxt.setText(list.get(position - 1).txt);

                // EditText的一些恶心事的处理
                holder.etTxt.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction()== MotionEvent.ACTION_UP){
                            index=position-1;
                        }
                        return false;
                    }
                });

                holder.etTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        EditText et=(EditText) view;
                        if(mWatcher==null){
                            mWatcher=new MyWatcher();
                        }
                        if(hasFocus){
                            Log.e("position", position + "");
                            edittext = holder.etTxt;
                            positions = position;
                            et.addTextChangedListener(mWatcher);
                        }else {
                            et.removeTextChangedListener(mWatcher);
                        }
                    }
                });
                holder.etTxt.clearFocus();
                if (index != -1 && index == position-1) {
                    holder.etTxt.requestFocus();
                }
                holder.etTxt.setText(list.get(position-1).txt);//这一定要放在clearFocus()之后，否则最后输入的内容在拉回来时会消失
                holder.etTxt.setSelection(holder.etTxt.getText().length());
            } else {
                holder.image.setVisibility(View.VISIBLE);
                holder.etTxt.setVisibility(View.GONE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.image.getLayoutParams();
                params.width = CommonUtil.getScreenWidth(context)/4*3;
                params.height = CommonUtil.getScreenWidth(context)/4*3;
                holder.image.setLayoutParams(params);
                LoaderImage.getInstance(0).ImageLoaders(list.get(position - 1).txt, holder.image);
            }
        }
    }

    // 获取焦点所在EditText的部分信息
    public String[] getInfo(){
        String []info = new String[3];
        info[0] = edittext.getText().toString();
        info[1] = edittext.getSelectionStart() +"";
        info[2] = positions +"";
        return info;
    }

    public String getTitle(){
        return title;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        EditText etTitle;
        EditText etTxt;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            etTitle = (EditText) itemView.findViewById(R.id.et_title);
            etTxt = (EditText) itemView.findViewById(R.id.et_txt);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    private MyWatcher mWatcher;
    private int index = 0;
    class MyWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            list.get(index).txt=s.toString();
        }
    }
}