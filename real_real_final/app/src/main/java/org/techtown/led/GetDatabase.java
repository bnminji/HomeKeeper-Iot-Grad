package org.techtown.led;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GetDatabase extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference detectionReference = databaseReference.child("detection");
    private DatabaseReference intruderReference = detectionReference.child("intruders");
    private DatabaseReference first = intruderReference.child("img2");
    ImageView imageView;
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_database);

        imageView = findViewById(R.id.id_img);

        editText = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button010);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver = editText.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+receiver));
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button119);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver = "119";
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+receiver));
                startActivity(intent2);
            }
        });

        Button button3 = (Button) findViewById(R.id.button112);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiver = "112";
                Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+receiver));
                startActivity(intent3);
            }
        });

        Button button4 =(Button) findViewById(R.id.button_back);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent4 = new Intent(getApplicationContext(),MainActivity.class);
//                startActivityForResult(intent4,101);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivityForResult(intent,0);
                finish();
            }
        });
//        Button button_cctv = (Button) findViewById(R.id.button_cctv);
////        button_cctv.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent4 = new Intent(getApplicationContext(),CctvPage.class);
////                startActivityForResult(intent4,101);
////            }
////        });

//        Button button_diary = (Button) findViewById(R.id.button_diary);
//        button_diary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent5 = new Intent(getApplicationContext(),chartActivity.class);
//                startActivityForResult(intent5, 102);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){
            String status = data.getStringExtra("status");
            Toast.makeText(getApplicationContext(),"cctv 응답 : " +status, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","onStart");
        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                if(Picasso.get().load(link)==null){Log.e("PICASSO","FAIL");}
                else Log.e("PICASSO","SUCCESS");
                Picasso.get().load(link).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}