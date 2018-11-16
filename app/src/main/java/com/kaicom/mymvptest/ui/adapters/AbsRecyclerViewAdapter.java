package com.kaicom.mymvptest.ui.adapters;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

/**
 * 封装RecyclerView的adapter
 *
 * @author scj
 */
public abstract class AbsRecyclerViewAdapter<T> extends RecyclerView.Adapter<AbsRecyclerViewAdapter.RecyclerViewHolder> {

    protected List<T> items;
    private int itemLayout;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public AbsRecyclerViewAdapter(@NonNull List<T> items, @LayoutRes int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return createRecyclerViewHolder(view);
    }

    protected RecyclerViewHolder createRecyclerViewHolder(View view) {
        return new RecyclerViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        convert(holder, items.get(position), position);
    }

    protected abstract void convert(RecyclerViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void addAll(Collection<T> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void resetData(Collection<T> collection) {
        if (collection != null) {
            items.clear();
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void add(T item) {
        if (item != null) {
            items.add(item);
            notifyDataSetChanged();
        }
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public T remove(int index) {
        if (index < 0 || index >= items.size())
            throw new IndexOutOfBoundsException("越界了");
        T result = items.remove(index);
        notifyDataSetChanged();
        return result;
    }

    public boolean remove(T o) {
        boolean removed = items.remove(o);
        if (removed)
            notifyDataSetChanged();
        return removed;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> views;

        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            this.views = new SparseArray<>();
            if (itemClickListener != null)
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(itemView, getLayoutPosition());
                    }
                });

            if (itemLongClickListener != null)
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        itemLongClickListener.onItemLongClick(itemView, getLayoutPosition());
                        return true;
                    }
                });
        }

        @SuppressWarnings("unchecked")
        public <V extends View> V getView(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (V) view;
        }

    }

}
