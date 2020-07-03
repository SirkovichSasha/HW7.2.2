package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText phoneText;
    EditText smsText;
    Button callBtn;
    Button sendBtn;

    final public static int MY_PERMISSIONS_REQUEST_CALL_PHONE=10;
    final public static int MY_PERMISSIONS_REQUEST_SEND_SMS=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        phoneText=findViewById(R.id.phoneText);
        smsText=findViewById(R.id.smsText);
        callBtn=findViewById(R.id.callBtn);
        sendBtn=findViewById(R.id.sendBtn);



        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callByNumber();

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendByNumber();
            }
        });
    }

    private void sendByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            String tel=phoneText.getText().toString();
            String message=smsText.getText().toString();
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(tel,null,message,null,null);
        }

    }

    private void callByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            String tel=phoneText.getText().toString();
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tel));
            startActivity(dialIntent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    callByNumber();
                } else {
                    finish();
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    sendByNumber();
                } else {
                    finish();
                }
            }
        }
    }

}
