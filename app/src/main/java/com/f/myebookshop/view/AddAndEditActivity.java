package com.f.myebookshop.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.f.myebookshop.databinding.ActivityAddAndEditBinding;
import com.f.myebookshop.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.widget.Toast;

import com.f.myebookshop.R;

public class AddAndEditActivity extends AppCompatActivity {
    private Book book;
    public static final String BOOK_ID = "bookId";
    public static final String BOOK_NAME = "bookName";
    public static final String UNIT_PRICE = "unitPrice";
    private ActivityAddAndEditBinding activityAddAndEditBinding;
    private AddAndEditActivityEventHandler addAndEditActivityEventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);

        book = new Book();

        activityAddAndEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_and_edit);
        activityAddAndEditBinding.setBook(book);

        addAndEditActivityEventHandler = new AddAndEditActivityEventHandler(this);
        activityAddAndEditBinding.setEventHandler(addAndEditActivityEventHandler);

        Intent intent = getIntent();
        if (intent.hasExtra(BOOK_ID)) {
            setTitle("Edit Book");
            book.setBookName(intent.getStringExtra(BOOK_NAME));
            book.setUnitPrice(intent.getStringExtra(UNIT_PRICE));
        } else {
            setTitle("Add New Book");
            Toast.makeText(this, "No ID", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public class AddAndEditActivityEventHandler {
        Context context;

        public AddAndEditActivityEventHandler(Context context) {
            this.context = context;
        }

        public void onSubmitButtonClicked(View view) {
            if (book.getBookName() == null) {
                Toast.makeText(context, "Name field cannot be empty", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(BOOK_NAME, book.getBookName());
                intent.putExtra(UNIT_PRICE, book.getUnitPrice());
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }



}
