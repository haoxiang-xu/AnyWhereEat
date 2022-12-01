package com.example.anywhereeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountPageFragment extends Fragment {

    TextView userName;
    Button logout;
    RecyclerView userInfoList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountPageFragment newInstance(String param1, String param2) {
        AccountPageFragment fragment = new AccountPageFragment();
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
        return inflater.inflate(R.layout.fragment_account_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Construct logout button
        logout = view.findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.writeToFile("", "userInfo.txt", getContext());
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        //Initialize user data
        HomeActivity.userInfos = new ArrayList<>();

        String name = loadUserData(HomeActivity.userInformations);

        //Display user name
        userName = view.findViewById(R.id.userNameTextView);
        userName.setText(name);

        //Construct restaurant recyclerview
        userInfoList = view.findViewById(R.id.userInfoRecyclerView);
        UserInfoViewAdapter userInfoViewAdapter = new UserInfoViewAdapter(getContext(), HomeActivity.userInfos);
        userInfoList.setAdapter(userInfoViewAdapter);
        userInfoList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private String loadUserData(String[] userInfo){
        String[] profileList = getResources().getStringArray(R.array.profileList);

        for(int i = 1; i < profileList.length; i++){
            HomeActivity.userInfos.add(new UserInfo(profileList[i], userInfo[i], HomeActivity.infoIcon[i-1]));
        }

        return userInfo[0];
    }

}