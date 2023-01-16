package com.example.chatapp.createMessage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class CreateMessageFragment extends Fragment {
    FirebaseAuth hAuth;
    FirebaseFirestore hStore;

    EditText messageName, message;
    Button createMessageButton;
    RecyclerView messageRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_message, container, false);

        message = view.findViewById(R.id.messageName);
        messageName = view.findViewById(R.id.message);
        createMessageButton = view.findViewById(R.id.btn_createMessage);
        messageRecyclerView = view.findViewById(R.id.recyclerView_messages);

        hAuth = FirebaseAuth.getInstance();
        hStore = FirebaseFirestore.getInstance();

        createMessageButton.setOnClickListener(v -> {
            String messageNameText = messageName.getText().toString();
            String messageText = message.getText().toString();

            if (messageNameText.isEmpty()){
                Toast.makeText(getContext(), "Mesaj Adı Boş Bırakılamaz ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (messageText.isEmpty()) {
                Toast.makeText(getContext(), "Mesaj Alanı Boş Bırakılamaz ", Toast.LENGTH_SHORT).show();
                return;
            }
            createMessage(messageNameText, messageText);
        });

        return view;
    }

    private void createMessage(String messageNameText, String messageText) {
        String userId = hAuth.getCurrentUser().getUid();

        hStore.collection("/users/" + userId + "/messages").add(new HashMap<String, String>() {{
            put("messageName", messageNameText);
            put("message", messageText);

        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Mesaj Oluşturuldu ", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Mesaj Oluşturulamadı ", Toast.LENGTH_SHORT).show();
        });

    }
}