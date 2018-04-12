package com.example.saksham.blackoutrestuarant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.item.CartDetail;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.US;

/**
 * Created by saksham_ on 08-Apr-18.
 */

class CartViewHolder extends RecyclerView.ViewHolder{

    public TextView text_cart_name,txt_price;
    public ImageView img_cart_count;

    public CartViewHolder(View itemView)
    {
        super(itemView);
        text_cart_name=(TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price=(TextView)itemView.findViewById(R.id.cart_item_Price);
        img_cart_count=(ImageView)itemView.findViewById(R.id.cart_item_count);
    }

}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<CartDetail> listData=new ArrayList<>();
    private Context context;

    public CartAdapter(List<CartDetail> listData, Context context)
    {
        this.listData=listData;
        this.context=context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        TextDrawable drawable=TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);
        Locale locale =new Locale("en","IN");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        double price =((listData.get(position).getPrice())*(listData.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        holder.text_cart_name.setText(listData.get(position).getProductName()+" ("+listData.get(position).getSize()+")");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
