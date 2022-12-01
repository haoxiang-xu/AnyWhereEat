package com.example.anywhereeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    EditText userName;
    EditText password;
    Button login;
    Button signup;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = (EditText)view.findViewById(R.id.userNameForLogin);
        password = (EditText)view.findViewById(R.id.passwordForLogin);
        login = view.findViewById(R.id.loginButton);
        signup = view.findViewById(R.id.signupButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(loginCheck(userName.getText().toString(), password.getText().toString())){
                    HomeActivity.writeToFile(userName.getText().toString() + "#" + password.getText().toString(), "userInfo.txt", getActivity());
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Incorrect username or password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public String[] login(String username, String password) {
        if (username.equals("")) {
            Toast.makeText(getContext(), "Please input a username", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals("")) {
                Toast.makeText(getContext(), "Please input a password", Toast.LENGTH_SHORT).show();
            } else {
                String[][] accounts = readData();

                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i][0].equals(username) && accounts[i][5].equals(password)) {
                        //Toast.makeText(getContext(), "Successfully login", Toast.LENGTH_SHORT).show();
                        return accounts[i];
                    }
                }
                Toast.makeText(getContext(), "No account found with that username and password", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    public boolean loginCheck(String username, String password) {
        if (username.equals("")) {
            return false;
        } else {
            if (password.equals("")) {
                return false;
            } else {
                String[][] accounts = readData();

                if(accounts != null) {
                    for (int i = 0; i < accounts.length; i++) {
                        if (accounts[i][0].equals(username) && accounts[i][5].equals(password)) {
                            Toast.makeText(getContext(), "Successfully login", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    public String[][] readData() {
        try {
            FileInputStream fis = getActivity().openFileInput("users.txt");
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