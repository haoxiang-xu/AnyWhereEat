package com.example.anywhereeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    ImageView goBack;
    TextView username;
    TextView address;
    TextView cell;
    TextView email;
    TextView card;
    TextView password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Hides action bar
        getSupportActionBar().hide();

        username = findViewById(R.id.userNameForSignup);
        address = findViewById(R.id.addressForSignup);
        cell = findViewById(R.id.cellForSignup);
        email = findViewById(R.id.emailForSignup);
        card = findViewById(R.id.cardNumberForSignup);
        password = findViewById(R.id.passwordForSignup);
        signup = findViewById(R.id.signupButton_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signUp(username.getText().toString(), password.getText().toString(), address.getText().toString(), cell.getText().toString(), email.getText().toString(), card.getText().toString())){
                    HomeActivity.writeToFile(username.getText().toString() + "#" + password.getText().toString(), "userInfo.txt", SignupActivity.this);
                    Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        goBack = findViewById(R.id.cancelButton_signup);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean signUp(String username, String password, String address, String phone, String email, String payment) {
        if (username.equals("")) {
            Toast.makeText(this, "Please provide a username", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String[][] accounts = readData();
            if (accounts != null) {
                boolean isDuplicateUsername = false;
                for (int i = 0; i < accounts.length; i++) {
                    if (username.equals(accounts[i][0])) {
                        isDuplicateUsername = true;
                        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    }
                }
                if (isDuplicateUsername) {
                    Toast.makeText(this, "There is already an existing account with that username, please input a new one", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (password.equals("")) {
                    Toast.makeText(this, "Please provide a password", Toast.LENGTH_SHORT).show();
                } else {
                    if (address.equals("")) {
                        Toast.makeText(this, "Please provide an address", Toast.LENGTH_SHORT).show();
                    } else {
                        if (phone.equals("")) {
                            Toast.makeText(this, "Please provide your phone number", Toast.LENGTH_SHORT).show();
                        } else {
                            if (email.equals("")) {
                                Toast.makeText(this, "Please provide your email address", Toast.LENGTH_SHORT).show();
                            } else {
                                if (payment.equals("")) {
                                    Toast.makeText(this, "Please provide a valid payment method", Toast.LENGTH_SHORT).show();
                                } else {
                                    String filename = "users.txt";
                                    String toWrite = String.format("%s,%s,%s,%s,%s,%s\n", username, address, phone, email, payment, password);

                                    FileOutputStream fos;
                                    try {
                                        fos = openFileOutput("users.txt", Context.MODE_APPEND);
                                        fos.write(toWrite.getBytes());
                                        fos.close();
                                        Toast.makeText(this, "Successfully Signup", Toast.LENGTH_SHORT).show();
                                        return true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (password.equals("")) {
                    Toast.makeText(this, "Please provide a password", Toast.LENGTH_SHORT).show();
                } else {
                    if (address.equals("")) {
                        Toast.makeText(this, "Please provide an address", Toast.LENGTH_SHORT).show();
                    } else {
                        if (phone.equals("")) {
                            Toast.makeText(this, "Please provide your phone number", Toast.LENGTH_SHORT).show();
                        } else {
                            if (email.equals("")) {
                                Toast.makeText(this, "Please provide your email address", Toast.LENGTH_SHORT).show();
                            } else {
                                if (payment.equals("")) {
                                    Toast.makeText(this, "Please provide a valid payment method", Toast.LENGTH_SHORT).show();
                                } else {
                                    String filename = "users.txt";
                                    String toWrite = String.format("%s,%s,%s,%s,%s,%s\n", username, address, phone, email, payment, password);

                                    FileOutputStream fos;
                                    try {
                                        fos = openFileOutput("users.txt", Context.MODE_APPEND);
                                        fos.write(toWrite.getBytes());
                                        fos.close();
                                        Toast.makeText(this, "Successfully Signup", Toast.LENGTH_SHORT).show();
                                        return true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String[][] readData() {
        try {
            FileInputStream fis = openFileInput("users.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            ArrayList<String> accountsRaw = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                accountsRaw.add(line);
            }

            String[][] accounts = new String[accountsRaw.size()][6];
            for (int i = 0; i < accountsRaw.size(); i++) {
                accounts[i] = accountsRaw.get(i).split(",");
            }
            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}