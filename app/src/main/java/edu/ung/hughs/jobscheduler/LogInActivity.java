package edu.ung.hughs.jobscheduler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogInActivity extends AppCompatActivity {

    //Declaring Layout Elements
    Button signInButton, signUpButton;
    EditText userField,passField;
    TextView title;

    //Connection objects

    int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().getBooleanExtra("EXIT", false))
            finish();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_view);

        //Assigning Layout Elements
        signInButton = (Button) findViewById(R.id.signInButton);
        signUpButton = (Button) findViewById(R.id.SignUpButton);
        userField = (EditText) findViewById(R.id.userField);
        passField = (EditText) findViewById(R.id.passField);
        title = (TextView) findViewById(R.id.TitleText);



        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO dao = new DAO();
                System.out.println("Created DAO object");
                String username = userField.getText().toString();
                String password = passField.getText().toString();
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] encodedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                    password = bytesToHex(encodedBytes);
                }
                catch(java.security.NoSuchAlgorithmException e)
                {
                    e.printStackTrace();
                }
                personID = dao.logIn(username, password);
                if(personID > 0)
                {
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    intent.putExtra("personID",personID);
                    startActivity(intent);
                }
                if(personID == 0)
                {
                    Toast.makeText(getBaseContext(),"Invalid Credentials",Toast.LENGTH_SHORT);
                }
                if(personID == -1)
                {
                    Toast.makeText(getBaseContext(),"Connection Error Occured",Toast.LENGTH_SHORT);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(LogInActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
