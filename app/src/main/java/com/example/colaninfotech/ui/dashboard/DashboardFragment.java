package com.example.colaninfotech.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colaninfotech.Comment;
import com.example.colaninfotech.CommentAdapter;
import com.example.colaninfotech.ContactsAdapter;
import com.example.colaninfotech.Data;
import com.example.colaninfotech.R;
import com.example.colaninfotech.SQLite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private TextView name,fullname,desc,lang,type;
    private ImageView imageView;
    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private List<Comment>comments;
    private CommentAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        name = root.findViewById(R.id.textView9);
        fullname = root.findViewById(R.id.textView10);
        desc = root.findViewById(R.id.textView11);
        lang = root.findViewById(R.id.textView12);
        type = root.findViewById(R.id.textView13);

        recyclerView = root.findViewById(R.id.rec);

        mLayoutManager = new LinearLayoutManager(getActivity());





        imageView = root.findViewById(R.id.imageView);




        Bundle bundle = this.getArguments();

        if(bundle != null){

            Data s= (Data) bundle.getSerializable("user");

            name.setText(s.getName());
            fullname.setText(s.getFull_name());
            desc.setText(s.getDescription());
            lang.setText(s.getLanguage());
            type.setText(s.getOwner().getType());

            Picasso.with(getContext())
                    .load(s.getOwner().getAvatar_url())
                    .into(imageView);

            SQLite sqLite = new SQLite(getContext());

            comments = sqLite.getcomment(s.getId());

            if(comments!=null){
                mAdapter = new CommentAdapter(getActivity(), comments);
                //   mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }








        }


        return root;
    }


}


