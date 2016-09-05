package com.example.yixu.fullcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity{

    Button createAccountBut;
    EditText newUsernameEditText, newPasswordEditText, confirmPasswordEditText;
    UserDatabaseHandler db = null;
    List<User> users = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);

        createAccountBut = (Button)findViewById(R.id.createAccountBut);
        newUsernameEditText = (EditText)findViewById(R.id.newUsernameEditText);
        newPasswordEditText = (EditText)findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = (EditText)findViewById(R.id.confirmPasswordEditText);
        db = new UserDatabaseHandler(this);
        users = db.getAllUsers();

    }

    public void onCreateAccount(View view) {

        boolean inUse = false;

        //check input
        if (newPasswordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())
                && newPasswordEditText.getText().length() >= 4
                && newUsernameEditText.getText().length() > 0){

            for(User currentUser : users){

                if (currentUser.getUserName().equals(newUsernameEditText.getText().toString())){

                    Toast.makeText(this, "Username in use.", Toast.LENGTH_LONG).show();
                    inUse = true;
                    newUsernameEditText.setText("");
                    break;

                }

            }

            if (!inUse){

                db.addUser(new User(newUsernameEditText.getText().toString(),
                        newPasswordEditText.getText().toString()));
                Toast.makeText(this, "Register success!", Toast.LENGTH_LONG).show();
                Intent goBackToLogin = new Intent(this, LoginActivity.class);
                startActivity(goBackToLogin);
                finish();

            }

        }
        else {

            if(newUsernameEditText.getText().length() == 0){

                Toast.makeText(this, "Username must not be empty!", Toast.LENGTH_LONG).show();

            }

            else if(newPasswordEditText.getText().length() < 5){

                Toast.makeText(this, "Password must be more than 4 characters!", Toast.LENGTH_LONG).show();

            }

            else {

                Toast.makeText(this, "Password mismatched!", Toast.LENGTH_LONG).show();

            }

        }

    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Registration cancelled by user.", Toast.LENGTH_LONG).show();

        Intent goBackToParent = new Intent(this, LoginActivity.class);

        startActivity(goBackToParent);

        finish();

    }
}