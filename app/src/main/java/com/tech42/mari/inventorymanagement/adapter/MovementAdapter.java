package com.tech42.mari.inventorymanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.model.MovementReport;

import java.util.ArrayList;

/**
 * Created by mari on 2/2/17.
 */

public class MovementAdapter extends RecyclerView.Adapter<MovementAdapter.MyViewHolder> {

    private ArrayList<MovementReport> reports;
    private Context mycontext;

    public MovementAdapter(Context context, ArrayList<MovementReport> reports) {
        this.mycontext = context;
        this.reports = reports;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.item_movementreport, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MovementReport reportobject = reports.get(position);
        holder.date.setText(reportobject.getDate());
        holder.code.setText(reportobject.getCode());
        holder.name.setText(reportobject.getName());
        holder.in.setText(reportobject.getIn());
        holder.out.setText(reportobject.getOut());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }


    /**
     * Created by mari on 1/27/17.
     */

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date, code, name, in, out;

        public MyViewHolder(View itemView) {
            super(itemView);
            code = (TextView) itemView.findViewById(R.id.codetext);
            name = (TextView) itemView.findViewById(R.id.nametext);
            date = (TextView) itemView.findViewById(R.id.datetext);
            in = (TextView) itemView.findViewById(R.id.intext);
            out = (TextView) itemView.findViewById(R.id.outtext);
        }
    }
}
