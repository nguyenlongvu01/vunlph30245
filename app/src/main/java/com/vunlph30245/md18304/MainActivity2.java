package com.vunlph30245.md18304;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    TextView tvKq;
    FirebaseFirestore database;
    Context context = this;
    String strKq = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvKq = findViewById(R.id.tvKq);
        database = FirebaseFirestore.getInstance();// khoi tao
        //insert();
        update();

    }
    void insert(){
        String id = UUID.randomUUID().toString();
        ToDo toDo = new ToDo(id, "title 11", "content11");// tao doi tuong moi de insert
        database.collection("TODO")//truy cap den bang du lieu
                .document(id)//truy cap den dong du lieu
                .set(toDo.convertToHashMap())//dua du lieu vao dong
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "insert thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void update(){
        String id ="2bef754b-cb1c-417a-9d86-f438f808172a";// copy id vao day
        ToDo toDo = new ToDo(id,"title 11 update","content 11 update");
        database.collection("TODO")//lay bang du lieu
                .document(id) //lay id
                .update(toDo.convertToHashMap())// thuc hien update
                .addOnSuccessListener(new OnSuccessListener<Void>() { // thanh cong
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void delete(){
        String id="";// cop id
        database.collection("TODO")//truy cap vao bang du lieu
                .document(id)//truy cap vao id
                .delete()// thuc hien xoa
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    ArrayList<ToDo> select(){
        ArrayList<ToDo> list = new ArrayList<>();
        database.collection("TODO")// truy cap vao bang du lieu
                .get()// lay ve du lieu
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            strKq="";
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                ToDo t= doc.toObject(ToDo.class);//chuyen du lieu duoc doc sang ToDo
                                list.add(t);
                                strKq += "id"+t.getId()+"\n";
                                strKq += "title"+t.getTitle()+"\n";
                                strKq += "content"+t.getContent()+"\n";
                            }
                            tvKq.setText(strKq);
                        }else {
                            Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return list;
    }
}