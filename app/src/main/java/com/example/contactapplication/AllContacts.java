package com.example.contactapplication;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllContacts extends AppCompatActivity {

    RecyclerView rvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        getAllContacts();
    }

    private void getAllContacts() {
        List<ContactVO> contactVOList = new ArrayList();

        if(ContextCompat.checkSelfPermission(AllContacts.this,Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED) {
            ContentResolver resolver = getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = resolver.query(uri, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    ContactVO contactVO = new ContactVO();
                    contactVO.setContactName(name);
                    contactVO.setContactNumber(number);
                    //ContactVO.setContactImage(image);
                    contactVOList.add(contactVO);
                    // contactVOList.add(new ContactVO(R.drawable.imag,name,number));
                }
                cursor.close();
            }
            AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
            rvContacts.setAdapter(contactAdapter);
        }
        else {
            ActivityCompat.requestPermissions(AllContacts.this,new String[]{Manifest.permission.READ_CONTACTS},123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==123){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getAllContacts();
            }
        }
        else {
            Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},123);
        }

    }
}