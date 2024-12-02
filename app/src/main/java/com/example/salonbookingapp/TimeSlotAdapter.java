package com.example.salonbookingapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    private final List<String> timeSlots;
    private final Map<String, Boolean> availability;
    private final OnTimeSlotClickListener listener;

    // インターフェース：クリックイベントを処理する
    public interface OnTimeSlotClickListener {
        void onTimeSlotClick(String timeSlot);
    }

    public TimeSlotAdapter(List<String> timeSlots, Map<String, Boolean> availability, OnTimeSlotClickListener listener) {
        this.timeSlots = timeSlots;
        this.availability = availability;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_slot, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        String timeSlot = timeSlots.get(position);
        boolean isAvailable = availability.getOrDefault(timeSlot, false);

        // ⭕（空きあり）または ❌（空きなし）の設定
        holder.timeSlotText.setText(isAvailable ? "⭕" : "❌");
        holder.timeSlotText.setBackgroundColor(isAvailable ? Color.GREEN : Color.RED);
        holder.timeSlotText.setTextColor(Color.WHITE);

        // クリックイベントを設定
        if (isAvailable) {
            holder.timeSlotText.setOnClickListener(v -> listener.onTimeSlotClick(timeSlot));
        }
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    // ビューホルダー
    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        public final TextView timeSlotText;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            timeSlotText = itemView.findViewById(R.id.availability_text);
        }
    }
}
