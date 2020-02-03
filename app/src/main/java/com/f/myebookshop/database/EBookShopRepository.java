package com.f.myebookshop.database;

import android.app.Application;
import android.os.AsyncTask;

import com.f.myebookshop.model.Book;
import com.f.myebookshop.model.Category;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EBookShopRepository {
    private CategoryDAO categoryDAO;
    private BooksDAO booksDAO;
    private LiveData<List<Category>> categories;
    private LiveData<List<Book>> books;

    public EBookShopRepository(Application application) {
        BooksDatabase booksDatabase = BooksDatabase.getInstance(application);
        categoryDAO = booksDatabase.categoryDAO();
        booksDAO = booksDatabase.booksDAO();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategory();
    }

    public LiveData<List<Book>> getBooks(int categoryId) {
        return booksDAO.getCategoryBooks(categoryId);
    }

    public void insertCategory(Category category){
        new InsertCategoryAsyncTask(categoryDAO).execute(category);
    }

    public void insertBook(Book book){
        new InsertBookAsyncTask(booksDAO).execute(book);
    }

    public void deleteBook(Book book){
        new DeleteBookAsyncTask(booksDAO).execute(book);
    }

    public void updateBook(Book book){
        new UpdateBookAsyncTask(booksDAO).execute(book);
    }

    public void deleteCategory(Category category){
        new DeleteCategoryAsyncTask(categoryDAO).execute(category);
    }

    public void updateCategory(Category category){
        new UpdateCategoryAsyncTask(categoryDAO).execute(category);
    }

    private static class InsertBookAsyncTask extends AsyncTask<Book, Void, Void>{
        private BooksDAO booksDAO;

        public InsertBookAsyncTask(BooksDAO booksDAO) {
            this.booksDAO = booksDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            booksDAO.insert(books[0]);
            return null;
        }
    }

    private static class DeleteBookAsyncTask extends AsyncTask<Book, Void, Void>{
        private BooksDAO booksDAO;

        public DeleteBookAsyncTask(BooksDAO booksDAO) {
            this.booksDAO = booksDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            booksDAO.delete(books[0]);
            return null;
        }
    }

    private static class UpdateBookAsyncTask extends AsyncTask<Book, Void, Void>{
        private BooksDAO booksDAO;

        public UpdateBookAsyncTask(BooksDAO booksDAO) {
            this.booksDAO = booksDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            booksDAO.update(books[0]);
            return null;
        }
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDAO categoryDAO;

        public InsertCategoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDAO.insert(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDAO categoryDAO;

        public DeleteCategoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDAO.delete(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void>{
        private CategoryDAO categoryDAO;

        public UpdateCategoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDAO.update(categories[0]);
            return null;
        }
    }
}
