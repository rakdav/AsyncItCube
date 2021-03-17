package com.example.async;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button load;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private String path;
    private EditText pathEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load=findViewById(R.id.button);
        imageView=findViewById(R.id.image);
        pathEdit=findViewById(R.id.path);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path=pathEdit.getText().toString();
                new ImageLoader().execute(path);
            }
        });
    }

    private class ImageLoader extends AsyncTask<String,Void, Bitmap>
    {
        @Override
        protected void onPreExecute() {
            progressDialog=ProgressDialog.show(MainActivity.this,"Wait","DownLoad");
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            InputStream inputStream=null;
            Bitmap bm=null;
            try {
                inputStream=new URL(strings[0]).openStream();
                bm = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressDialog.dismiss();
            imageView.setImageBitmap(bitmap);
        }
    }
}