package android.example.newsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;


public class ArticleTaskLoader extends AsyncTaskLoader<ArrayList<Article>> {

    String url;

    public ArticleTaskLoader(@NonNull Context context, String stringUrl) {
        super(context);
        url = stringUrl;

    }

    @Nullable
    @Override
    public ArrayList<Article> loadInBackground() {
        URL parsedUrl = MainActivity.createUrl(url);
        return MainActivity.parseJson(MainActivity.makeHttpConnection(parsedUrl));

    }
}
