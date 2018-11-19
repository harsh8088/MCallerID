package com.example.harsh.mcallerid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CallDetailsAdapter extends RecyclerView.Adapter<CallDetailsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CallerModel> arrayList;
    ClickListener listener;

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void onListClick(int index, ArrayList<CallerModel> item);
    }

    CallDetailsAdapter(Context context) {
        this.mContext = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liststyle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CallerModel item = arrayList.get(position);

        holder.tvName.setText(item.getConNames());
        holder.tvNumber.setText(item.getConNumbers());
        holder.tvTime.setText("( " + item.getConTime() + "sec )");
        holder.tvDate.setText(item.getConDate());
        holder.tvType.setText("( " + item.getConType() + " )");


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setList(List<CallerModel> recentList) {
        arrayList.clear();
        arrayList.addAll(recentList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNumber;
        TextView tvTime;
        TextView tvDate;
        TextView tvType;

        ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvNameMain);
            tvNumber = (TextView) view.findViewById(R.id.tvNumberMain);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvType = (TextView) view.findViewById(R.id.tvType);

        }


    }


}
