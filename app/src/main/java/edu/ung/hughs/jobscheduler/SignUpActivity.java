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
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SignUpActivity extends AppCompatActivity {

    EditText un,pw,cpw,fn,ln,company;
    Button signUpBtn;
    String username,password,firstName,lastName,cmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_view);

        //Assign Elements
        un = (EditText) findViewById(R.id.userNameEditText);
        pw = (EditText) findViewById(R.id.passwordEditText);
        cpw = (EditText) findViewById(R.id.confirmPasswordEditText);
        fn = (EditText) findViewById(R.id.firstNameEditText);
        ln = (EditText) findViewById(R.id.lastNameEditText);
        company = (EditText) findViewById(R.id.companyEditText);
        signUpBtn = (Button) findViewById(R.id.SignUpButton);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(un.getText() != null && pw.getText() != null && cpw.getText() != null && fn.getText() != null && ln.getText() != null)
                {
                    if (!pw.getText().toString().equals(cpw.getText().toString()))
                        Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    else {
                        username = un.getText().toString();
                        password = pw.getText().toString();
                        firstName = fn.getText().toString();
                        lastName = ln.getText().toString();
                        if (company.getText() != null)
                            cmp = company.getText().toString();
                        if(username.length() > 30 || password.length() > 30)
                            Toast.makeText(SignUpActivity.this, "Usernames and passwords may not exceed 30 characters!", Toast.LENGTH_LONG).show();
                        else{
                            try {
                                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                byte[] encodedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                                String hashedPass = bytesToHex(encodedBytes);
                                DAO dao = new DAO();
                                dao.signUp(firstName, lastName, cmp, username, hashedPass);
                                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                startActivity(intent);
                            }
                            catch(NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Please enter all required fields!", Toast.LENGTH_LONG).show();
                }
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

}
