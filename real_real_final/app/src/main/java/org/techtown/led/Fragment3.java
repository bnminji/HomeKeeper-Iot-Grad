package org.techtown.led;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    MainActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (MainActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);
        ImageButton intruder =(ImageButton)rootView.findViewById(R.id.intruder);
        ImageButton register=(ImageButton)rootView.findViewById(R.id.register);
        ImageButton diary=(ImageButton)rootView.findViewById(R.id.diary);

        intruder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetDatabase.class) ;
                startActivity(intent) ;
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(),CctvPage.class);
                startActivityForResult(intent4,101);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mActivity.sendMsg("LED","register");
                Toast.makeText(getActivity(),"얼굴 등록을 시작합니다.",Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
    public static Fragment3 newInstance() {
        Bundle args = new Bundle();

        Fragment3 fragment = new Fragment3();
        fragment.setArguments(args);
        return fragment;
    }
}