package com.example.anywhereeat;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserInfoViewAdapter<RecyclerViewHolder> extends RecyclerView.Adapter<UserInfoViewAdapter.RecyclerViewHolder>{
    Context context;
    ArrayList<UserInfo> userInfo;

    public UserInfoViewAdapter(Context context, ArrayList<UserInfo> userInfo) {
        this.context = context;
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public UserInfoViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_list_recycler_row, parent, false);

        return new UserInfoViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewAdapter.RecyclerViewHolder holder, int position) {

        //Initialize EditText
        holder.value.setText(userInfo.get(position).getValue());

        holder.saveButton.setVisibility(View.INVISIBLE);

        holder.value.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                holder.saveButton.setVisibility(View.VISIBLE);
                holder.saveButton.setClickable(true);
                holder.saveButton.setOnClickListener(new ClickListener(position, s.toString()));
            }
            @Override
            public void beforeTextChanged(CharSequence S, int start, int count, int after) {
                // TODO Auto-generated method stub
                holder.saveButton.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        holder.icon.setImageResource(userInfo.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return userInfo.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        EditText value;
        ImageView icon;
        CardView saveButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            value = itemView.findViewById(R.id.profileInfoValue);
            icon = itemView.findViewById(R.id.profileInfoIcon);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }

    public void edit(String username, String password, String address, String phone, String email, String payment) {
        String[][] accounts = readData();
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i][0].equals(username)) {
                accounts[i][1] = address;
                accounts[i][2] = phone;
                accounts[i][3] = email;
                accounts[i][4] = payment;
                accounts[i][5] = password;
            }
        }

        File dir = context.getFilesDir();
        File originalFile = new File(dir, "users.txt");
        if (originalFile.delete()) {
            String filename = "users.txt";
            FileOutputStream fos;
            for (int i = 0; i < accounts.length; i++) {
                String toWrite = String.format("%s,%s,%s,%s,%s,%s\n", accounts[i][0], accounts[i][1], accounts[i][2], accounts[i][3], accounts[i][4], accounts[i][5]);
                try {
                    fos = context.openFileOutput(filename, Context.MODE_APPEND);
                    fos.write(toWrite.getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }
    }

    public String[][] readData() {
        try {
            FileInputStream fis = context.openFileInput("users.txt");
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

    public class ClickListener implements View.OnClickListener
    {
        public int p;
        public String edited_value;

        public ClickListener(int position, String edited_value){
            p = position;
            this.edited_value = edited_value;
        }

        @Override
        public void onClick(View view)
        {
            HomeActivity.userInformations[p+1] = edited_value;
            if(p+1 == 5){
                HomeActivity.writeToFile("", "userInfo.txt", context);
                edit(HomeActivity.userInformations[0], HomeActivity.userInformations[5], HomeActivity.userInformations[1], HomeActivity.userInformations[2], HomeActivity.userInformations[3], HomeActivity.userInformations[4]);
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                Toast.makeText(context, "Password reset successfully, please relogin to the system", Toast.LENGTH_SHORT).show();
            }else {
                edit(HomeActivity.userInformations[0], HomeActivity.userInformations[5], HomeActivity.userInformations[1], HomeActivity.userInformations[2], HomeActivity.userInformations[3], HomeActivity.userInformations[4]);
                Toast.makeText(context, "Change successfully", Toast.LENGTH_SHORT).show();
                AccountPageFragment.updateView(context);
            }
        }
    }
}
