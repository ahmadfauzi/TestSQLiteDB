package com.example.ahmadfauzi.testsqlitedb.login;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest.DasboardFTActivity;

public class LoginActivity extends ActionBarActivity {
    EditText mEditUserameLogin, mEditPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditUserameLogin = (EditText) findViewById(R.id.etUsername);
        mEditPasswordLogin = (EditText) findViewById(R.id.etPassword);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void enterLogin(View view) {
        String nameAccount="";
        String password="";

        nameAccount = mEditUserameLogin.getText().toString();
        password = mEditPasswordLogin.getText().toString();
        if(nameAccount.equals("") || password.equals("")) {
            Toast.makeText(this, "Username or Password must be filled",Toast.LENGTH_SHORT).show();
        }else {
            if (nameAccount.equals(password)) {
                Intent intent = new Intent(this, DasboardFTActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
