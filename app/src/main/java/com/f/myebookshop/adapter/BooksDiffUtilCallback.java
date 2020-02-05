package com.f.myebookshop.adapter;

import com.f.myebookshop.model.Book;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class BooksDiffUtilCallback extends DiffUtil.Callback {
    ArrayList<Book> oldBookList;
    ArrayList<Book> newBookList;

    public BooksDiffUtilCallback(ArrayList<Book> oldBookList, ArrayList<Book> newBookList) {
        this.oldBookList = oldBookList;
        this.newBookList = newBookList;
    }

    @Override
    public int getOldListSize() {
        return oldBookList==null?0:oldBookList.size();
    }

    @Override
    public int getNewListSize() {
        return newBookList==null?0:newBookList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldBookPosition, int newBookPosition) {
        return oldBookList.get(oldBookPosition).getBookId()==newBookList.get(newBookPosition).getBookId();
    }

    @Override
    public boolean areContentsTheSame(int oldBookPosition, int newBookPosition) {
        return oldBookList.get(oldBookPosition).equals(newBookList.get(newBookPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
