package se.bth.homejungle.ui.plants.yourplants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import se.bth.homejungle.R;
import se.bth.homejungle.adapter.YourPlantsListAdapter;

public class YourPlantsFragment extends Fragment {

    ListView listView;
    ImageButton add_button;


    private YourPlantsViewModel plantViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        plantViewModel = new ViewModelProvider(this).get(YourPlantsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_your_plants, container, false);

        add_button = root.findViewById(R.id.btn_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.navigation_database);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.idRecyclerView);
        final YourPlantsListAdapter adapter = new YourPlantsListAdapter(new YourPlantsListAdapter.PlantDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavDirections action = YourPlantsFragmentDirections.openSinglePlantView();
             //   OpenSinglePlantViewAction action = YourPlantsFragmentDirections.OpenSinglePlantView();
//                HomeFragment parentFragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.homeFragment);

            //    Navigation.findNavController(root).navigate(R.id.testPlantFragment);
                NavController navController = Navigation.findNavController(root);
                Navigation.findNavController(root).navigate(action);

                Toast.makeText(getActivity(), "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        WriteBtn(root);
        ReadBtn(root);

        return root;
    }

    // write text to file
    public void WriteBtn(View v) {
        // add-write text into file
        try {
            FileOutputStream fileout = getActivity().openFileOutput("your_plants.txt", getActivity().MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("name");
            outputWriter.close();

            //display file saved message
            /*Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReadBtn(View v) {
     //   TextView textmsg = v.findViewById(R.id.textmsg);
        //reading text from file
        try {
            FileInputStream fileIn = getActivity().openFileInput("your_plants.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
       //     textmsg.setText(s);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}