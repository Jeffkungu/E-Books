package com.f.myebookshop.view;

import android.content.Intent;
import android.os.Bundle;

import com.f.myebookshop.R;
import com.f.myebookshop.adapter.BooksAdapter;
import com.f.myebookshop.databinding.ActivityMainBinding;
import com.f.myebookshop.model.Book;
import com.f.myebookshop.model.Category;
import com.f.myebookshop.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoryList;
    private ArrayList<Book> booksList;
    private MainActivityEventHandler mainActivityEventHandler;
    private ActivityMainBinding activityMainBinding;
    private Category selectedCategory;
    private RecyclerView booksRecyclerView;
    private BooksAdapter booksAdapter;
    private int selectedBookId;

    public static final int ADD_BOOK_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryList = (ArrayList<Category>) categories;
                showOnSpinner();
            }
        });

        mainActivityEventHandler = new MainActivityEventHandler();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setEventHandler(mainActivityEventHandler);
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    private void loadBooksArrayList(int categoryId){
        mainActivityViewModel.getBooksOfSelectedCategory(categoryId).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                booksList = (ArrayList<Book>) books;
                loadRecyclerView();
            }
        });
    }

    private void loadRecyclerView(){
        booksRecyclerView = activityMainBinding.secondaryLayout.rvBooks;
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setHasFixedSize(true);

        booksAdapter = new BooksAdapter();
        booksRecyclerView.setAdapter(booksAdapter);

        booksAdapter.setBooks(booksList);
        booksAdapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onitemClick(Book book) {
                selectedBookId = book.getBookId();
                Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
                intent.putExtra(AddAndEditActivity.BOOK_ID, selectedBookId);
                intent.putExtra(AddAndEditActivity.BOOK_NAME, book.getBookName());
                intent.putExtra(AddAndEditActivity.UNIT_PRICE, book.getUnitPrice());
                startActivityForResult(intent, EDIT_BOOK_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Book bookToDelete = booksList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(bookToDelete);
            }
        }).attachToRecyclerView(booksRecyclerView);

    }

    public class MainActivityEventHandler{
        public void onFABClicked(View view){
            Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);
        }

        public void onSelectedItem(AdapterView<?> parent, View view, int position, long id){
            selectedCategory = (Category) parent.getItemAtPosition(position);
            String message = "Id is: " + selectedCategory.getId() + "\n name is: " + selectedCategory.getCategoryName()
                    + "\n email is: " + selectedCategory.getCategoryDescription();
            Toast.makeText(parent.getContext(), message, Toast.LENGTH_LONG).show();
            loadBooksArrayList(selectedCategory.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryId = selectedCategory.getId();
        if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {

            Book book = new Book();
            book.setCategoryId(selectedCategoryId);
            book.setBookName(data.getStringExtra(AddAndEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            mainActivityViewModel.addNewBook(book);


        } else if (requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {

            Book book = new Book();
            book.setCategoryId(selectedCategoryId);
            book.setBookName(data.getStringExtra(AddAndEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));

            book.setBookId(selectedBookId);
            mainActivityViewModel.updateBook(book);


        }
    }
}
