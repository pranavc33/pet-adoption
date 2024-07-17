package com.example.furrishta;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BrowseProfilesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetProfileAdapter adapter;
    private List<PetProfile> petProfileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_profiles);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the petProfileList with actual data
        petProfileList = new ArrayList<>();
        petProfileList.add(new PetProfile("Dog", "John Doe", "123-456-7890", "Buddy", "2 years", "Golden Retriever", "Friendly and playful"));
        petProfileList.add(new PetProfile("Cat", "Jane Smith", "987-654-3210", "Whiskers", "3 years", "Siamese", "Calm and affectionate"));
        petProfileList.add(new PetProfile("Rabbit", "Alice Brown", "555-123-4567", "Thumper", "1 year", "Netherland Dwarf", "Curious and energetic"));

        adapter = new PetProfileAdapter(this, petProfileList);
        recyclerView.setAdapter(adapter);
    }
}
