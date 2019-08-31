package edu.ung.hughs.jobscheduler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Job> mValues;

    private final OnJobListener onJobListener;
    public MyItemRecyclerViewAdapter(ArrayList<Job> items, OnJobListener onJobListener) {
        mValues = items;
        this.onJobListener = onJobListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view, onJobListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Integer.toString(mValues.get(position).getJobID()));
        holder.mContentView.setText(mValues.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Job mItem;
        OnJobListener onJobListener;

        public ViewHolder(View view, OnJobListener onJobListener) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            this.onJobListener = onJobListener;

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            onJobListener.onJobClick(getAdapterPosition());
        }
    }

    public interface OnJobListener {
        void onJobClick(int position);
    }
}
