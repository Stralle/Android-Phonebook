package com.example.strahinja.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Strahinja on 4/9/2018.
 */

public class AddNewContactActivity extends AppCompatActivity {

    DBHelper myDb;
    EditText addFirstName, addLastName, addPhone, addEmail;
    Button createContact;
    ImageView addImage;
    CheckBox favorite;
    int flag = 0;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        myDb = new DBHelper(this);

        addFirstName = findViewById(R.id.add_first_name);
        addLastName = findViewById(R.id.add_last_name);
        addPhone = findViewById(R.id.add_phone);
        addEmail = findViewById(R.id.add_email);
        addImage = findViewById(R.id.add_image);
        addImage.setImageResource(R.drawable.ic_person);
        addImage.setTag(R.drawable.ic_person);
        favorite = findViewById(R.id.add_favorite);

        createContact = findViewById(R.id.create_contact);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        //Image integer? or string?
        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = favorite.isChecked()?1:0;

                if (flag == 0) {
                    Log.d("FLAG", "IS "+flag);
                    addImage.setImageResource(R.drawable.ic_person);
                }

                boolean isInserted = myDb.insertData(
                        addFirstName.getText().toString(),
                        addLastName.getText().toString(),
                        addEmail.getText().toString(),
                        addPhone.getText().toString(),
                        num,
                        addImage.getDrawable(),
                        null);
                Log.d("Contact inserted: ", addFirstName.getText().toString() + " " + num);

                if(isInserted == true) {
                    Toast.makeText(AddNewContactActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(AddNewContactActivity.this, MainActivity.class);
                    AddNewContactActivity.this.startActivity(myIntent);
                    MainActivity.getThemAll();
                    finish();
                }
                else
                    Toast.makeText(AddNewContactActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != PICK_IMAGE) {
            addImage.setImageResource(R.drawable.ic_person);
            Log.d("PICK_IMAGE", " IS NOT TRIGGERED");
        }
        if (requestCode == PICK_IMAGE && null != data) {
            flag = 1;
            Uri selectedImage = data.getData();
            ParcelFileDescriptor parcelFD = null;
            try {
                parcelFD = getContentResolver().openFileDescriptor(selectedImage, "r");
                FileDescriptor imageSource = parcelFD.getFileDescriptor();

                // Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(imageSource, null, o);

                // the new size we want to scale to
                final int REQUIRED_SIZE = 1024;

                // Find the correct scale value. It should be the power of 2.
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                        break;
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }

                // decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

                addImage.setImageBitmap(bitmap);
                addImage.setTag(bitmap);
                Log.d("Added image:", addImage.toString());


            } catch (FileNotFoundException e) {
                // handle errors
                e.printStackTrace();
            } catch (IOException e) {
                // handle errors
                e.printStackTrace();
            } finally {
                if (parcelFD != null)
                    try {
                        parcelFD.close();
                    } catch (IOException e) {
                        // ignored
                    }
            }
        }
    }


}
