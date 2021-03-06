package se.bth.homejungle.ui.plants.yourplants;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import se.bth.homejungle.R;
import se.bth.homejungle.adapter.YourPlantsAdapter;
import se.bth.homejungle.storage.entity.view.PlantWithSpecies;
import se.bth.homejungle.ui.Source;
import se.bth.homejungle.ui.plants.HomeFragmentDirections;

/**
 * YourPlantsFragment contains a RecyclerView which contains all plants that are as own plants
 * in the local database. The recyclerview is attached with the YourPlantsAdapter.
 *
 * Furthermore, the recyclerview has an itemToucher, so that a swipe to left will call the
 * delete-function for the listitem.
 */

public class YourPlantsFragment extends Fragment {

    TextView noPlant;
    Button noPlantBtn;
    ImageButton add_button;
    private YourPlantsViewModel plantViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        plantViewModel = new ViewModelProvider(this).get(YourPlantsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_your_plants, container, false);

        noPlant = root.findViewById(R.id.tv_no_plant);
        noPlantBtn = root.findViewById(R.id.btn_no_plant);
        noPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = HomeFragmentDirections.homeToDatabase().setSource(Source.YOURPLANTS);
                Navigation.findNavController(root).navigate(action);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.idRecyclerView);
        final YourPlantsAdapter adapter = new YourPlantsAdapter(new YourPlantsAdapter.PlantDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        plantViewModel.getPlantsWithSpecies().observe(getViewLifecycleOwner(), plants -> {
            if(plants.size() < 1){
                noPlant.setVisibility(View.VISIBLE);
                noPlantBtn.setVisibility(View.VISIBLE);
            }
            Log.v("Database", "Your plants: " + plants.size());
            adapter.submitList(plants);
        });

        add_button = root.findViewById(R.id.btn_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = HomeFragmentDirections.homeToDatabase().setSource(Source.YOURPLANTS);
                Navigation.findNavController(root).navigate(action);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            PlantWithSpecies deleteItem;

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int itemPosition = viewHolder.getAdapterPosition();
                deleteItem = adapter.getByPosition(itemPosition);
                plantViewModel.delete(deleteItem.getPlant());
                showUndoSnackbar();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX,
                        dY, actionState, isCurrentlyActive);
            }

            public void showUndoSnackbar(){
                Snackbar snackbar = Snackbar.make(recyclerView, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.snack_bar_undo, v->undoDelete());
                snackbar.show();
            }

            public void undoDelete(){
                plantViewModel.insert(deleteItem.getPlant());
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }

}