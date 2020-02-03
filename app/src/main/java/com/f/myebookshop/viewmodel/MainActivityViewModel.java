package com.f.myebookshop.viewmodel;

import android.app.Application;

import com.f.myebookshop.database.EBookShopRepository;
import com.f.myebookshop.model.Book;
import com.f.myebookshop.model.Category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private EBookShopRepository eBookShopRepository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Book>> booksOfSelectedCategory;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        eBookShopRepository = new EBookShopRepository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = eBookShopRepository.getCategories();
        return allCategories;
    }

    public LiveData<List<Book>> getBooksOfSelectedCategory(int categoryId) {
        booksOfSelectedCategory = eBookShopRepository.getBooks(categoryId);
        return booksOfSelectedCategory;
    }

    public void addNewBook(Book book){
        eBookShopRepository.insertBook(book);
    }

    public void updateBook(Book book){
        eBookShopRepository.updateBook(book);
    }

    public void deleteBook(Book book){
        eBookShopRepository.deleteBook(book);
    }


}
