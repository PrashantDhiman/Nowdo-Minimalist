package com.prashantdhiman.todominimalist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<ItemModel> listItems;

    private onCardClickListener mListener;

    public interface onCardClickListener {
        void onCardClick(int position);

        void onChkBtnClick(int position);
    }

    public void setOnCardClickListener(onCardClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(Context context, List<ItemModel> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview, parent, false);

        return new ViewHolder(v, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ItemModel itemModelObj = listItems.get(position);
        holder.todoTaskTextView.setText(itemModelObj.getTodoTaskText());

        int color = itemModelObj.getColor();
        int status = itemModelObj.getStatus();

        if (status == 0) {
            if (color == 1) {
                holder.chkBtn.setBackgroundResource(R.drawable.chk_btn_bndry_blue);
            } else if (color == 2) {
                holder.chkBtn.setBackgroundResource(R.drawable.chk_btn_bndry_red);
            } else {
                holder.chkBtn.setBackgroundResource(R.drawable.chk_btn_bndry_green);
            }
        } else {
            holder.chkBtn.setBackgroundResource(R.drawable.tick_inside_circle);
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView todoTaskTextView;
        Button chkBtn;
        CardView taskCardView;

        public ViewHolder(@NonNull final View itemView, final onCardClickListener listener) {
            super(itemView);

            todoTaskTextView = itemView.findViewById(R.id.todoTaskTextView);
            chkBtn = itemView.findViewById(R.id.chkBtn);
            taskCardView = itemView.findViewById(R.id.taskCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCardClick(position);
                        }
                    }
                }
            });

            chkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onChkBtnClick(position);
                        }
                    }
                }
            });

        }

    }

}
