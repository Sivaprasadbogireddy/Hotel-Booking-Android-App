package com.example.surface.ramhotelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class InvoiceAdpater extends BaseAdapter {

    Context context;
    List<InvoiceHandler> data;
    LayoutInflater inflater;

    public InvoiceAdpater(Context context, List<InvoiceHandler> data){
        this.context= context;
        this.data= data;
        inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //making invoice
        ViewHolder holder;
        if(convertView==null){
            convertView= inflater.inflate(R.layout.invoice_line, viewGroup, false);
            holder= new ViewHolder();
            holder.Order_Id=(TextView)convertView.findViewById(R.id.oid1);
            holder.date= (TextView)convertView.findViewById(R.id.date);
            holder.service= (TextView)convertView.findViewById(R.id.service);
            holder.charges= (TextView)convertView.findViewById(R.id.charges);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder)convertView.getTag();
        }

        holder.Order_Id.setText(data.get(position).getOrderId());
        holder.date.setText(data.get(position).getDate());
        holder.service.setText(data.get(position).getService());
        holder.charges.setText("$"+data.get(position).getCharges()+"");
        return convertView;
    }

    class ViewHolder{
        TextView Order_Id,date, service, charges;

    }
}
