package com.example.saksham.blackoutrestuarant.tabfrag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.FireRecyclerAdapter;
import com.example.saksham.blackoutrestuarant.adapter.FoodViewHolder;
import com.example.saksham.blackoutrestuarant.design.FoodDetail;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.example.saksham.blackoutrestuarant.item.Product;
import com.example.saksham.blackoutrestuarant.main.RecyclerTouchListener;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonVegSoupMenuFrag extends Fragment {



    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<Product,FoodViewHolder> adapter;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_non_veg_soup_menu, container, false);

        Bundle args = getArguments();

        final String index =args.getString("index","00");

        firebaseDatabase= FirebaseSingleDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("FoodItemDetail");

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //HomeMenuData homeMenuData=dataSnapshot.getValue(HomeMenuData.class);
                Object val = dataSnapshot.getValue(Object.class);
                Log.i("cms",val.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.VegRecycle);
        //recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter= FireRecyclerAdapter.loadListFood(recyclerView,index,databaseReference,getContext());

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),recyclerView,new ItemClickListener(){

            @Override
            public void onClick(View view, int adapterPosition) {

                //Log.i("cms","bir "+index+(char)(adapterPosition+97));
                //Toast.makeText(getContext(),"hi",Toast.LENGTH_SHORT).show();
                Intent foodDetail=new Intent(getContext(), FoodDetail.class);
                foodDetail.putExtra("FoodId",index+(char)(adapterPosition+97));
                startActivity(foodDetail);
            }
        }));

        return rootView;
    }

}
