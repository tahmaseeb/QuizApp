package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private int mQuestionIndex, mQuizQuestion, mScore, mWrongScore;
    Button btnTrue, btnFalse;
    private TextView mTextQuestion, mQuizStat;
    private ProgressBar mQuizProgressBar;

    private QuizModel[] questionCollection = new QuizModel[]{
            new QuizModel(R.string.q1,true),
            new QuizModel(R.string.q2,false),
            new QuizModel(R.string.q3,true),
            new QuizModel(R.string.q4,false),
            new QuizModel(R.string.q5,true),
            new QuizModel(R.string.q6,true),
            new QuizModel(R.string.q7,false),
            new QuizModel(R.string.q8,true),
    };
    final  int USER_PROGRESS =100/questionCollection.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTrue=findViewById(R.id.btnTrue);
        btnFalse=findViewById(R.id.btnFalse);
        mTextQuestion = findViewById(R.id.txtQuestion);
        mQuizStat = findViewById(R.id.txtQuizStat);
        mQuizProgressBar =findViewById(R.id.progressBar);

        if (savedInstanceState!=null){
            mQuestionIndex=savedInstanceState.getInt("INDEX");
            mScore=savedInstanceState.getInt("SCORE_A");
            mWrongScore=savedInstanceState.getInt("SCORE_B");
            mQuizStat.setText("True: " + mScore + " " + "False: " + mWrongScore + "");
        }

        QuizModel q1 = questionCollection[mQuestionIndex];
        mQuizQuestion = q1.getmQuestion();
        mTextQuestion.setText(mQuizQuestion);



        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.btnTrue){
                    Log.i("TestText",v.getId()+"");
                    evaluateAnswer (true);

                    changeQuestionOnButtonClick();



                }
                else if (v.getId()==R.id.btnFalse){
                    Log.i("TestText",v.getId()+"");
                    evaluateAnswer (false);

                    changeQuestionOnButtonClick();

                }
            }
        };
        btnTrue.setOnClickListener(myClickListener);
        btnFalse.setOnClickListener(myClickListener);
    }

    private void changeQuestionOnButtonClick(){

            mQuestionIndex = (mQuestionIndex + 1) % questionCollection.length;

        if (mQuestionIndex==0){
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setTitle("Quiz is finished");
            quizAlert.setMessage("True: " + mScore + " " + "False: " + mWrongScore + "");
            quizAlert.setCancelable(false);
            quizAlert.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();

        }
            mQuizQuestion = questionCollection[mQuestionIndex].getmQuestion();
            mTextQuestion.setText(mQuizQuestion);
            mQuizProgressBar.incrementProgressBy(USER_PROGRESS);

    }

    private void evaluateAnswer(boolean userAnswer){
        boolean currentQuestionAnswer = questionCollection[mQuestionIndex].ismAnswer();
        if (currentQuestionAnswer == userAnswer){
            Toast myToast = Toast.makeText(getApplicationContext(),"true",Toast.LENGTH_LONG);
            myToast.show();
            trackScore(true);
        }
        else {
            Toast myToast = Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_LONG);
            myToast.show();
            trackScore(false);
        }

    }

    private void trackScore(boolean userAnswer){
        if (userAnswer){
            mScore ++;
            mQuizStat.setText("True: " + mScore + " " + "False: " + mWrongScore + "");
        }
        else {
            mWrongScore++;
            mQuizStat.setText("True: " + mScore + " " + "False: " + mWrongScore + "");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("SCORE_A", mScore);
        outState.putInt("SCORE_B", mWrongScore);
        outState.putInt("INDEX", mQuestionIndex);

    }
}
