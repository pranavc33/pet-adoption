package com.example.furrishta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furrishta.R;
import com.example.furrishta.PetProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PetProfileAdapter extends RecyclerView.Adapter<PetProfileAdapter.PetProfileViewHolder> {

    private List<PetProfile> petProfiles;

    public PetProfileAdapter(List<PetProfile> petProfiles) {
        this.petProfiles = petProfiles;
    }

    @NonNull
    @Override
    public PetProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet_profile, parent, false);
        return new PetProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetProfileViewHolder holder, int position) {
        PetProfile petProfile = petProfiles.get(position);

        holder.animalTypeTextView.setText(petProfile.getAnimalType());
        holder.ownerNameTextView.setText(petProfile.getOwnerName());
        holder.ownerContactTextView.setText(petProfile.getOwnerContact());
        holder.petNameTextView.setText(petProfile.getPetName());
        holder.petAgeTextView.setText(petProfile.getPetAge());
        holder.petBreedTextView.setText(petProfile.getPetBreed());
        holder.petDescriptionTextView.setText(petProfile.getPetDescription());

        Picasso.get().load(petProfile.getImageUrl()).into(holder.petImageView);
    }

    @Override
    public int getItemCount() {
        return petProfiles.size();
    }

    public static class PetProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView petImageView;
        TextView animalTypeTextView;
        TextView ownerNameTextView;
        TextView ownerContactTextView;
        TextView petNameTextView;
        TextView petAgeTextView;
        TextView petBreedTextView;
        TextView petDescriptionTextView;

        public PetProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            petImageView = itemView.findViewById(R.id.pet_image);
            animalTypeTextView = itemView.findViewById(R.id.animal_type);
            ownerNameTextView = itemView.findViewById(R.id.owner_name);
            ownerContactTextView = itemView.findViewById(R.id.owner_contact);
            petNameTextView = itemView.findViewById(R.id.pet_name);
            petAgeTextView = itemView.findViewById(R.id.pet_age);
            petBreedTextView = itemView.findViewById(R.id.pet_breed);
            petDescriptionTextView = itemView.findViewById(R.id.pet_description);
        }
    }
}

