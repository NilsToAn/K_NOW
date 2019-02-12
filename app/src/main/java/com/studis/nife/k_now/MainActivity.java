package com.studis.nife.k_now;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends FragmentActivity{

    public String ownId = "-1";
    String link_getQuestion = "http://qnascm.myqnapcloud.com/k_now/test2.cgi";


    ViewPager viewPager;
    List<Question> questions = new ArrayList<Question>();
    List<Question> questions_own = new ArrayList<Question>();
    SwipeAdapter swipeAdapter,swipeAdapter_own;
    SwipeAdapter_set swipeAdapter_set;
    AntwortHandler antwortHandler = new AntwortHandler();

    MemoryManager MM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_l); // get the reference of TabLayout

        MM = new MemoryManager();

        ownId = getOwnId();

        questions = beispielfragen(questions);
        questions_own = beispielfragen(questions_own);


        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(),questions);
        swipeAdapter_own = new SwipeAdapter(getSupportFragmentManager(),questions_own);
        swipeAdapter_set = new SwipeAdapter_set(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);


        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener());


        updateQuestionlist();


    }
    public void updateQuestionlist(){
        String[][] befehl = new String[1][2];
        befehl[0][0] = "ID";
        befehl[0][1] = ownId;
        server_request(link_getQuestion, befehl);
    }

    private String getOwnId() {
        String id = MM.load("Main","client_ID",this);
        if(id.equals("")){
            id = UUID.randomUUID().toString();
            MM.save("Main","client_ID",id,this);
        }

        return id;
    }

    public void server_request(String link, String[][] befehle){
        Intent intent = new Intent(this,ServerCommunication.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("link",link);
        bundle.putSerializable("befehle",befehle);
        intent.putExtras(bundle);
        startService(intent);
        ServerCommunication.antwortHandler = antwortHandler;
    }
    private void server_answer(String result) {
        try {
            JSONArray rows = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                rows = new JSONArray(result);
            }
            if(rows.getString(0).equals("new_questions")) {
                newQuestions_fromServer(rows);
            }else if (rows.getString(0).equals("q_stats")){
                newQStats(rows);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void newQStats(JSONArray rows) {
    }

    private void newQuestions_fromServer(JSONArray rows){
        for (int i = 1; i < rows.length(); i++) {
            try {
                JSONArray object1 = rows.getJSONArray(i);
                int id = object1.getInt(0);
                String question = object1.getString(1);
                String a1 = object1.getString(2);
                String a2 = object1.getString(3);
                int rating = 100;
                questions.add(new Question(id, question, a1, a2, rating));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        swipeAdapter.notifyDataSetChanged();
    }


    private List<Question> beispielfragen(List<Question> questions) {
        questions.add(new Question(1,"Wie geht's ?","Gut","Schlecht",100));
        questions.add(new Question(2,"Schon von Heisberg gehört ?","Ja","Nein",100));
        questions.add(new Question(3,"Wie findest du Felix?","Gut","Schlecht",100));
        questions.add(new Question(4,"Wie findest du Nils ?","Gut","Schlecht",100));
        questions.add(new Question(5,"Wie gefällt dir diese App ?","Gut","Schlecht",100));
        return questions;
    }


    private class AntwortHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String)msg.obj;
            server_answer(result);
        }}
    private class OnTabSelectedListener implements TabLayout.OnTabSelectedListener{

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if(tab.getPosition() == 0){
                viewPager.setAdapter(swipeAdapter);
            }else if(tab.getPosition() == 1){
                viewPager.setAdapter(swipeAdapter_set);
            }else if(tab.getPosition() == 2){
                viewPager.setAdapter(swipeAdapter_own);
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

}
