package com.tech42.mari.inventorymanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.model.Inventory;

import java.util.ArrayList;


/**
 * Created by mari on 1/27/17.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {
    ArrayList<Inventory> inventories;
    Context mycontext;

    public InventoryAdapter(Context context, ArrayList<Inventory> inventories) {
        this.mycontext = context;
        this.inventories = inventories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.item_inventory, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Inventory inventoryobject = inventories.get(position);
        holder.code.setText(inventoryobject.getCode());
        holder.name.setText(inventoryobject.getName());
        holder.quantity.setText("0 " + inventoryobject.getUnit() + "s");
        holder.totalValue.setText((int) inventoryobject.getTotalvalue());
        holder.category.setText(inventoryobject.getCategory());
    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView code, name, quantity, totalValue, category;

        public MyViewHolder(View itemView) {
            super(itemView);
            code = (TextView) itemView.findViewById(R.id.codetext);
            name = (TextView) itemView.findViewById(R.id.nametext);
            quantity = (TextView) itemView.findViewById(R.id.qtytext);
            totalValue = (TextView) itemView.findViewById(R.id.totaltext);
            category = (TextView) itemView.findViewById(R.id.categorytext);
        }
    }
}
