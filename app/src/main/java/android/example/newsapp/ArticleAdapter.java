package android.example.newsapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private ArrayList<Article> news;

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
        Article article = news.get(position);
        holder.getArticleTextView().setText(article.getName());
        holder.getSectionTextView().setText(article.getSection());
        holder.getAuthorTextView().setText(article.getAuthor());
        holder.getDateTextView().setText(article.getDate());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
