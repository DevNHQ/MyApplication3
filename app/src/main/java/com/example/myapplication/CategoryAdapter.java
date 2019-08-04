package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.chat.Chat;
import com.example.myapplication.model.Categorie;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    public boolean isOnLoadMore() {
        return onLoadMore;
    }

    public void setOnLoadMore(boolean onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    private boolean onLoadMore = true;

    private List<Chat> categories;

    public CategoryAdapter(Context context, List<Chat> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == ITEM ) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.category, parent, false);
            return new CateHolder(view);
        }
        else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.loadmore, parent, false);
            return new LoadHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CateHolder) {
            Chat category = categories.get(position);
            if(category.getAuthorName().equals("admin") && category.getContent().getRendered() !=null) {
                Log.e("nameead",category.getAuthorName());
                ((CateHolder) holder).tvMessageAmi.setText(category.getContent().getRendered());

            }
            else{
                ((CateHolder) holder).tvMessageAmin.setText(category.getContent().getRendered());
                ((CateHolder) holder).tvTimeOther.setText(category.getDate().substring(11,16));
            }
        } else if (holder instanceof LoadHolder){

        }
    }

    int ITEM = 1;
    int LOAD_MORE = 2;

    @Override
    public int getItemViewType(int position) {

        if (onLoadMore){
            if (position == categories.size() - 1) return LOAD_MORE;
            else return ITEM;
        }else return ITEM;

      }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
