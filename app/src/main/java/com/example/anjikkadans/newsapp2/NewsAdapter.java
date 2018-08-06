package com.example.anjikkadans.newsapp2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> mNewsList;
    private final Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public NewsAdapter(Context context, List<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
        this.mOnItemClickListener = (OnItemClickListener) mContext;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.news_list_item_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        holder.bind(mNewsList.get(position), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private final TextView newsContent;
        private final TextView newsType;

        public NewsViewHolder(View itemView) {
            super(itemView);

            newsType = itemView.findViewById(R.id.news_type_text_view);
            newsContent = itemView.findViewById(R.id.news_content_text_view);
        }

        public void bind(final News news, final OnItemClickListener listener) {
            newsContent.setText(news.getNewsTitle());
            newsType.setText(news.getNewsType());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }


}
