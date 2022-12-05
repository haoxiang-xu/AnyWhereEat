package com.example.anywhereeat;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CustomerServiceActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button send;

    TextView confirm;
    TextView ask;
    TextView orderId;
    TextView reply;
    TextView ans;

    CardView c_confirm;
    CardView c_ask;
    CardView c_orderld;
    CardView c_reply;

    Button provideHelp;

    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        //Hides action bar
        getSupportActionBar().hide();

        radioGroup = findViewById(R.id.radioGroup);

        send = findViewById(R.id.send);

        confirm = findViewById(R.id.confirm);
        ask = findViewById(R.id.ask);
        orderId = findViewById(R.id.orderId);
        reply = findViewById(R.id.reply);
        ans = findViewById(R.id.ans);

        provideHelp = findViewById(R.id.provideHelpButton);

        provideHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerServiceActivity.this, HelpVideoActivity.class);
                startActivity(intent);
            }
        });

        c_confirm = findViewById(R.id.confirm_card);
        c_ask = findViewById(R.id.ask_card);
        c_orderld = findViewById(R.id.ordeid_card);
        c_reply = findViewById(R.id.reply_card);

        goBack = findViewById(R.id.cs_goBackButton);
        goBack.setClickable(true);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerServiceActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                orderId.setVisibility(View.VISIBLE);
                c_orderld.setVisibility(View.VISIBLE);
                orderId.setText(ans.getText());
                reply.setVisibility(View.VISIBLE);
                c_reply.setVisibility(View.VISIBLE);
            }

        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);

                confirm.setVisibility(View.VISIBLE);
                c_confirm.setVisibility(View.VISIBLE);
                confirm.setText(radioButton.getText() + "?");
                ask.setVisibility(View.VISIBLE);
                c_ask.setVisibility(View.VISIBLE);

            }
        });
    }
}