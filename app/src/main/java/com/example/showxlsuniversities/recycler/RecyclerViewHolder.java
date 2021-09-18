package com.example.showxlsuniversities.recycler;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @ClassName RecyclerViewHolder
 * @Description TODO   适配器  RecyclerView  RecyclerViewHolder  绑定数据
 * @Author SeanLim
 * @Date 2021-9-17 10:04
 * @E-mail linlin.1016@qq.com
 * @Version 1.0
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> listView;

    private View mConvertView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.listView = new SparseArray<>();
        this.mConvertView = itemView;
        itemView.setTag(this);
    }

    public <T extends View> T getView(int viewId) {
        View view = listView.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            listView.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null) view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecyclerViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    public RecyclerViewHolder setImageUrl(int viewId, String url) {
        ImageView view = this.getView(viewId);
//        ImageLoader.getInstance().displayImage(url, view, ImageLoaderEx.getDisplayImageOptions());

        return this;
    }
}
