package com.kaicom.api.adapter;

import java.util.Collection;
import java.util.List;




import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 封装BaseAdapter
 * 
 * @author scj
 */
public abstract class AbsCommonAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> datas;
    protected int itemLayoutId;

    public AbsCommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        this.context = context;
        this.datas = datas;
        this.itemLayoutId = itemLayoutId;
    }
    
    public synchronized void addAll(Collection<T> collection) {
    	if (collection != null) {
    		datas.addAll(collection);
    		notifyDataSetChanged();
    	}
    }
    
    public synchronized void setData(Collection<T> collection) {
    	if (collection != null) {
    		datas.clear();
    		datas.addAll(collection);
    		notifyDataSetChanged();
    	}
    }
    
    public synchronized void add(T data) {
    	if (data != null) {
    		datas.add(data);
    		notifyDataSetChanged();
    	}
    }
    
    public synchronized T remove(int index) {
    	if (index < 0 || index >= datas.size())
    		throw new IndexOutOfBoundsException("越界了");
    	T result = datas.remove(index);
    	notifyDataSetChanged();
    	return result;
    }
    
    public boolean remove(T o) {
    	boolean removed = datas.remove(o);
    	if (removed)
    		notifyDataSetChanged();
    	return removed;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /**
     * 用于将datas中的数据显示在view上
     * <p>
     * <li> 1) 从ViewHolder中获取view对象<br>
     * <li> 2) 从数据集中获取数据<br>
     * <li> 3) 将数据绑定到view上<br>
     * @param holder ViewHolder
     * @param data T
     */
    public abstract void convert(ViewHolder holder, T data, int position);

    /**
     * 获得ViewHolder对象
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    protected ViewHolder getViewHolder(int position, View convertView,
            ViewGroup parent) {
        return ViewHolder.get(context, convertView, parent, itemLayoutId);
    }

}
