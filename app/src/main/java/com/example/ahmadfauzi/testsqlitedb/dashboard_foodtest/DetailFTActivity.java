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
    private int _idFT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ft);

        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            FTDetailFragment fragment = new FTDetailFragment();
            Bundle bundle = getIntent().getBundleExtra("packetFromDashboard");

            Intent intent = getIntent();
            _idFT = intent.getIntExtra("FT_Id",0);

            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.detailFTContainer, fragment).commit();
            //Toast.makeText(this, "DetailFTActivity, ID = " + String.valueOf(_idFT), Toast.LENGTH_SHORT).show();
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
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                saveFT();
                break;
            case R.id.action_edit:
                isUpdate = true;
                break;
            case R.id.action_delete:
                deleteFT();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFT() {
        Intent intent = getIntent();
        _idFT = intent.getIntExtra("FT_Id",0);

        FoodTest foodTest = new FoodTest();

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextReagent = (EditText) findViewById(R.id.editTextReagent);
        EditText editTextResult = (EditText) findViewById(R.id.editTextResult);

        if(!editTextName.getText().toString().equals("") || !editTextReagent.getText().toString().equals("") || !editTextResult.getText().toString().equals("")){
            foodTest.setNameFT(editTextName.getText().toString());
            foodTest.setReagentFT(editTextReagent.getText().toString());
            foodTest.setResultFT(editTextResult.getText().toString());
            foodTest.setIdFT(_idFT);

            DatabaseConnector databaseConnector = new DatabaseConnector(this);

            long statusInsert = -1;
            //Toast.makeText(this, "DetailFTActivity(SaveFT), ID = " + String.valueOf(_idFT) + ", /" + foodTest.toString(), Toast.LENGTH_SHORT).show();

            if(_idFT != 0){
                statusInsert = databaseConnector.updateFT(foodTest);
                Log.d("DetailFTActivity", "Update : " + foodTest.toString());
                //Toast.makeText(this, "DetailFTActivity, Update : " + foodTest.toString(), Toast.LENGTH_SHORT).show();
            }else{
                statusInsert = databaseConnector.insertFT(foodTest);
            }

            if(statusInsert != -1){
                Log.d("DetailFTActivity", "Save FoodTest success: " + foodTest.toString());
                Toast.makeText(this, "Save FoodTest Success: " + foodTest.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Log.d("DetailFTActivity", "Save FoodTest failed: " + foodTest.toString());
                Toast.makeText(this, "Save FoodTest failed: " + foodTest.toString(), Toast.LENGTH_SHORT).show();
            }
            finish();
        }else{
            Toast.makeText(this, "This field must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFT() {

        Intent intent = getIntent();
        _idFT = intent.getIntExtra("FT_Id",0);

        Log.d("DetailFTActivity","on delete is running, ID = " +  String.valueOf(_idFT));

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
