package com.oy.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public abstract class TypeMoreAdapter<T extends TypeMoreAdapter.OnType> extends BaseAdapter {
    private Context context;
    private List<T> datas;
    private int[] resids;//如果布局的返回类型为0，那采用resids[0]所对应的布局
    public TypeMoreAdapter(Context context,int...resids){
        this.context = context;
        this.resids = resids;
        this.datas = new ArrayList<>();
    }
    public void setDatas(List<T> datas){
        this.datas = datas;
        this.notifyDataSetChanged();
    }
    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }
    public void addNewDatas(List<T> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return resids.length;
    }

    @Override
    public int getItemViewType(int position) {
        OnType onType = datas.get(position);
        return onType.getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(resids[getItemViewType(position)], null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        bindData(viewHolder, datas.get(position), getItemViewType(position));
        return convertView;
    }
    protected abstract void bindData(ViewHolder viewHolder, T data, int typeView);
    public class ViewHolder{
        private View viewLayout;
        SparseArray<View> sparseArray = new SparseArray<>();
        public ViewHolder(View viewLayout){
            this.viewLayout = viewLayout;
        }
        public View getView(int id){
            View view = sparseArray.get(id);
            if (view==null){
                view = viewLayout.findViewById(id);
                sparseArray.put(id,view);
            }
            return view;
        }
        public ViewHolder bindTextView(int id,String value){
            TextView textView = (TextView) this.getView(id);
            textView.setText(value);
            return this;
        }

        /**
         * 下载原图
         * @param id
         * @param url
         * @param defaultId
         * @return
         */
        public ViewHolder bindImageView(int id,String url,int defaultId){
            ImageView imageView = (ImageView) getView(id);
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .thumbnail(0.1f)
                    .placeholder(defaultId)
                    .into(imageView);
            return this;
        }

        public ViewHolder bindWebView(int id,String content){
            WebView webView = (WebView) getView(id);
            Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
            Matcher m = CRLF.matcher(content);
            if (m.find()) {
                content = m.replaceAll("<br>");
            }
            // 设置是否可缩放
            webView.getSettings().setBuiltInZoomControls(true);
            webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
            return this;
        }
    }
    public interface OnType{
        int getType();
    }

}
