package com.studis.nife.k_now;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends FragmentStatePagerAdapter {
    List<Question> questions = new ArrayList<Question>();



    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }
    public SwipeAdapter(FragmentManager fm, List<Question> questions){
        super(fm);
        this.questions = questions;

    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count",i+1);
        bundle.putInt("q_ID",questions.get(i).id);
        bundle.putString("question",questions.get(i).qText);
        bundle.putString("a1",questions.get(i).a1);
        bundle.putString("a2",questions.get(i).a2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
