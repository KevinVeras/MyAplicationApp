package com.example.myapplicationapp.ui.dispensador;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DispensadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispensadorFragment extends Fragment {

    View vista;
    EditText nombre,tipo,edad;
    Button btnRegistrar,btnCancel;
    CheckBox rabia,distemper,parvovirus;

    TextView tv_valor,valopm,valopme,valorgrande,valorgigante;
    Spinner spinner;
    DatabaseReference dbref;
    ValueEventListener listener;
    ArrayList<String> list,list2;
    ArrayAdapter<String> adapter,adapter2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DispensadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DispensadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DispensadorFragment newInstance(String param1, String param2) {
        DispensadorFragment fragment = new DispensadorFragment();
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
        vista=inflater.inflate(R.layout.fragment_add, container, false);



        return vista;

    }
    public void datosSelect(){
        listener=dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata: snapshot.getChildren())
                    list.add(mydata.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void vacunas(){
        if (rabia.isChecked()==true)
            rabia.setText("Rabia");

        else
            rabia.setText("");

        if (distemper.isChecked()==true)
            distemper.setText("Distemper");
        else
            distemper.setText("");

        if (parvovirus.isChecked()==true)
            parvovirus.setText("Parvovirus");
        else
            parvovirus.setText("");
    }
    private void insertaDatos(){

        vacunas();

        Map<String,Object> map=new HashMap<>();
        map.put("nombre",nombre.getText().toString());
        map.put("tipo",tipo.getText().toString());
        map.put("edad",edad.getText().toString());

        map.put("vDistemper",distemper.getText().toString());
        map.put("vParvovirus",parvovirus.getText().toString());
        map.put("vRabia",rabia.getText().toString());

        map.put("tamanio",tv_valor.getText().toString());
        map.put("tmini",valopm.getText().toString());
        map.put("tmediano",valopme.getText().toString());
        map.put("tgrande",valorgrande.getText().toString());
        map.put("tgigante",valorgigante.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("mascotag").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(),"Se registro correctamenta",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Error No se registro ",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}