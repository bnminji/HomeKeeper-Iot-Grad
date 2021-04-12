package org.techtown.led;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.techtown.led.Intruder;
import org.techtown.led.MainActivity;
import org.techtown.led.R;

public class Fragment4 extends Fragment {
    MainActivity mActivity;

    EditText editText;
    Button button;
    String dbDate;
    Integer cnt;
    ImageView imageView1;
    TextView textView1;
    ImageView imageView2;
    TextView textView2;
    ImageView imageView3;
    TextView textView3;
    ImageView imageView4;
    TextView textView4;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference diaryReference = databaseReference.child("diary");
    private DatabaseReference diary;


    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);
        imageView1 = rootView.findViewById(R.id.diary_img1);
        textView1 = rootView.findViewById(R.id.id_date1);

        imageView2 = rootView.findViewById(R.id.diary_img2);
        textView2 = rootView.findViewById(R.id.id_date2);

        imageView3 = rootView.findViewById(R.id.diary_img3);
        textView3 = rootView.findViewById(R.id.id_date3);

        imageView4 = rootView.findViewById(R.id.diary_img4);
        textView4 = rootView.findViewById(R.id.id_date4);

        editText = (EditText) rootView.findViewById(R.id.editText);
        button = (Button) rootView.findViewById(R.id.button_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbDate = editText.getText().toString();

                diary = diaryReference.child(dbDate);
                diary.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        try{
                            cnt = 0;
                            for (DataSnapshot snapshot : datasnapshot.getChildren()){
                                Intruder intruder = snapshot.getValue(Intruder.class);
                                String date = intruder.getDate();
                                String imgLink = intruder.getImage();
                                cnt +=1;
                                if(cnt >4){
                                    cnt = 1;
                                    Picasso.get().load(imgLink).into(imageView1);
                                    textView1.setText(date);
                                    continue;
                                }

                                if (cnt == 1){
                                    Picasso.get().load(imgLink).into(imageView1);
                                    textView1.setText(date);
                                }else if (cnt == 2){
                                    Picasso.get().load(imgLink).into(imageView2);
                                    textView2.setText(date);
                                }else if (cnt == 3){
                                    Picasso.get().load(imgLink).into(imageView3);
                                    textView3.setText(date);
                                }else if (cnt == 4){
                                    Picasso.get().load(imgLink).into(imageView4);
                                    textView4.setText(date);
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(),"해당 날짜에는 침입자가 없습니다.", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        return rootView;
    }
}