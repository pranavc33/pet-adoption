package com.example.furrishta;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> messageList;
    private EditText inputMessage;
    private Button sendButton;
    private DatabaseReference chatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycler_view);
        inputMessage = findViewById(R.id.edit_text_message);
        sendButton = findViewById(R.id.button_send);

        messageList = new ArrayList<>();
        adapter = new ChatMessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        String petName = getIntent().getStringExtra("PET_NAME");
        chatRef = FirebaseDatabase.getInstance().getReference("messages").child(petName);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        loadMessages();
    }

    private void loadMessages() {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    ChatMessage message = child.getValue(ChatMessage.class);
                    messageList.add(message);
                }
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChatActivity", "Failed to load messages", error.toException());
            }
        });
    }

    private void sendMessage() {
        String messageText = inputMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(messageText)) {
            String senderId = "user_id"; // Replace with actual sender ID
            long timestamp = System.currentTimeMillis();
            ChatMessage message = new ChatMessage(senderId, messageText, timestamp);

            chatRef.push().setValue(message).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    inputMessage.setText("");
                    recyclerView.scrollToPosition(messageList.size() - 1);
                } else {
                    Log.e("ChatActivity", "Failed to send message", task.getException());
                }
            });
        } else {
            Log.w("ChatActivity", "Message text is empty");
        }
    }
}
