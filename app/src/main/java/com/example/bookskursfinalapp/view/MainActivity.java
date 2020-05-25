package com.example.bookskursfinalapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookskursfinalapp.R;
import com.example.bookskursfinalapp.adapter.BookAdapter;
import com.example.bookskursfinalapp.model.Book;
import com.example.bookskursfinalapp.model.BooksApiResponse;
import com.example.bookskursfinalapp.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.nytimes.com/svc/books/v3/lists/current/";
    private static final String APP_KEY = "r6W7LRxX5SPAzSjcw6DTSchJimM8kKAP";

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> listOfBooks;

    private ProgressBar progressBar;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.et_input);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getBooksFromServer(APP_KEY);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text){

        List<Book> filteredList = new ArrayList<>();

        for (Book book : listOfBooks){
            if (book.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(book);
            }
        }
        bookAdapter.filteredList(filteredList);
    }


    public APIInterface getInterface() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        return apiInterface;
    }

    public void getBooksFromServer(String apikey) {
        APIInterface apiInterface = this.getInterface();
        progressBar.setVisibility(View.VISIBLE);

        Call<BooksApiResponse> mService = apiInterface.getBooks(apikey);

        mService.enqueue(new Callback<BooksApiResponse>() {
            @Override
            public void onResponse(Call<BooksApiResponse> call, Response<BooksApiResponse> response) {
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

                listOfBooks = response.body().getResults().getBooks();

                bookAdapter = new BookAdapter(listOfBooks);

                bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        String name = listOfBooks.get(position).getTitle();
                        Toast.makeText(MainActivity.this, "You clicked: "+name, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                        String image = listOfBooks.get(position).getBookImage();
                        String bookTitle = listOfBooks.get(position).getTitle();
                        String author = listOfBooks.get(position).getAuthor();
                        String rank = String.valueOf(listOfBooks.get(position).getRank());
                        String isbn = listOfBooks.get(position).getPrimaryIsbn10();
                        String publisher = listOfBooks.get(position).getPublisher();
                        String description = listOfBooks.get(position).getDescription();
                        String bookImage = listOfBooks.get(position).getBookImage();
                        String buyOnAmazon = listOfBooks.get(position).getAmazonProductUrl();
                        String rankLastWeek = String.valueOf(listOfBooks.get(position).getRankLastWeek());
                        String weeksOnList = String.valueOf(listOfBooks.get(position).getWeeksOnList());

                        intent.putExtra("image", image);
                        intent.putExtra("booktitle", bookTitle);
                        intent.putExtra("author", author);
                        intent.putExtra("rank", rank);
                        intent.putExtra("isbn", isbn);
                        intent.putExtra("publisher", publisher);
                        intent.putExtra("description", description);
                        intent.putExtra("visitsite", bookImage);
                        intent.putExtra("buyonamazon", buyOnAmazon);
                        intent.putExtra("ranklastweek", rankLastWeek);
                        intent.putExtra("weeksonlist", weeksOnList);


                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(bookAdapter);




            }

            @Override
            public void onFailure(Call<BooksApiResponse> call, Throwable t) {
                call.cancel();
                Log.i("error", t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
