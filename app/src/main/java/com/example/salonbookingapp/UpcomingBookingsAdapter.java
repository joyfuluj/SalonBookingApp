package com.example.salonbookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpcomingBookingsAdapter extends RecyclerView.Adapter<UpcomingBookingsAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;

    public UpcomingBookingsAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.textViewSalonName.setText(booking.getSalonName());
        holder.textViewMenu.setText("Menu: " + booking.getMenu());
        holder.textViewStylist.setText("Stylist: " + booking.getStylist());
        holder.textViewDateTime.setText(booking.getDate() + " " + booking.getTime());

        holder.buttonViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingDetailActivity.class);
            intent.putExtra(Constants.EXTRA_BOOKING_SALON, booking.getSalonName());
            intent.putExtra(Constants.EXTRA_BOOKING_MENU, booking.getMenu());
            intent.putExtra(Constants.EXTRA_BOOKING_STYLIST, booking.getStylist());
            intent.putExtra(Constants.EXTRA_BOOKING_DATE, booking.getDate());
            intent.putExtra(Constants.EXTRA_BOOKING_TIME, booking.getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSalonName, textViewMenu, textViewStylist, textViewDateTime;
        Button buttonViewDetails;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSalonName = itemView.findViewById(R.id.text_view_salon_name);
            textViewMenu = itemView.findViewById(R.id.text_view_menu);
            textViewStylist = itemView.findViewById(R.id.text_view_stylist);
            textViewDateTime = itemView.findViewById(R.id.text_view_date_time);
            buttonViewDetails = itemView.findViewById(R.id.button_view_details);
        }
    }
}
