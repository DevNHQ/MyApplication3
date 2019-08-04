package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.chat.Chat;
import com.example.myapplication.model.Categorie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    String url = "http://asian.dotplays.com/wp-json/wp/v2/categories?" +
            "page=1&per_page=5";

    private Toolbar toolbar;
    private RecyclerView lvList;

    private SwipeRefreshLayout swipe;


    private int page = 1;
    private int per_page = 5;


    private List<Chat> categories;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        lvList = findViewById(R.id.lvList);
        swipe = findViewById(R.id.swipe);
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);
        linearLayoutManager = new LinearLayoutManager(this);
        lvList.setLayoutManager(linearLayoutManager);
        lvList.setAdapter(categoryAdapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                categories.clear();
                getData(page, per_page);
            }
        });

        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getData(page + 1, per_page);
            }
        });
        getData(page + 1, per_page);

    }

    public void getData(int page, int per_page) {

        PolyRetrofit.getInstance().getChat(page, per_page).enqueue(new Callback<List<Chat>>() {
                    @Override
                    public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                        swipe.setRefreshing(false);
                        if (response.body().size() == 0){
                            Log.e("daaa", String.valueOf(response.body().size()));
                            lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                @Override
                                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                                }
                            });

                            categoryAdapter.setOnLoadMore(false);

                        }

                        categories.addAll(response.body());
                        categoryAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<Chat>> call, Throwable t) {
                        swipe.setRefreshing(false);
                    }
                });

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.action_settings:
//                runnaBle();
//                return true;
//            case R.id.action_chat:
//                runnaBle();
//                return true;
//            case R.id.action_message:
//                runnaBle();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    public void runnaBle(){
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.show();
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progress.cancel();

            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
    }

}
