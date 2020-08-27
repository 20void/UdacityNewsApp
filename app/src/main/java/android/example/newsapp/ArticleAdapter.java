package android.example.newsapp;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private ArrayList<Article> news;
    private MyOnItemClickLestener listener;


    public void setNewsArray(ArrayList<Article> list){
        news = list;
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        news = new ArrayList<>();
    }
    public ArticleAdapter(ArrayList<Article> a){
        news = a;
    }
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout l = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        ArticleViewHolder articleViewHolder = new ArticleViewHolder(l);
        return articleViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final Article article = news.get(position);
        holder.getArticleTextView().setText(article.getName());
        holder.getSectionTextView().setText(article.getSection());
        holder.getDateTextView().setText(article.getDate());


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (listener !=null) {
                   listener.onItemClick(article);
               }
           }
       });





    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setMyOnItemClickListener(MyOnItemClickLestener listener){
        this.listener = listener;
    }

}
