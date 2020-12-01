package se.bth.homejungle.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Objects;

import se.bth.homejungle.storage.entity.PlantWithSpecies;
import se.bth.homejungle.ui.plants.yourplants.PlantListItem;

public class YourPlantsAdapter extends ListAdapter<PlantWithSpecies, PlantListItem> {

    public YourPlantsAdapter(@NonNull DiffUtil.ItemCallback<PlantWithSpecies> diffCallback) {
        super(diffCallback);
    }

    public PlantWithSpecies getByPosition(int position) {
        return getCurrentList().get(position);
    }

    @Override
    public PlantListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return PlantListItem.create(parent);
    }

    @Override
    public void onBindViewHolder(PlantListItem holder, int position) {
        PlantWithSpecies currentPlant = getItem(position);
        holder.bind(currentPlant);
    }

    public static class PlantDiff extends DiffUtil.ItemCallback<PlantWithSpecies> {
        @Override
        public boolean areItemsTheSame(@NonNull PlantWithSpecies oldItem, @NonNull PlantWithSpecies newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlantWithSpecies oldItem, @NonNull PlantWithSpecies newItem) {
            return Objects.equals(oldItem, newItem);
        }
    }
}