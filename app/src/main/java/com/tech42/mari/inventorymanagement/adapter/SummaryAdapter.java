package com.tech42.mari.inventorymanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech42.mari.inventorymanagement.R;
import com.tech42.mari.inventorymanagement.model.SummaryReport;

import java.util.ArrayList;

/**
 * Created by mari on 2/4/17.
 */

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {

    private ArrayList<SummaryReport> reports;
    private Context mycontext;

    public SummaryAdapter(Context context, ArrayList<SummaryReport> reports) {
        this.mycontext = context;
        this.reports = reports;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mycontext).inflate(R.layout.item_summaryreport, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SummaryReport reportobject = reports.get(position);
        holder.code.setText(reportobject.getCode());
        holder.name.setText(reportobject.getName());
        holder.in.setText(String.valueOf(reportobject.getIn()));
        holder.out.setText(String.valueOf(reportobject.getOut()));
        holder.outvalue.setText(String.valueOf(reportobject.getOutvalue()));
        holder.invalue.setText(String.valueOf(reportobject.getInvalue()));
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }


    /**
     * Created by mari on 1/27/17.
     */

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView code, name, in, out , invalue , outvalue;

        public MyViewHolder(View itemView) {
            super(itemView);
            code = (TextView) itemView.findViewById(R.id.codetext);
            name = (TextView) itemView.findViewById(R.id.nametext);
            in = (TextView) itemView.findViewById(R.id.intext);
            out = (TextView) itemView.findViewById(R.id.outtext);
            invalue = (TextView) itemView.findViewById(R.id.invaluetext);
            outvalue = (TextView) itemView.findViewById(R.id.outvaluetext);
        }
    }
}
