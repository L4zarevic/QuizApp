package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Variables
    private final String SCORE_KEY = "SCORE";
    private final String INDEX_KEY = "IDEX";

    private TextView mTxtQuestion;
    private ProgressBar mProgressBar;
    private TextView mQuizStatsTextView;
    private Button btnTrue;
    private Button btnWrong;

    private int mQuestionIndex;
    private int mQuizQuestion;
    private int mUserScore;

    //Create array which contains question from string.xml and answer what we define
    private QuizModel[] questionCollection = new QuizModel[]{
            new QuizModel(R.string.q1, true),
            new QuizModel(R.string.q2, false),
            new QuizModel(R.string.q3, true),
            new QuizModel(R.string.q4, false),
            new QuizModel(R.string.q5, true),
            new QuizModel(R.string.q6, false),
            new QuizModel(R.string.q7, true),
            new QuizModel(R.string.q8, false),
            new QuizModel(R.string.q9, true),
            new QuizModel(R.string.q10, false),


    };

    //Initialization of a variable which is a constant that will represent the progress state
    final int USER_PROGRESS = (int) Math.ceil(100 / questionCollection.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Condition in which savedInstanceStace value is checked is different from null or not
        //This is necessary when changing the position of the screen so that the required values are not lost
        if (savedInstanceState != null) {
            mUserScore = savedInstanceState.getInt(SCORE_KEY);
            mQuestionIndex = savedInstanceState.getInt(INDEX_KEY);

        } else {
            //Else represent when app is just starting
            mUserScore = 0;
            mQuestionIndex = 0;
        }

        mTxtQuestion = findViewById(R.id.txtQuestion);
        QuizModel q1 = questionCollection[mQuestionIndex];
        mQuizQuestion = q1.getQuestion();
        mTxtQuestion.setText(mQuizQuestion);

        mProgressBar = findViewById(R.id.quizPB);
        mQuizStatsTextView = findViewById(R.id.txtQuizStats);
        mQuizStatsTextView.setText(mUserScore + "");

        btnTrue = findViewById(R.id.btnTrue);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(true);
                changeQuestionOnButtonClick();

            }
        });


        btnWrong = findViewById(R.id.btnWrong);
        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUserAnswer(false);
                changeQuestionOnButtonClick();

            }
        });


    }

    //A method that is invoked when buttons are clicked
    private void changeQuestionOnButtonClick() {
        mQuestionIndex = (mQuestionIndex + 1) % 10;

        //Checking is question index equal to zero
        if (mQuestionIndex == 0) {

            //Create alert dialog
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            //Disable to click outside of alert dialog
            quizAlert.setCancelable(false);
            //Set title of alert dialog
            quizAlert.setTitle("The quiz is finished");
            //Set message in alert dialog
            quizAlert.setMessage("Your score is " + mUserScore);

            //Creative button in alert dialog
            quizAlert.setPositiveButton("Finish the quiz", new DialogInterface.OnClickListener() {

                //Method when click on button in alert dialog
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Metod which close app
                    finish();
                }
            });

            //Show alert dialog
            quizAlert.show();
        }

        //Getting a index value of question
        mQuizQuestion = questionCollection[mQuestionIndex].getQuestion();

        //Set question in text view
        mTxtQuestion.setText(mQuizQuestion);

        //Changing of progress bar look depending on the value USER_PROGRESS
        mProgressBar.incrementProgressBy(USER_PROGRESS);

        //Set the value of user score
        mQuizStatsTextView.setText(mUserScore + "");
    }

    //A method that checks if the answer is correct
    private void evaluateUserAnswer(boolean userGuess) {

        //The variable gets the value of the correct answer for a particular question
        boolean currentQuestionAnswer = questionCollection[mQuestionIndex].isAnswer();

        //Condition where checking is correct answer is equal to users answer
        if (currentQuestionAnswer == userGuess) {
            //Show message
            Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_SHORT).show();

            //Increment users score
            mUserScore = mUserScore + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
        }
    }

    //This method saves important values when screen position is change
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY, mUserScore);
        outState.putInt(INDEX_KEY, mQuestionIndex);
    }
}
