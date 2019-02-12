package com.studis.nife.k_now;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {
    public Handler viewAnswerHandler;
    TextView tv;
    Button b1,b2;
    boolean answered = false;
    MainActivity activity;

    String link = "http://qnascm.myqnapcloud.com/k_now/send_answer.cgi";

    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            answered = savedInstanceState.getBoolean("answered");
        }
        View view = inflater.inflate(R.layout.fragment_page_layout, container, false);
        tv = view.findViewById(R.id.tv);
        b1 = view.findViewById(R.id.b1);
        b2 = view.findViewById(R.id.b2);
        Bundle bundle = getArguments();

        activity = (MainActivity) getActivity();


        String question = bundle.getString("question");
        final int q_ID = bundle.getInt("q_ID");
        tv.setText(question);

        if(!answered) {
            String a1 = bundle.getString("a1");
            String a2 = bundle.getString("a2");


            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);

            b1.setText(a1);
            b2.setText(a2);


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Sende Statistik
                    String[][] befehl = new String[3][2];
                    befehl[0][0] = "ID";
                    befehl[0][1] = activity.ownId;
                    befehl[1][0] = "q_ID";
                    befehl[1][1] = Integer.toString(q_ID);
                    befehl[2][0] = "a";
                    befehl[2][1] = "1";
                    activity.server_request(link,befehl);
                    showResult();
                    answered = true;

                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Sende Statistik
                    String[][] befehl = new String[3][2];
                    befehl[0][0] = "ID";
                    befehl[0][1] = activity.ownId;
                    befehl[1][0] = "q_ID";
                    befehl[1][1] = Integer.toString(q_ID);
                    befehl[2][0] = "a";
                    befehl[2][1] = "2";
                    activity.server_request(link,befehl);

                    showResult();
                    answered = true;
                }
            });
        }else{
            showResult();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("answered",answered);
        super.onSaveInstanceState(outState);
    }

    private void showResult() {
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
    }

}
