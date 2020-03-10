package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.ProjectITI.tripsproject.HomeScreen;
import com.ProjectITI.tripsproject.Model.Trip;
import com.ProjectITI.tripsproject.Model.TripDao;
import com.ProjectITI.tripsproject.R;
import com.ProjectITI.tripsproject.ui.Trips_History.Alert.deleteTrip;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final Context context;
    private List<Trip> values;
    ArrayList<String> categories = new ArrayList<String>();


    public RecycleViewAdapter(Context context, List<Trip> values) {
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
    public void onBindViewHolder(@NonNull final RecycleViewAdapter.ViewHolder holder, final int position) {
        holder.tripname.setText(values.get(position).getName());
        holder.from.setText( values.get(position).getFrom());
        holder.to.setText( values.get(position).getTo());
        holder.data.setText(values.get(position).getDate());
        holder.time.setText(values.get(position).getTime());
        /*categories.add("Auto khii joijoijoijiojojoijiojijklkljiojjkjiomobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");*/
        categories = new ArrayList<>();
        categories = values.get(position).getNotes();
        ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        holder.spinner.setPrompt("nada");
        holder.spinner.setSelected(false);
        holder.spinner.setAdapter(aa);

        /*
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, categories);

        holder.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(HomeScreen.context)
                        .setTitle("Notes")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                            }
                        }).create().show();
            }
        });
*/
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
                                //open AddNotes activity + send tripId with the intent
                                break;
                            case R.id.Edit:
                                //open EditTrip activity + send tripId with the intent
                                //handle menu2 click
                                break;
                            case R.id.Cancel:
                                TripDao.cancelTrip(values.get(position).getId());
                            case R.id.Delete:
                                //TripDao.deleteTrip(values.get(position).getId());
                                String trip_id = values.get(position).getId();
                                Intent intent = new Intent(context, deleteTrip.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("trip_id", trip_id);
                                context.startActivity(intent);
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
        public Spinner spinner;
     //   public Button spinner;
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
