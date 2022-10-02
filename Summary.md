# Main Activity
> There are more dependencies than just this source code for the final application, this is to show my competency in Mobile Application Development on Android.
``` java
package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String webMessage = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = (Button) findViewById(R.id.btn_search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchFor = ((EditText) findViewById(R.id.edit_search)).getText().toString();

                Intent i = new Intent(MainActivity.this, WebSearch.class);
                i.putExtra(webMessage, searchFor);
                startActivity(i);
            }
        });

        Button text = (Button) findViewById(R.id.btn_text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TxtMessage.class);
                startActivity(i);
            }
        });
    }



    private boolean checkPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        return (permissionCheck == getPackageManager().PERMISSION_GRANTED);
    }

    public void dialPhone(View view) {
        String number = "07707520042";
        if(checkPermission("android.permission.CALL_PHONE")==false) {
            Toast toast = Toast.makeText(this, "No Permissions to Call", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel: " + number));
            startActivity(intent);
        }
    }
}
```

# TxtMessage Module
``` java
package com.example.app2;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;


public class TxtMessage extends AppCompatActivity {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_message);

        send = (Button) findViewById(R.id.btn_sendMessage);
        send.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            send.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}
                    , SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    send.setEnabled(true);
                }
                return;
            }
        }
    }

    private boolean checkPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        return (permissionCheck == getPackageManager().PERMISSION_GRANTED);
    }

    public void send(View view) {
        String phoneNumber = ((EditText) findViewById(R.id.editTextTextNumber)).getText().toString();
        String msg = ((EditText) findViewById(R.id.editTextMessage)).getText().toString();

        if(phoneNumber == null || phoneNumber.length() == 0 || msg == null || msg.length() == 0) {
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
            Toast.makeText(this, "Your message has been sent", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
        }
    }
}
```

# WebViewer Module
``` java
package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class WebSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        Intent i = getIntent();
        String webMessage = i.getStringExtra(MainActivity.webMessage);

        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl("https://www." + webMessage + ".ac.uk/");
    }
}
```
