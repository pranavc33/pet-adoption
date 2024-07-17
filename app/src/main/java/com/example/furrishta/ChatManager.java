package com.example.furrishta;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private DatabaseReference chatRef;

    public ChatManager() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        chatRef = database.getReference("messages"); // Use the correct reference path
    }

    public void sendMessage(ChatMessage message) {
        chatRef.push().setValue(message);
    }

    public void getMessages(final MessageCallback callback) {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChatMessage> messages = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    if (chatMessage != null) {
                        messages.add(chatMessage);
                    }
                }
                callback.onMessagesReceived(messages);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });
    }

    public interface MessageCallback {
        void onMessagesReceived(List<ChatMessage> messages);
    }
}
