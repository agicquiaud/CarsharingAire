package fr.eni.carsharingaire;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import fr.eni.carsharingaire.pojo.Parking;
import fr.eni.carsharingaire.pojo.Records;


public class ListFragment extends Fragment {

    private static final String TAG = ListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Parking> parkingsList;
    private ListAdapter mAdapter;
    static private List<Parking> list = null;
    static private ActionBar toolbar;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(List<Parking> parkings) {
        list = parkings;
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        toolbar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        recyclerView = view.findViewById(R.id.recycler_view);
        parkingsList = new ArrayList<>();
        mAdapter = new ListAdapter(getActivity(), parkingsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems();

        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fetchStoreItems() {
        //List<Records> items = new ArrayList<Records>();

        parkingsList.clear();
        parkingsList.addAll(list);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
    }



    class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
        private Context context;
        private List<Parking> parkingsList;

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

                view.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        toolbar.setTitle("Carte");
                        loadFragment(MapFragment.newInstance(list));
                    }
                });
            }
        }


        public ListAdapter(Context context, List<Parking> parkingsList) {
            this.context = context;
            this.parkingsList = parkingsList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Parking parking = parkingsList.get(position);
            holder.name.setText(parking.getNom());
            holder.address.setText(parking.getAdresse()+' '+parking.getCodePostale()+' '+parking.getCommune());
            holder.description.setText(parking.getPresentation());
            holder.site.setText(parking.getSiteWeb());
        }

        @Override
        public int getItemCount() {
            return parkingsList.size();
        }
    }

}
