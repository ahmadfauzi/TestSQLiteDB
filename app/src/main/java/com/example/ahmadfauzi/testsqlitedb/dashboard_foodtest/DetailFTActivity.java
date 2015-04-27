package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.model.DatabaseConnector;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;

public class DetailFTActivity extends ActionBarActivity {
    boolean isUpdate = false;
    private int _idFT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ft);

        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            FTDetailFragment fragment = new FTDetailFragment();
            Bundle bundle = getIntent().getBundleExtra("packetFromDashboard");

            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.detailFTContainer, fragment).commit();
            /*
            _idFT = 0;
            Intent intent = getIntent();
            _idFT = intent.getIntExtra("foodtestId",0);
            DatabaseConnector databaseConnector = new DatabaseConnector(this);
            FoodTest foodTest = new FoodTest();
            foodTest = databaseConnector.getFTbyId(_idFT);
            */
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_ft, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                break;
            case R.id.home:
                finish();
                break;
            case R.id.action_save:
                saveFT();
                finish();
                break;
            case R.id.action_delete:
                deleteFT();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFT() {
        FoodTest foodTest = new FoodTest();

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        foodTest.setNameFT(editTextName.getText().toString());

        EditText editTextReagent = (EditText) findViewById(R.id.editTextReagent);
        foodTest.setReagentFT(editTextReagent.getText().toString());

        EditText editTextResult = (EditText) findViewById(R.id.editTextResult);
        foodTest.setResultFT(editTextResult.getText().toString());

        DatabaseConnector databaseConnector = new DatabaseConnector(this);

        long statusInsert = -1;
        statusInsert = databaseConnector.insertFT(foodTest);

        if(statusInsert != -1){
            Log.d("DetailFTActivity", "Save FoodTest success: " + foodTest.toString());
            Toast.makeText(this, "Save FoodTest Success: " + foodTest.toString(), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Log.d("DetailFTActivity", "Save FoodTest failed: " + foodTest.toString());
            Toast.makeText(this, "Save FoodTest failed: " + foodTest.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFT() {

        Intent intent = getIntent();
        _idFT = intent.getIntExtra("FT_Id",0);

        Log.d("DetailFTActivity","on delete is running");
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetailFTActivity.this);

        builder.setTitle("Are you sure?");
        builder.setMessage("Data will be deleted");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseConnector databaseConnector = new DatabaseConnector(DetailFTActivity.this);

                databaseConnector.deleteFT(_idFT);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cancel delete data", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.show();
    }
}
