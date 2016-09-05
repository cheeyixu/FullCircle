package com.example.yixu.fullcircle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername, txtPassword;
    UserDatabaseHandler db;
    List<User> users;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("testapp", MODE_PRIVATE);
        editor = pref.edit();

        String getStatus=pref.getString("Logged", "nil");

        if(getStatus.equals("true")){

            Intent afterLogin = new Intent(this, AfterLoginActivity.class);

            startActivity(afterLogin);

        }
        else{

            setContentView(R.layout.activity_login);

        }

        btnLogin = (Button)findViewById(R.id.loginBtn);
        txtUsername = (EditText)findViewById(R.id.usernameEditText);
        txtPassword = (EditText)findViewById(R.id.passwordEditText);

        db = new UserDatabaseHandler(this);

        users = db.getAllUsers();



        createDatabase();

    }

    private void createDatabase() {

        if (users.size() == 0){

            db.addUser(new User("Yi Xu", "kjjw0005"));
            db.addUser(new User("Agnes", "agnes1994"));
            db.addUser(new User("Simona", "simona1985"));
            db.addUser(new User("Karen", "karen1980"));
            db.addUser(new User("Jason", "jason1900"));
            users = db.getAllUsers();

        }

    }


    public void onLoginClick(View view) {

        Intent afterLogin = new Intent(this, AfterLoginActivity.class);


        if(checkCredential(txtUsername.getText().toString(), txtPassword.getText().toString())){

            Toast.makeText(this, "Login successfully!", Toast.LENGTH_LONG).show();

            startActivity(afterLogin);

            editor.putString("Logged","true");

            editor.commit();

            finish();

        }

        else{

            Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_LONG).show();

            txtUsername.setText("");
            txtPassword.setText("");

        }

    }

    private boolean checkCredential(String username, String password) {

        for (User currentUser : users){

            if(currentUser.getUserName().equals(username) && currentUser.getUserPassword().equals(password)) {

                return true;

            }

        }

        return false;

    }

    public void newUser(View view) {

        Intent goCreateAccount = new Intent(this, CreateAccountActivity.class);

        startActivity(goCreateAccount);

        finish();

    }

    @Override
    public void onBackPressed() {

        finish();

        System.exit(0);

    }
}
