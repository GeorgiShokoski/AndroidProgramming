package com.example.ap_workshop;

public class pollData {
    String authorName;
    String pollQuestion;
    String pollOption1;
    String pollOption2;
    String pollOption3;
    String pollOption4;


    pollData(String authorName,
             String pollQuestion,
             String pollOption1,
             String pollOption2,
             String pollOption3,
             String pollOption4)
    {
        this.authorName = authorName;
        this.pollQuestion = pollQuestion;
        this.pollOption1 = pollOption1;
        this.pollOption2 = pollOption2;
        this.pollOption3 = pollOption3;
        this.pollOption4 = pollOption4;
    }
}
