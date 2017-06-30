package com.example.axay.o2hleave;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static android.R.attr.typeface;

public class SelfieActivity extends AppCompatActivity {
    Context context;
     TextView textView;
    private static final int CAMERA_REQUEST = 1888;

    Button skip;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);
        skip=(Button)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnclickRegister();
            }
        });
        textView=(TextView)findViewById(R.id.textView);

        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/o2hfont.ttf");
        textView.setTypeface(face);
        this.imageView = (ImageView)this.findViewById(R.id.selfie);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            Intent intent=new Intent(SelfieActivity.this,RegisterActivity.class);
            intent.putExtra("photo",photo);
            startActivity(intent);
        }
    }

    public void OnclickRegister()
    {
        Intent intent=new Intent(SelfieActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
