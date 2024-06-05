package com.example.furrishta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePreviewActivity extends AppCompatActivity {

    private TextView animalTypeTextView, ownerNameTextView, ownerContactTextView, petNameTextView, petAgeTextView, petBreedTextView, petDescriptionTextView;
    private ImageView petImageView;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_preview);

        Button btn_browse_profiles = findViewById(R.id.btn_browse_profiles);
        btn_browse_profiles.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePreviewActivity.this, BrowseProfilesActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();

        animalTypeTextView = findViewById(R.id.preview_animal_type);
        ownerNameTextView = findViewById(R.id.preview_owner_name);
        ownerContactTextView = findViewById(R.id.preview_owner_contact);
        petNameTextView = findViewById(R.id.preview_pet_name);
        petAgeTextView = findViewById(R.id.preview_pet_age);
        petBreedTextView = findViewById(R.id.preview_pet_breed);
        petDescriptionTextView = findViewById(R.id.preview_pet_description);
        petImageView = findViewById(R.id.preview_pet_image);

        String profileId = getIntent().getStringExtra("profileId");
        if (profileId != null) {
            loadProfile(profileId);
        }
    }

    private void loadProfile(String profileId) {
        db.collection("profiles").document(profileId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String animalType = documentSnapshot.getString("animalType");
                    String ownerName = documentSnapshot.getString("ownerName");
                    String ownerContact = documentSnapshot.getString("ownerContact");
                    String petName = documentSnapshot.getString("petName");
                    String petAge = documentSnapshot.getString("petAge");
                    String petBreed = documentSnapshot.getString("petBreed");
                    String petDescription = documentSnapshot.getString("petDescription");
                    String imageUrl = documentSnapshot.getString("imageUrl");

                    animalTypeTextView.setText(animalType);
                    ownerNameTextView.setText(ownerName);
                    ownerContactTextView.setText(ownerContact);
                    petNameTextView.setText(petName);
                    petAgeTextView.setText(petAge);
                    petBreedTextView.setText(petBreed);
                    petDescriptionTextView.setText(petDescription);

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(ProfilePreviewActivity.this).load(imageUrl).into(petImageView);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfilePreviewActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
