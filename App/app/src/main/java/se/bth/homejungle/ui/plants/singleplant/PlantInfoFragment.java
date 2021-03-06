package se.bth.homejungle.ui.plants.singleplant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import se.bth.homejungle.R;
import se.bth.homejungle.ui.Source;

/**
 * The PlantInfoFragment is displayed in the Viewpager of the SinglePlantFragment.
 * It gives information about how to start with a plant.
 */

public class PlantInfoFragment extends Fragment {
    long speciesId;
    TextView information;
    Source source;

    SinglePlantViewModel singlePlantViewModel;

    public PlantInfoFragment(long speciesId, Source source) {
        this.speciesId = speciesId;
        this.source = source;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        singlePlantViewModel = new ViewModelProvider(requireActivity()).get(SinglePlantViewModel.class);

        View root = inflater.inflate(R.layout.fragment_plant_info, container, false);
        information = root.findViewById(R.id.moreInformation);

        singlePlantViewModel.getSpeciesById(speciesId).observe(getViewLifecycleOwner(), species -> {
            information.setText(species.getDescription());
            int waterAmount = (int) species.getWater();
            switch (waterAmount) {
                case 4:
                    ImageView water4 = root.findViewById(R.id.water4);
                    water4.setVisibility(View.VISIBLE);
                    ImageView care4 = root.findViewById(R.id.heart4);
                    care4.setVisibility(View.VISIBLE);
                case 3:
                    ImageView water3 = root.findViewById(R.id.water3);
                    water3.setVisibility(View.VISIBLE);
                    ImageView care3 = root.findViewById(R.id.heart3);
                    care3.setVisibility(View.VISIBLE);
                case 2:
                    ImageView water2 = root.findViewById(R.id.water2);
                    water2.setVisibility(View.VISIBLE);
                    ImageView care2 = root.findViewById(R.id.heart2);
                    care2.setVisibility(View.VISIBLE);
                default:
                    break;
            }
            int sunAmount = (int) species.getSun();
            switch (sunAmount) {
                case 4:
                    ImageView sun4 = root.findViewById(R.id.sun4);
                    sun4.setVisibility(View.VISIBLE);
                case 3:
                    ImageView sun3 = root.findViewById(R.id.sun3);
                    sun3.setVisibility(View.VISIBLE);
                case 2:
                    ImageView sun2 = root.findViewById(R.id.sun2);
                    sun2.setVisibility(View.VISIBLE);
                default:
                    break;
            }
        });

        return root;
    }

}