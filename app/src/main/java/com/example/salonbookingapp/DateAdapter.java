package com.example.salonbookingapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private final List<String> dates; // 日付のリスト
    private int selectedPosition = -1; // 選択された日付の位置（初期値は選択なし）
    private final OnDateClickListener listener; // 日付がクリックされたときのリスナー

    // 日付クリック用インターフェース
    public interface OnDateClickListener {
        void onDateClick(String date);
    }

    // コンストラクタ
    public DateAdapter(List<String> dates, OnDateClickListener listener) {
        this.dates = dates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String date = dates.get(position);
        holder.dateText.setText(date); // 日付のテキストを設定

        // 選択された日付をハイライト
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_light));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
        }

        // クリックイベントの設定
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(previousPosition); // 前の選択状態をリセット
            notifyItemChanged(selectedPosition); // 新しい選択状態を反映
            listener.onDateClick(date); // リスナーで日付クリックを通知
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    // ViewHolderクラス
    public static class DateViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateText;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.date_text);
        }
    }
}
