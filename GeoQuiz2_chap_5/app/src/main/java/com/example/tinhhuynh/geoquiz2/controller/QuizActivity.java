package com.example.tinhhuynh.geoquiz2.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinhhuynh.geoquiz2.R;
import com.example.tinhhuynh.geoquiz2.model.Question;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER = "cheater";
    private static final String KEY_CHEATED_INDEXES = "cheatedIndexes";
    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private ArrayList<Integer> mCheatedIndexes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_quiz);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTue = mQuestionBank[mCurrentIndex].isTrueAnswer();
                Intent i =  CheatActivity.newIntent(QuizActivity.this, answerIsTue);
//                startActivity(i);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER);
            mCheatedIndexes = savedInstanceState.getIntegerArrayList(KEY_CHEATED_INDEXES);
        }
        updateQuestion();

    }



    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueAnswer();
        int messageResId = R.string.incorrect_toast;
        if (mIsCheater || (!mCheatedIndexes.isEmpty() && mCheatedIndexes.contains(mCurrentIndex))) {
            messageResId = R.string.judgement_toast;
            if (!mIsCheater) {
                mIsCheater = true;
//                mCheatedIndexes.remove(mCheatedIndexes.indexOf(mCurrentIndex));
            }
        }else {
            if (answerIsTrue == userPressedTrue) {
                messageResId = R.string.correct_toast;
            }
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data != null){
                mIsCheater = CheatActivity.wasAnswerShown(data);
//                Log.d("CheatActivity", "cheater: " + mIsCheater);
                if(mIsCheater && !mCheatedIndexes.contains(mCurrentIndex)){
                    mCheatedIndexes.add(mCurrentIndex);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_CHEATER, mIsCheater);
        outState.putIntegerArrayList(KEY_CHEATED_INDEXES, mCheatedIndexes);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
