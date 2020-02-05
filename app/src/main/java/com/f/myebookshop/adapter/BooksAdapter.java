package com.f.myebookshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f.myebookshop.R;
import com.f.myebookshop.databinding.BookListItemBinding;
import com.f.myebookshop.model.Book;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder>{
    private OnItemClickListener listener;
    private ArrayList<Book> books = new ArrayList<>();

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookListItemBinding bookListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.book_list_item, parent, false);
        return new BooksViewHolder(bookListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bookListItemBinding.setBook(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {
        private BookListItemBinding bookListItemBinding;

        public BooksViewHolder(@NonNull BookListItemBinding bookListItemBinding) {
            super(bookListItemBinding.getRoot());
            this.bookListItemBinding = bookListItemBinding;

            bookListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();

                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onitemClick(books.get(clickedPosition));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onitemClick(Book book);
    }

    public void setBooks(ArrayList<Book> newBooks) {
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BooksDiffUtilCallback(books, newBooks), false);
        books = newBooks;
        diffResult.dispatchUpdatesTo(this);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
