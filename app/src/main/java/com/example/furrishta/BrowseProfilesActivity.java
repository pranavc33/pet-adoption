package com.example.furrishta;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furrishta.R;
import com.example.furrishta.PetProfileAdapter;
import com.example.furrishta.PetProfile;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petProfileList = new ArrayList<>();
        adapter = new PetProfileAdapter(petProfileList);
        recyclerView.setAdapter(adapter);

        fetchPetProfiles();
    }

    private void fetchPetProfiles() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference petProfilesRef = db.collection("profiles");

        petProfilesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (value != null) {
                    petProfileList.clear();
                    petProfileList.addAll(value.toObjects(PetProfile.class));
                    adapter.notifyDataSetChanged();
                }
            }
      });
   }
}
