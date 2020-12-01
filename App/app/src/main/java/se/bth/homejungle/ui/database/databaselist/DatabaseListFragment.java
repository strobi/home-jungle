package se.bth.homejungle.ui.database.databaselist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import se.bth.homejungle.R;
import se.bth.homejungle.adapter.DatabaseAdapter;
import se.bth.homejungle.storage.entity.Plant;
import se.bth.homejungle.ui.plants.yourplants.YourPlantsViewModel;


public class DatabaseListFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseListViewModel databaseListViewModel;
    YourPlantsViewModel yourPlantsViewModel;
    int source;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseListViewModel = new ViewModelProvider(this).get(DatabaseListViewModel.class);

        //TODO: check if this works the correct way
        yourPlantsViewModel = new ViewModelProvider(this).get(YourPlantsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_database_list, container, false);

        recyclerView = root.findViewById(R.id.idRecyclerView);
        source = DatabaseListFragmentArgs.fromBundle(getArguments()).getSource();

        final DatabaseAdapter adapter = new DatabaseAdapter(new DatabaseAdapter.PlantDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseListViewModel.getSpecies().observe(getViewLifecycleOwner(), species -> {
            Log.v("Database:", "Species: " + species.size());
            adapter.submitList(species);
        });
        return root;
    }

    public int getSource(){
        return this.source;
    }

    public void insertToOwnPlants(long categoryId, String description){
        databaseListViewModel.insertToOwnPlants(categoryId, description);
    }
}