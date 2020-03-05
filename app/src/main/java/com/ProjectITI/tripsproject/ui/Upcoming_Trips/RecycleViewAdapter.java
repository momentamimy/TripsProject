package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ProjectITI.tripsproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final Context context;
    private List<DataRow> values;

    public RecycleViewAdapter(Context context, List<DataRow> values) {
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater in = LayoutInflater.from(parent.getContext());
        View v = in.inflate(R.layout.upcoming_trips_details, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewAdapter.ViewHolder holder, int position) {
        holder.tripname.setText(values.get(position).getTripName());
        holder.from.setText( values.get(position).getFrom());
        holder.to.setText( values.get(position).getTo());
        holder.data.setText(values.get(position).getData());
        holder.time.setText(values.get(position).getTime());
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, categories);

        holder.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Notes")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Notes:
                                //handle menu1 click
                                break;
                            case R.id.Edit:
                                //handle menu2 click
                                break;
                            case R.id.Cancel:
                                //handle menu3 click
                            case R.id.Delete:
                                //handle menu4click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView tripname;
        public TextView from;
        public TextView to;
        public TextView data;
        public TextView time;
        public ImageView img;
        public Button spinner;

        public TextView buttonViewOption;

        public CardView c;

        public ViewHolder(View v) {
            super(v);
            view = v;
            tripname = v.findViewById(R.id.TripName_id);
            from = v.findViewById(R.id.From_id);
            to = v.findViewById(R.id.To_id);
            data = v.findViewById(R.id.Date_id);
            time = v.findViewById(R.id.time_id);
            img = v.findViewById(R.id.imageView_id);
            c = v.findViewById(R.id.card_id);
            spinner = v.findViewById(R.id.showNotes);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);

        }
    }

}
