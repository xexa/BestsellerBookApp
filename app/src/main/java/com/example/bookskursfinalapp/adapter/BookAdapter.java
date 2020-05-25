package com.example.bookskursfinalapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookskursfinalapp.R;
import com.example.bookskursfinalapp.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewholder> {

    public List<Book> booksList;
    // Define listener member variable
    private OnItemClickListener listener;

    public BookAdapter(List<Book> books) {
        this.booksList = books;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {


        String book = booksList.get(position).getTitle();
        holder.bookTitle.setText(book);

        String author= booksList.get(position).getAuthor();
        holder.authorName.setText(author);

        String rank = String.valueOf(booksList.get(position).getRank());
        holder.rankBook.setText(rank);

        String isbn = String.valueOf(booksList.get(position).getPrimaryIsbn10());
        holder.isbnBook.setText(isbn);

        String publisher = booksList.get(position).getPublisher();
        holder.publisherOfBook.setText(publisher);


        String imageUrl = booksList.get(position).getBookImage();
        Picasso.get().load(imageUrl).into(holder.bookImage);



    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView bookImage;
        private TextView bookTitle;
        private TextView authorName;
        private TextView rankBook;
        private TextView isbnBook;
        private TextView publisherOfBook;



        public MyViewholder(@NonNull final View itemView) {
            super(itemView);


            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.book_title);
            authorName = itemView.findViewById(R.id.book_author);
            rankBook = itemView.findViewById(R.id.rank_display);
            isbnBook = itemView.findViewById(R.id.isbn_display);
            publisherOfBook = itemView.findViewById(R.id.publisher_display);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position);
                }
            }

        }
    }

    public void filteredList(List<Book> filteredList) {
        booksList = filteredList;
        notifyDataSetChanged();
    }
}
