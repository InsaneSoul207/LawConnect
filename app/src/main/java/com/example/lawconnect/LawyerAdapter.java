package com.example.lawconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LawyerAdapter extends RecyclerView.Adapter<LawyerAdapter.LawyerViewHolder> {
    private List<Lawyer> lawyers;
    private OnLawyerClickListener listener;

    public interface OnLawyerClickListener {
        void onLawyerClick(Lawyer lawyer);
    }

    public LawyerAdapter(List<Lawyer> lawyers, OnLawyerClickListener listener) {
        this.lawyers = lawyers;
        this.listener = listener;
    }

    @Override
    public LawyerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lawyer, parent, false);
        return new LawyerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LawyerViewHolder holder, int position) {
        Lawyer lawyer = lawyers.get(position);
        holder.name.setText(lawyer.getName());
        holder.expertise.setText("Expertise: " + lawyer.getExpertise());
        holder.price.setText("Price: ₹" + lawyer.getPrice());

        holder.itemView.setOnClickListener(v -> listener.onLawyerClick(lawyer));
    }

    @Override
    public int getItemCount() {
        return lawyers.size();
    }

    static class LawyerViewHolder extends RecyclerView.ViewHolder {
        TextView name, expertise, price;

        public LawyerViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lawyer_name);
            expertise = itemView.findViewById(R.id.lawyer_expertise);
            price = itemView.findViewById(R.id.lawyer_price);
        }
    }
}