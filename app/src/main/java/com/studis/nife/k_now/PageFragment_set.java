package com.studis.nife.k_now;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment_set extends Fragment {
    TextView tv;
    Button button;
    EditText et_question, et_a1, et_a2;
    String link = "http://qnascm.myqnapcloud.com/k_now/set_questions.cgi";
    MainActivity activity;

    public PageFragment_set() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_page_set_layout, container, false);
        tv = view.findViewById(R.id.tv);
        button = view.findViewById(R.id.button_set);
        et_question = view.findViewById(R.id.et_question);
        et_a1 = view.findViewById(R.id.et_a1);
        et_a2 = view.findViewById(R.id.et_a2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String question = et_question.getText().toString();
                    String a1 = et_a1.getText().toString();
                    String a2 = et_a2.getText().toString();
                    String[][] befehl = new String[4][2];
                    befehl[0][0] = "ID";
                    befehl[0][1] = activity.ownId;
                    befehl[1][0] = "q";
                    befehl[1][1] = question;
                    befehl[2][0] = "a1";
                    befehl[2][1] = a1;
                    befehl[3][0] = "a2";
                    befehl[3][1] = a2;
                    activity.server_request(link,befehl);
                    activity.updateQuestionlist();
                    tv.setText("Frage gesendet");
                }catch (Exception e){
                    tv.setText("Fehlerhafte Eingabe: " + e.toString());
                }

            }
        });

        return view;
    }

}
