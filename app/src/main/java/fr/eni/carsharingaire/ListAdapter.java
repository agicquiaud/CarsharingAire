package fr.eni.carsharingaire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.carsharingaire.pojo.Parking;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Parking> parkingsList;
    private List<Parking> filteredParkingList;
    private OnClickAireListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView address;
        public TextView description;
        public TextView site;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            address = view.findViewById(R.id.address);
            description = view.findViewById(R.id.description);
            site = view.findViewById(R.id.site);
        }
    }


    public ListAdapter(Context context, List<Parking> parkingsList, OnClickAireListener listener) {
        this.context = context;
        this.parkingsList = parkingsList;
        this.filteredParkingList = parkingsList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Parking parking = filteredParkingList.get(position);
        holder.name.setText(parking.getNom());
        holder.address.setText(parking.getAdresse().concat(" ").concat(parking.getCodePostale().concat(" ").concat(parking.getCommune())));
        holder.description.setText(parking.getPresentation());
        holder.site.setText(parking.getSiteWeb());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAireClicked(parkingsList,parking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredParkingList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredParkingList = parkingsList;
                } else {
                    List<Parking> filteredList = new ArrayList<>();
                    for (Parking row : parkingsList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNom().toLowerCase().contains(charString.toLowerCase()) || row.getAdresse().toLowerCase().contains(charString.toLowerCase()) || row.getCommune().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredParkingList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredParkingList;
                filterResults.count = filteredParkingList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredParkingList = (ArrayList<Parking>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnClickAireListener{
        void onAireClicked(List<Parking> listParking, Parking park);
    }
}
