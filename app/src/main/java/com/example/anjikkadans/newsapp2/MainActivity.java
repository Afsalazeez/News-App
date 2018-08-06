package com.example.anjikkadans.newsapp2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PortUnreachableException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListener {

    private static final String API_KEY = BuildConfig.NEWS_API_KEY;
    private RecyclerView newsRecyclerView;
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<News> newsList;
    private TextView errotTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup the connectivity manager and check connection status
        ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Check currently active network
        NetworkInfo netInfo = connectMgr.getActiveNetworkInfo();
        //If there is an active network connection get data otherwise display error
        if (netInfo != null && netInfo.isConnected()) {
            new getNews().execute();
            hideErrorTextView();
        } else {
            hideProgressBar();
            showErrorTextView();
        }
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onClick(int position) {
        News clickedNewsItem = newsList.get(position);
        String webURL = clickedNewsItem.getNewsURL();

        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(webURL));
        startActivity(webIntent);
    }

    public class getNews extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://content.guardianapis.com/search?api-key=" + API_KEY + "&format=json";
            NetworkUtils networkUtils = new NetworkUtils();
            newsList = networkUtils.getNews(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (newsList != null) {
                hideProgressBar();
                showNewsData(newsList);
            }
        }
    }


    public void showNewsData(List<News> newsList) {
        newsAdapter = new NewsAdapter(this, newsList);
        newsRecyclerView.setAdapter(newsAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        newsRecyclerView.addItemDecoration(itemDecor);
    }

    public void showProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showErrorTextView() {
        errotTextView = (TextView) findViewById(R.id.error_text_view);
        errotTextView.setVisibility(View.VISIBLE);
    }

    public void hideErrorTextView() {
        errotTextView = (TextView) findViewById(R.id.error_text_view);
        errotTextView.setVisibility(View.INVISIBLE);
    }
}
