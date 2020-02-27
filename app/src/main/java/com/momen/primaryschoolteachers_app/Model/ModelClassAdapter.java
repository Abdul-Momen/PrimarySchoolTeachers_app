package com.momen.primaryschoolteachers_app.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.momen.primaryschoolteachers_app.R;
import com.goodiebag.pinview.Pinview;

import java.util.ArrayList;

public class ModelClassAdapter extends RecyclerView.Adapter<ModelClassAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<ClassModel> dataModelArrayList;




    public ModelClassAdapter(Context ctx, ArrayList<ClassModel> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }
    @Override
    public ModelClassAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.addinfoview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ModelClassAdapter.MyViewHolder holder, int position) {

        /*Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);*/
        /*holder.name.setText(dataModelArrayList.get(position).getName());
        holder.country.setText(dataModelArrayList.get(position).getCountry());
        holder.city.setText(dataModelArrayList.get(position).getCity());*/
        holder.classname.setText(dataModelArrayList.get(position).getClassname());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView classname;

        Pinview pvBoys1,pvGirls1;


        public MyViewHolder(View itemView) {
            super(itemView);

            classname = (TextView) itemView.findViewById(R.id.textView3Id);

            pvBoys1.setPinViewEventListener(new Pinview.PinViewEventListener() {
                @Override
                public void onDataEntered(Pinview pinview, boolean fromUser) {
                    //Make api calls here or what not
                    //Toast.makeText(MainActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
                    //dataModelArrayList.get(getAdapterPosition()).setStudentno(pvBoys1.getValue());

                }
            });


        }

    }
}
