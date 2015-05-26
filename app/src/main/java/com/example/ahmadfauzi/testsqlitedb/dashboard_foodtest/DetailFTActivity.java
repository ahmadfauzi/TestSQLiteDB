package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
//import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.color_finder.ColorFinder;
import com.example.ahmadfauzi.testsqlitedb.model.DatabaseConnector;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailFTActivity extends ActionBarActivity {
    boolean isUpdate = false;
    private int _idFT;
    //private Uri mImageCaptureUri;
    private static final int CAMERA_REQUEST_CODE = 1;
    //private ImageView imageViewFT;

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
        ImageView imageViewFT = (ImageView) findViewById(R.id.ivFoodTest);

        if(!editTextName.getText().toString().equals("") || !editTextReagent.getText().toString().equals("") || !editTextResult.getText().toString().equals("")){
            foodTest.setNameFT(editTextName.getText().toString());
            foodTest.setReagentFT(editTextReagent.getText().toString());
            foodTest.setResultFT(editTextResult.getText().toString());
            foodTest.setIdFT(_idFT);

            Bitmap photoFT = ((BitmapDrawable) imageViewFT.getDrawable()).getBitmap();
            String fileDirPhotoFT = saveToSDCard(photoFT);
            foodTest.setPhotoFT(fileDirPhotoFT);

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

    private String saveToSDCard(Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 40, bytes); //compress photo to PNG, quality from 0-100, outputstream to write compressed data

        File mFolder =  new File(Environment.getExternalStorageDirectory() + File.separator + "PhotoFoodTest");
        if(!mFolder.exists()){
            mFolder.mkdir();
        }

        String mFileName = "photo_" + String.valueOf(System.currentTimeMillis()) + ".png";
        File file = new File(mFolder.getAbsolutePath(), mFileName);

        FileOutputStream fo = null;
        try{
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        Log.d("DetailFTActivity", "Photo is saved in : " + file.getPath());
        Toast.makeText(this, "Photo is saved in : " + file.getPath(), Toast.LENGTH_SHORT).show();
        return file.toString();
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

    public void takePhotoFT(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "photo_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        try {
            //intent.putExtra("return-data", true);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        /*
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    imageViewFT = (ImageView) findViewById(R.id.imageView);
                    imageViewFT.setImageBitmap(photo);
                    //String root = Environment.getExternalStorageDirectory().toString();

                    //find dominant color
                    new ColorFinder(new ColorFinder.CallbackInterface() {
                        @Override
                        public void onCompleted(String color) {
                            //convert color from hex triplet to RGB
                            int r = Integer.valueOf(color.substring(1, 3), 16);
                            int g = Integer.valueOf(color.substring(3, 5), 16);
                            int b = Integer.valueOf(color.substring(5, 7), 16);
                            //show color in Toast Message
                            Context context = getApplicationContext();
                            Toast.makeText(context, "Your Color : " + "(" + r + " " + g + " " + b + ")", Toast.LENGTH_SHORT).show();
                            //show color in TextView
                            //TextView showColor = (TextView) findViewById(R.id.textViewColor);
                            //showColor.setText(String.valueOf(r + " " + g + " " + b));
                            EditText showColor = (EditText) findViewById(R.id.editTextResult);
                            showColor.setText(String.valueOf(r + " " + g + " " + b));
                        }
                    }).findDominantColor(photo);

                    //save cropped image to sdcard
                    File myDir = new File(root + "/PhotoFoodTest");
                    myDir.mkdirs();
                    String fname = "photo_" + String.valueOf(System.currentTimeMillis()) +".jpg";
                    File file = new File (myDir, fname);
                    if (file.exists ()) file.delete ();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) f.delete();
                }
                break;
        }
        */
        Bitmap photoFT = null;
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap bitmapPhotoFT = (Bitmap) data.getExtras().get("data");
                photoFT = ThumbnailUtils.extractThumbnail(bitmapPhotoFT, 300, 300);
                ImageView imageViewFT = (ImageView) findViewById(R.id.ivFoodTest);
                imageViewFT.setImageBitmap(photoFT);

                /*
                Uri photoUri = data.getData();
                if(photoUri != null){
                    try{
                        photoFT = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                        ImageView imageViewFT = (ImageView) findViewById(R.id.imageView);
                        imageViewFT.setImageBitmap(photoFT);
                    }catch (FileNotFoundException e){
                        throw new RuntimeException(e);
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
                */

                new ColorFinder(new ColorFinder.CallbackInterface() {
                    @Override
                    public void onCompleted(String color) {
                        //convert color from hex triplet to RGB
                        int r = Integer.valueOf(color.substring(1, 3), 16);
                        int g = Integer.valueOf(color.substring(3, 5), 16);
                        int b = Integer.valueOf(color.substring(5, 7), 16);
                        //show color in Toast Message
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Your Color : " + "(" + r + " " + g + " " + b + ")", Toast.LENGTH_SHORT).show();
                        //show color in TextView
                        //TextView showColor = (TextView) findViewById(R.id.textViewColor);
                        //showColor.setText(String.valueOf(r + " " + g + " " + b));
                        EditText showColor = (EditText) findViewById(R.id.editTextResult);
                        showColor.setText(String.valueOf(r + " " + g + " " + b));
                    }
                }).findDominantColor(photoFT);

            }
        }
    }
}
