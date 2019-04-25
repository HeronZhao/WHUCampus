package com.example.hp.mycampus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Book;
import com.example.hp.mycampus.util.LibraryInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LibraryLoginActivity extends AppCompatActivity {



    // UI references.
    private EditText studentNum;
    private EditText password;

    private ArrayList<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_login);

        studentNum = (EditText)findViewById(R.id.studnetNum);
        password = (EditText) findViewById(R.id.password);

        studentNum.setText(LoginActivity.username);

        password.setFocusable(true);
        password.setFocusableInTouchMode(true);
        password.requestFocus();

        Button mEmailSignInButton = (Button) findViewById(R.id.library_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LibraryLoginActivity.this,"玩命儿加载中~~~",Toast.LENGTH_LONG).show();

                String stn = studentNum.getText().toString().trim();
                String psd = password.getText().toString().trim();

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                LibraryInfo.Login(stn,psd);

                bookList = LibraryInfo.getHistoryList();

                if (LibraryInfo.Login(stn,psd)){
                    if (bookList.isEmpty()) {
                        Intent intent_null = new Intent(LibraryLoginActivity.this, NoticeActivity.class);
                        startActivity(intent_null);
                    }else {
                        Intent intent = new Intent(LibraryLoginActivity.this, LibraryActivity.class);
                        startActivity(intent);
                    }
                }else
                    Toast.makeText(LibraryLoginActivity.this,"密码输入错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
}


