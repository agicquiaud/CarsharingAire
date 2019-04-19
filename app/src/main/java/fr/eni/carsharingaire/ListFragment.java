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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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
        mAdapter = new ListAdapter(getActivity(), parkingsList, new ListAdapter.OnClickAireListener() {
            @Override
            public void onAireClicked(List<Parking> listParking, Parking park) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Carte");
                loadFragment(MapFragment.newInstance(parkingsList, park,null));

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems();

        EditText inputSearch = (EditText) view.findViewById(R.id.search_box);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

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





}
