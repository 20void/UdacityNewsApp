package android.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public ArrayList<Article> articles = new ArrayList<>();
    public RecyclerView recyclerView;
    private ArticleAdapter aAdapter;
    private ProgressBar progressBar;
    public final String URL = "https://content.guardianapis.com/search?section=technology&from-date=2020-08-24&to-date=2020-08-26&q=technology&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress_bar);

        aAdapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(aAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        getSupportLoaderManager().initLoader(1, null, this).forceLoad();

//        View.OnClickListener clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        };
//        recyclerView.setOnClickListener(clickListener);


        final MyOnItemClickLestener l = new MyOnItemClickLestener() {
            @Override
            public void onItemClick(Article a) {
                Uri uri = Uri.parse(a.getAuthor());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                Log.v("THIS IS CLICK LISTENER", a.getAuthor());
                startActivity(i);

            }
        };

        aAdapter.setMyOnItemClickListener(l);
    }


    public static java.net.URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static String makeHttpConnection(URL url) {
        HttpURLConnection conn = null;
        String jsonresponse = "";
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.connect();

            if (conn.getResponseCode() == 200) {
                InputStreamReader isp = new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(isp);
                String line = bufferedReader.readLine();
                StringBuilder builder = new StringBuilder();
                while (line != null) {
                    builder.append(line);
                    line = bufferedReader.readLine();
                }

                jsonresponse = builder.toString();
                Log.v("RESPONSE", jsonresponse);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return jsonresponse;


    }

    public static ArrayList<Article> parseJson(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        ArrayList<Article> news = new ArrayList<>();
        try {

            JSONObject jObj = new JSONObject(jsonString);

            JSONObject jAr0 = jObj.getJSONObject("response");
            JSONArray jAr = jAr0.getJSONArray("results");

            for (int i = 0; i < jAr.length(); i++) {
                JSONObject j = jAr.getJSONObject(i);
                String articleTitle = j.getString("webTitle");
                String section = j.getString("sectionName");
                String date = j.getString("webPublicationDate");
                String webUrl = j.getString("webUrl");
                news.add(new Article(articleTitle, section, webUrl, date));
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return news;

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new ArticleTaskLoader(this, URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        progressBar.setVisibility(View.GONE);
        aAdapter.setNewsArray(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        aAdapter.clearAdapter();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}