package com.example.saksham.blackoutrestuarant.design;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.adapter.CartAdapter;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.item.CartDetail;
import com.example.saksham.blackoutrestuarant.item.OrderRequest;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.pattern.FirebaseSingleDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.Request;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView textTotalPrice;
    Button placeOrder;

    List<CartDetail> cart=new ArrayList<>();

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseDatabase= FirebaseSingleDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("OrderRequest");

        recyclerView=(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textTotalPrice=(TextView)findViewById(R.id.totalPrice);

        placeOrder=(Button)findViewById(R.id.btnPlaceOrder);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialog();

            }
        });

        cart=new Database(this).getCart();
        loadListFood();
    }

    private void showAlertDialog(){

        LayoutInflater inflater = getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.take_address, null);

        final EditText add_name = alertLayout.findViewById(R.id.add_name);
        final EditText add_phone = alertLayout.findViewById(R.id.add_phone);
        final EditText add_pincode = alertLayout.findViewById(R.id.add_pincode);
        final EditText add_flat = alertLayout.findViewById(R.id.add_flat);
        final EditText add_area = alertLayout.findViewById(R.id.add_area);
        final EditText add_landmark = alertLayout.findViewById(R.id.add_landmark);
        final EditText add_city = alertLayout.findViewById(R.id.add_city);
        final EditText add_state = alertLayout.findViewById(R.id.add_state);

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Cart.this,R.style.DialogFragmentStyle);
        alertDialog.setTitle("One More Step");
        alertDialog.setMessage("Enter your Address");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                OrderRequest orderRequest=new OrderRequest(
                        add_phone.getText().toString(),
                        add_name.getText().toString(),
                        add_flat.getText().toString(),
                        add_area.getText().toString(),
                        add_landmark.getText().toString(),
                        add_pincode.getText().toString(),
                        add_city.getText().toString(),
                        add_state.getText().toString(),
                        cart
                );
                databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(orderRequest);

                new Database(getBaseContext()).cleanCart();

                Toast.makeText(Cart.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alertDialog.setView(alertLayout);

        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.show();

    }

    private void loadListFood() {


        cartAdapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(cartAdapter);
        double total=0;
        for(CartDetail cartDetail:cart)
        {
            total+=cartDetail.getQuantity()*cartDetail.getPrice();
        }

        Locale locale =new Locale("en","IN");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);

        textTotalPrice.setText(fmt.format(total));

    }
}
