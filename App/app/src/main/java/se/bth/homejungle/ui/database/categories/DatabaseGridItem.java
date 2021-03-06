package se.bth.homejungle.ui.database.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import se.bth.homejungle.R;
import se.bth.homejungle.storage.AppDatabase;
import se.bth.homejungle.storage.entity.SpeciesCategory;
import se.bth.homejungle.ui.Source;

/**
 * The DatabaseGridItem is used to bind the data of a category to the recyclerview in DatabaseGridFragment.
 * If a category is clicked, it navigates to the corresponding DatabaseListFragment.
 */

public class DatabaseGridItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView category_img;
    TextView category_name;
    long category_id;
    Source source;

    public DatabaseGridItem(@NonNull View itemView) {
        super(itemView);
        category_img = itemView.findViewById(R.id.giveaway_img);
        category_name = itemView.findViewById(R.id.species_name);
        itemView.setOnClickListener(this);
    }

    public void bind(SpeciesCategory currentCategory, Source source){
        category_name.setText(currentCategory.getName());
        category_id = currentCategory.getId();
        category_img.setImageURI(AppDatabase.getUriForFileName(currentCategory.getImage()));
        this.source = source;
    }

    public static DatabaseGridItem create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.database_grid_item, parent, false);
        return new DatabaseGridItem(view);
    }

    @Override
    public void onClick(View view) {
        NavDirections action = DatabaseCategoriesFragmentDirections.openCategory(source)
                .setCategoryName(category_name.getText().toString())
                .setCategoryId(category_id);
        Navigation.findNavController(view).navigate(action);
    }
}
