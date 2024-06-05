package com.example.furrishta;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE_PICK = 1001;
    private static final int REQUEST_CODE_PERMISSIONS = 1002;

    private Spinner animalTypeSpinner;
    private EditText ownerNameEditText, ownerContactEditText, petNameEditText, petAgeEditText, petBreedEditText, petDescriptionEditText;
    private ImageView petImageView;
    private Button chooseImageButton, submitButton;

    private Uri imageUri;

    private FirebaseFirestore db;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        animalTypeSpinner = findViewById(R.id.spinner_animal_type);
        ownerNameEditText = findViewById(R.id.owner_name);
        ownerContactEditText = findViewById(R.id.owner_contact);
        petNameEditText = findViewById(R.id.pet_name);
        petAgeEditText = findViewById(R.id.pet_age);
        petBreedEditText = findViewById(R.id.pet_breed);
        petDescriptionEditText = findViewById(R.id.pet_description);
        petImageView = findViewById(R.id.image_view_pet);
        chooseImageButton = findViewById(R.id.btn_choose_image);
        submitButton = findViewById(R.id.btn_submit_profile);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.animal_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalTypeSpinner.setAdapter(adapter);

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    openImagePicker();
                } else {
                    requestPermissions();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();
            }
        });
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, REQUEST_CODE_PERMISSIONS);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Log.d("CreateProfileActivity", "Image URI: " + imageUri.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                petImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void submitProfile() {
        String animalType = animalTypeSpinner.getSelectedItem().toString();
        String ownerName = ownerNameEditText.getText().toString();
        String ownerContact = ownerContactEditText.getText().toString();
        String petName = petNameEditText.getText().toString();
        String petAge = petAgeEditText.getText().toString();
        String petBreed = petBreedEditText.getText().toString();
        String petDescription = petDescriptionEditText.getText().toString();

        if (imageUri != null) {
            StorageReference storageRef = storage.getReference();
            StorageReference petImageRef = storageRef.child("pet_images/" + UUID.randomUUID().toString());

            petImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        petImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveProfileToFirestore(animalType, ownerName, ownerContact, petName, petAge, petBreed, petDescription, imageUrl);
                        }).addOnFailureListener(e -> {
                            Toast.makeText(CreateProfileActivity.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(CreateProfileActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            saveProfileToFirestore(animalType, ownerName, ownerContact, petName, petAge, petBreed, petDescription, null);
        }
    }

    private void saveProfileToFirestore(String animalType, String ownerName, String ownerContact, String petName, String petAge, String petBreed, String petDescription, String imageUrl) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("animalType", animalType);
        profile.put("ownerName", ownerName);
        profile.put("ownerContact", ownerContact);
        profile.put("petName", petName);
        profile.put("petAge", petAge);
        profile.put("petBreed", petBreed);
        profile.put("petDescription", petDescription);
        profile.put("imageUrl", imageUrl);

        db.collection("profiles").add(profile).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intent = new Intent(CreateProfileActivity.this, ProfilePreviewActivity.class);
                intent.putExtra("profileId", documentReference.getId());
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateProfileActivity.this, "Failed to save profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

