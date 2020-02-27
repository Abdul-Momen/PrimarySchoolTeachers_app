package com.momen.primaryschoolteachers_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.momen.primaryschoolteachers_app.Model.Log;
import com.momen.primaryschoolteachers_app.R;

import java.util.List;

public class LogAdapter extends  RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private Context context;
    private List<Log> logList;

    public LogAdapter(Context context, List<Log> list) {
        this.context = context;
        this.logList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log log = logList.get(position);

        holder.tvDate.setText(log.getDate());
        holder.presentmale.setText(log.getTotalMalePresent());
        holder.presentfemale.setText(log.getTotalFemalePresent());
        /*holder.textRating.setText(String.valueOf(log.getDate()));
        holder.textYear.setText(String.valueOf(log.getTotalFAbsent()));
        holder.textYear.setText(String.valueOf(log.getTotalMAbsent()));
        holder.textYear.setText(String.valueOf(log.getTotalMalePresent()));
        holder.textYear.setText(String.valueOf(log.getTotalFemalePresent()));*/
//        holder.textYear.setText(String.valueOf(log.getTotalFAbsent()));

    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, presentmale, presentfemale;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            presentmale = itemView.findViewById(R.id.presentmale);
            presentfemale = itemView.findViewById(R.id.presentfemale);
        }
    }
}
