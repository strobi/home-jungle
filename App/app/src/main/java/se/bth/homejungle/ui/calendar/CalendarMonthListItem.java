package se.bth.homejungle.ui.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

import se.bth.homejungle.R;
import se.bth.homejungle.storage.entity.view.CalendarEvent;
import se.bth.homejungle.storage.entity.view.CalendarEventType;

public class CalendarMonthListItem extends RecyclerView.ViewHolder {
    TextView month_name;
    TextView date;
    TextView plant_name;
    TextView plant_desc;
    ImageView icon;
    RadioButton check_button;

    public CalendarMonthListItem(@NonNull View itemView) {
        super(itemView);
        month_name = itemView.findViewById(R.id.month_name);
        date = itemView.findViewById(R.id.date);
        plant_name = itemView.findViewById(R.id.plant_name);
        plant_desc = itemView.findViewById(R.id.plant_desc);
        icon = itemView.findViewById(R.id.icon);
        check_button = itemView.findViewById(R.id.check_btn);
    }

    public void bind(CalendarEvent calendarEvent, CalendarFragment calendarFragment) {
        month_name.setText(calendarEvent.getDate().getMonth().toString());
        if (calendarEvent.getType() == CalendarEventType.PLANT) {
            icon.setImageResource(R.drawable.ic_flower);
        }
        LocalDate nextWateringDay = calendarEvent.getDate();
        if (nextWateringDay.isAfter(LocalDate.now())) {
            check_button.setVisibility(View.INVISIBLE);
        }
        date.setText("" + nextWateringDay.getDayOfMonth());
        plant_name.setText(calendarEvent.getSpecies().getName());
        plant_desc.setText(calendarEvent.getSourceDescription());
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calendarEvent.getType() == CalendarEventType.PLANT){
                    calendarFragment.createFromFuturePlant(calendarEvent.getSourceId()
                            , calendarEvent.getSourceDescription(), calendarEvent.getSpecies());
                } else if (calendarEvent.getType() == CalendarEventType.WATER){
                    calendarFragment.waterPlant(calendarEvent.getSourceId());
                }
            }
        });
    }

    public static CalendarMonthListItem create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_month_list_item, parent, false);
        return new CalendarMonthListItem(view);
    }


}