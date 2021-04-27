package com.example.colaninfotech.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colaninfotech.ApiClient;
import com.example.colaninfotech.ApiInterface;
import com.example.colaninfotech.ContactsAdapter;
import com.example.colaninfotech.Data;
import com.example.colaninfotech.Owner;
import com.example.colaninfotech.R;
import com.example.colaninfotech.SQLite;
import com.example.colaninfotech.SendMessage;
import com.example.colaninfotech.ui.dashboard.DashboardFragment;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//https://howtodoandroid.com/retrofit-android-example/

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Data>dataList;
    private List<Owner>owners;
    private RecyclerView.LayoutManager mLayoutManager;
    private ContactsAdapter.ContactsAdapterListener listener;
    private ContactsAdapter mAdapter;
    SendMessage SM;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.rec);

        mLayoutManager = new LinearLayoutManager(getActivity());



        listener = new ContactsAdapter.ContactsAdapterListener() {


            @Override
            public void onLongPress(int position, Data userFirebase) {




            }

            @Override
            public void onContactSelected(Data userFirebaseAdd) {

                Bundle bundle = new Bundle();
                bundle.putString("key","abc"); // Put anything what you want
                bundle.putSerializable("user", (Serializable) userFirebaseAdd);


                DashboardFragment fragmentB = new DashboardFragment();
                fragmentB.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragmentB)
                        .commit();

            }

            @Override
            public void onComment(Data userFirebaseAdd) {
                alertUpdate(userFirebaseAdd);
            }
        };



        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Data>> call = apiService.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                dataList = response.body();
                mAdapter = new ContactsAdapter(getActivity(), dataList, listener);
                //   mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);



            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });

        return root;
    }

    public void alertUpdate(final Data data){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_alert_item, null);

        final EditText editText = dialogView.findViewById(R.id.edt_comment);
        TextView textView = dialogView.findViewById(R.id.textView);
        Button button1 = dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = dialogView.findViewById(R.id.buttonCancel);


        textView.setText("Add Comment");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().trim().equalsIgnoreCase("")){
                   editText.setError("Enter Comment");
                   editText.requestFocus();
                }
                else {
                    SQLite sqLite = new SQLite(getContext());
                    long test = sqLite.user_register(data.getId(), editText.getText().toString().trim());

                    if (test == -1) {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                        dialogBuilder.dismiss();
                    } else {
                        dialogBuilder.dismiss();
                    }

                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }


}