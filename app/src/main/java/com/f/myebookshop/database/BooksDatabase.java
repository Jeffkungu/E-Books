package com.f.myebookshop.database;

import android.content.Context;
import android.os.AsyncTask;

import com.f.myebookshop.model.Book;
import com.f.myebookshop.model.Category;

import java.security.PrivateKey;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Category.class, Book.class}, version = 1)
public abstract class BooksDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();
    public abstract BooksDAO booksDAO();

    private static BooksDatabase instance;

    public static synchronized BooksDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), BooksDatabase.class,
                    "books_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void>{
        private CategoryDAO categoryDAO;
        private BooksDAO booksDAO;

        public InitialDataAsyncTask(BooksDatabase booksDatabase) {
            categoryDAO = booksDatabase.categoryDAO();
            booksDAO = booksDatabase.booksDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
