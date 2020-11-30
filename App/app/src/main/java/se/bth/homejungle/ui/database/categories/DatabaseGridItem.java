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
import se.bth.homejungle.storage.entity.Species;


public class DatabaseGridItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView category_img;
    TextView category_name;
    long category_id;

    public DatabaseGridItem(@NonNull View itemView) {
        super(itemView);
        category_img = itemView.findViewById(R.id.category_img);
        category_name = itemView.findViewById(R.id.category_name);
        itemView.setOnClickListener(this);
    }

    public void bind(Species currentCategory){
        category_name.setText(currentCategory.getName());
        category_id = currentCategory.getId();
    }

    public static DatabaseGridItem create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.database_grid_item, parent, false);
        return new DatabaseGridItem(view);
    }

    @Override
    //TODO: implement category ID
    public void onClick(View view) {
        NavDirections action = DatabaseFragmentDirections.openCategory(1);
        Navigation.findNavController(view).navigate(action);
    }
}
