package android.example.newsapp;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        String newdate = formatDate(article);
        holder.getDateTextView().setText(newdate);


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

    public String formatDate(Article a){
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


        Date d = null;
        try
        {
            d = input.parse(a.getDate());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(d);

        return formatted;
    }

}
