package com.example.furrishta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PetProfileAdapter extends RecyclerView.Adapter<PetProfileAdapter.PetProfileViewHolder> {

    private Context context;
    private List<PetProfile> petProfileList;

    public PetProfileAdapter(Context context, List<PetProfile> petProfileList) {
        this.context = context;
        this.petProfileList = petProfileList;
    }

    @NonNull
    @Override
    public PetProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_profile, parent, false);
        return new PetProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetProfileViewHolder holder, int position) {
        PetProfile petProfile = petProfileList.get(position);

        holder.animalType.setText(petProfile.getAnimalType());
        holder.ownerName.setText(petProfile.getOwnerName());
        holder.ownerContact.setText(petProfile.getOwnerContact());
        holder.petName.setText(petProfile.getPetName());
        holder.petAge.setText(petProfile.getPetAge());
        holder.petBreed.setText(petProfile.getPetBreed());
        holder.petDescription.setText(petProfile.getPetDescription());

        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start ChatActivity
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("PET_NAME", petProfile.getPetName()); // Pass pet name to ChatActivity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return petProfileList.size();
    }

    public static class PetProfileViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView animalType, ownerName, ownerContact, petName, petAge, petBreed, petDescription;
        Button chatButton;

        public PetProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.pet_image);
            animalType = itemView.findViewById(R.id.animal_type);
            ownerName = itemView.findViewById(R.id.owner_name);
            ownerContact = itemView.findViewById(R.id.owner_contact);
            petName = itemView.findViewById(R.id.pet_name);
            petAge = itemView.findViewById(R.id.pet_age);
            petBreed = itemView.findViewById(R.id.pet_breed);
            petDescription = itemView.findViewById(R.id.pet_description);
            chatButton = itemView.findViewById(R.id.btn_chat);
        }
    }
}
