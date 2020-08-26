package android.example.newsapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    private TextView articleTextView;
    private TextView sectionTextView;
    private TextView authorTextView;
    private TextView dateTextView;

    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        articleTextView = (TextView) itemView.findViewById(R.id.header);
        sectionTextView = (TextView) itemView.findViewById(R.id.section);
        authorTextView = (TextView) itemView.findViewById(R.id.author);
        dateTextView = (TextView) itemView.findViewById(R.id.date);
    }


}
