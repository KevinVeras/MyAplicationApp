package com.example.myapplicationapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationapp.R;
import com.example.myapplicationapp.databinding.FragmentGalleryBinding;
import com.example.myapplicationapp.ui.dispensador.AddFragment;
import com.example.myapplicationapp.ui.dispensador.DispensadorFragment;
import com.example.myapplicationapp.ui.dispensador.MainAdapter;
import com.example.myapplicationapp.ui.dispensador.MainModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    RecyclerView recyclerView;
    MainAdapter mainAdapter;

    FloatingActionButton floatingActionButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView =root.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mascotag"), MainModel.class)
                        .build();
        mainAdapter=new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton=root.findViewById(R.id.floatingActionButton);

        // cambiar de frgament
        Fragment newFragment = new AddFragment();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Agregar reg",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frmas,newFragment).commit();

                floatingActionButton.setVisibility(View.GONE);
                //getActivity().getSupportFragmentManager().beginTransaction().remove(newFragment).commit();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}