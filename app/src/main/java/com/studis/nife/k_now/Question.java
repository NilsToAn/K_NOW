package com.studis.nife.k_now;

public class Question {
    int id;
    String qText;
    int rating;
    String a1;
    String a2;

    public Question(int id,String qText,String a1,String a2,int rating) {
        this.id = id;
        this.qText = qText;
        this.rating = rating;
        this.a1 = a1;
        this.a2 = a2;
    }

}
