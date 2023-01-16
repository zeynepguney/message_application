package com.example.chatapp.ui.createGroup;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chatapp.GroupModels;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CreateGroupFragment extends Fragment {

    EditText groupName, groupExplanation;
    ImageView groupImage;
    Button createGroupButton;
    RecyclerView recyclerViewGroups;

    FirebaseAuth hAuth;
    FirebaseFirestore hStore;
    FirebaseStorage hStorage;

    Uri path;
    ArrayList<GroupModels> groupModelsArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_group, container, false);


        groupName = view.findViewById(R.id.groupName);
        groupExplanation = view.findViewById(R.id.groupExplanation);
        groupImage = view.findViewById(R.id.groupImage);
        createGroupButton = view.findViewById(R.id.btn_createGroup);
        recyclerViewGroups = view.findViewById(R.id.recyclerView_groups);

        hAuth = FirebaseAuth.getInstance();
        hStore = FirebaseFirestore.getInstance();
        hStorage = FirebaseStorage.getInstance();

        groupModelsArrayList = new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                path = result.getData().getData();
                groupImage.setImageURI(path);
            }
        });

        groupImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });
        createGroupButton.setOnClickListener(v -> {
            String name = groupName.getText().toString();
            String explanation = groupExplanation.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Grup Adı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
                return;
            }
            if (explanation.isEmpty()) {
                Toast.makeText(getContext(), "Grup Açıklaması Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
                return;
            }
            if (path != null) {
                StorageReference storageReference = hStorage.getReference().child("images" + UUID.randomUUID().toString());
                storageReference.putFile(path).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        Toast.makeText(getContext(), "Resim yüklendi", Toast.LENGTH_SHORT).show();
                        createGroup(name, explanation, imageUrl);
                    });
                });
            }
            else {
                createGroup(name, explanation, null);
            }
        });
        PickGroups();
        return view;
    }

    private void createGroup(String name, String explanation, String imageUrl){
        String userId = hAuth.getCurrentUser().getUid();

        hStore.collection("/users/" + userId + "/groups").add(new HashMap<String, Object>() {
            {
                put("grupAdı", name);
                put("grupAciklamasi", explanation);
                put("grupResmi", imageUrl);
                put("numaralar", new ArrayList<String>());
            }
        }).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Grup oluşturuldu", Toast.LENGTH_SHORT).show();
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                GroupModels groupModel = new GroupModels( name, explanation, imageUrl,(List<String>)documentSnapshot.get("numaralar") ,documentSnapshot.getId());
                groupModelsArrayList.add(groupModel);
                recyclerViewGroups.getAdapter().notifyItemInserted(groupModelsArrayList.size() - 1);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Grup Oluşturulamadı", Toast.LENGTH_SHORT).show();
        });

    }

    private void PickGroups() {
        String userId = hAuth.getCurrentUser().getUid();
        hStore.collection("/users/" + userId + "/groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            groupModelsArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                GroupModels groupModel = new GroupModels(documentSnapshot.getString("grupAdı"), documentSnapshot.getString("grupAciklamasi"),
                        documentSnapshot.getString("grupResmi"), (List<String>) documentSnapshot.get("numaralar"), documentSnapshot.getId());
                groupModelsArrayList.add(groupModel);
            }
            recyclerViewGroups.setAdapter(new Grouping(groupModelsArrayList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerViewGroups.setLayoutManager(linearLayoutManager);
        });
    }

}