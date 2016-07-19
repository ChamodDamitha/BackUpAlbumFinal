package com.example.chamod.backupalbumfinal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Handlers.AccountHandler;


public class NewAccountActivity extends ActionBarActivity {

    private AccountHandler accountHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        accountHandler=AccountHandler.getInstance();
        accountHandler.setNewAccountActivity(this);

        final EditText nameTxt=(EditText)findViewById(R.id.nameTxt);
        final EditText emailTxt=(EditText)findViewById(R.id.emailTxt);
        final EditText usernameTxt=(EditText)findViewById(R.id.userNameTxt);
        final EditText passwordTxt=(EditText)findViewById(R.id.passwordTxt);
        final EditText confrmPasswordTxt=(EditText)findViewById(R.id.confrmPasswordTxt);


        Button btnCreateAccount=(Button)findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        accountHandler.createAccount(nameTxt.getText().toString(),emailTxt.getText().toString()
                                ,usernameTxt.getText().toString(),passwordTxt.getText().toString()
                                ,confrmPasswordTxt.getText().toString());
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
