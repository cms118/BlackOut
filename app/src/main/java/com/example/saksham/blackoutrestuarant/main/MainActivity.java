package com.example.saksham.blackoutrestuarant.main;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.MainHomeMenu_Adapter;
import com.example.saksham.blackoutrestuarant.adapter.MenuViewHolder;
import com.example.saksham.blackoutrestuarant.adapter.SwipeAdapter;
//import com.example.saksham.blackoutrestuarant.databinding.ActivityMainBinding;
import com.example.saksham.blackoutrestuarant.design.Cart;
import com.example.saksham.blackoutrestuarant.design.HomeMenuData;
import com.example.saksham.blackoutrestuarant.design.MainMenuSelectionFrag;
import com.example.saksham.blackoutrestuarant.design.Menu_item;
import com.example.saksham.blackoutrestuarant.interfaces.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
/*
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }
*/

    Toolbar toolbar;

    ViewPager viewPager;
    LinearLayout sliderDots;
    public int dotCounts;
    public ImageView[] dots;
   // public ActivityMainBinding mBinding;

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth au;
    public static String phoneNumber;

    FirebaseRecyclerAdapter<HomeMenuData,MenuViewHolder> adapter;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_btn, menu);

        MenuItem getItem = menu.findItem(R.id.open_Cart);
        if (getItem != null) {

            getItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this,Cart.class));
                    return true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("HomeMenuData");

        au=FirebaseAuth.getInstance();
        phoneNumber=au.getCurrentUser().getPhoneNumber();


        toolbar=(Toolbar)findViewById(R.id.myTool);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);

     /*   FloatingActionButton fab = findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this,Cart.class));
            }
        });

*/
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

/*
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        viewPager=mBinding.viewPager;
        sliderDots=mBinding.SliderDots;
        SwipeAdapter swipeAdapter=new SwipeAdapter(this);
        viewPager.setAdapter(swipeAdapter);
        dotCounts=swipeAdapter.getCount();
        dots=new ImageView[dotCounts];

        for(int i=0;i<dotCounts;i++)
        {
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for(int i=0;i<dotCounts;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

*/
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


       // initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
     //   recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.i("cms","abc");
        loadMenu();
        Log.i("cms","def");



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,recyclerView, new ItemClickListener() {

            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
               // Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                //        Toast.LENGTH_SHORT).show();

                if(position==0)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",0);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",1);
                    startActivity(intent);
                }
                else if(position==2)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",2);
                    startActivity(intent);
                }
                else if(position==3)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",3);
                    startActivity(intent);
                }
                else if(position==4)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",4);
                    startActivity(intent);
                }
                else if(position==5)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",5);
                    startActivity(intent);
                }
                else if(position==6)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",6);
                    startActivity(intent);
                }
                else if(position==7)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",7);
                    startActivity(intent);
                }
                else if(position==8)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",8);
                    startActivity(intent);
                }
                else if(position==9)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",9);
                    startActivity(intent);
                }
                else if(position==10)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",10);
                    startActivity(intent);
                }
                else if(position==11)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",11);
                    startActivity(intent);
                }
                else if(position==12)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",12);
                    startActivity(intent);
                }
                else if(position==13)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",13);
                    startActivity(intent);
                }
                else if(position==14)
                {
                    Intent intent=new Intent(getApplicationContext(),MainMenuSelectionFrag.class);
                    intent.putExtra("ID",14);
                    startActivity(intent);
                }

            }

        }));

      //  Timer timer=new Timer();
      //  timer.scheduleAtFixedRate(new myTimerTask(),4000,4000);

    }


    void loadMenu()
    {
      //  databaseReference = databaseReference.child("01");
        //Query query=databaseReference.orderByKey();
        FirebaseRecyclerOptions option=new FirebaseRecyclerOptions.Builder<HomeMenuData>().setQuery(databaseReference,HomeMenuData.class).build();
        adapter=new FirebaseRecyclerAdapter<HomeMenuData, MenuViewHolder>(option) {

            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_card, parent, false);
                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull HomeMenuData model) {

                holder.textView.setText(model.getName());
                Log.i("cms","pqr");
                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imageView);


            }
        };
        Log.i("cms","xyz");
        recyclerView.setAdapter(adapter);
        Log.i("cms","xyz1");
    }

    public class myTimerTask extends TimerTask{

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem()==0)
                        viewPager.setCurrentItem(1);
                    else if(viewPager.getCurrentItem()==1)
                        viewPager.setCurrentItem(2);
                    else
                        viewPager.setCurrentItem(0);

                }
            });
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

