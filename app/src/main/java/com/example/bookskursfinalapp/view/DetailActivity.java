package com.example.bookskursfinalapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookskursfinalapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleTView;
    private TextView authorTextView;
    private TextView rankTextView;
    private TextView isbnTextView;
    private TextView publisherTextView;
    private TextView bookDescription;
    private TextView rankLastWeekTextView;
    private TextView weeksOnListTextView;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detail_book_image);
        titleTView = findViewById(R.id.detail_book_title);
        authorTextView = findViewById(R.id.detail_book_author);
        rankTextView = findViewById(R.id.detail_rank_display);
        isbnTextView = findViewById(R.id.detail_isbn_display);
        publisherTextView = findViewById(R.id.detail_publisher_display);
        bookDescription = findViewById(R.id.detail_book_description);
        rankLastWeekTextView = findViewById(R.id.rank_last_week_display);
        weeksOnListTextView = findViewById(R.id.weeks_on_list_display);

        buyButton = findViewById(R.id.buy_button);


        Intent intent = getIntent();

        String image = intent.getStringExtra("image");
        String title = intent.getStringExtra("booktitle");
        String author = intent.getStringExtra("author");
        String rank = intent.getStringExtra("rank");
        String isbn = intent.getStringExtra("isbn");
        String publisher = intent.getStringExtra("publisher");
        String description = intent.getStringExtra("description");
        String visitSite = intent.getStringExtra("visitsite");
        String buyOnAmazon = intent.getStringExtra("buyonamazon");
        String rankLastWeek = intent.getStringExtra("ranklastweek");
        String weeksOnList = intent.getStringExtra("weeksonlist");

        final Uri imageUri = Uri.parse(visitSite);

        final  Uri amazonBookUri = Uri.parse(buyOnAmazon);

        Picasso.get().load(image).into(imageView);

        titleTView.setText(title);
        authorTextView.setText("By " +author);
        rankTextView.setText(rank);
        isbnTextView.setText(isbn);
        publisherTextView.setText(publisher);
        bookDescription.setText(description);
        rankLastWeekTextView.setText(rankLastWeek);
        weeksOnListTextView.setText(weeksOnList);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, imageUri);
                startActivity(webIntent);
            }
        });

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent amazonIntent = new Intent(Intent.ACTION_VIEW, amazonBookUri);
                startActivity(amazonIntent);

            }
        });

    }
}
