package com.example.tinhhuynh.geoquiz2.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tinhhuynh.geoquiz2.R;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.tinhhuynh.geoquiz2.controller.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.example.tinhhuynh.geoquiz2.controller.answer_shown";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_ANSWER_IS_SHOWN = "answerIsShown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mIsAnswerShown;

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static Intent newIntent(Context packageContexr, boolean answerIsTrue) {
        Intent i = new Intent(packageContexr, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                     mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                mIsAnswerShown = true;
                setAnswerShownResult();
            }
        });
        if(savedInstanceState != null){
            mAnswerTextView.setText(savedInstanceState.getString(KEY_ANSWER));
            mIsAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_IS_SHOWN);
//            Log.d("CheatActivity", "onCreate: " + mIsAnswerShown);
        }
    }

    private void setAnswerShownResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String answer = mAnswerTextView.getText().toString();
        outState.putString(KEY_ANSWER, answer);
//        Log.d("CheatActivity", "onSave: " + mIsAnswerShown);
        outState.putBoolean(KEY_ANSWER_IS_SHOWN, mIsAnswerShown);
    }


//    @Override
//    public void onBackPressed() {
//        setAnswerShownResult();
//        super.onBackPressed();
//    }

    @Override
    protected void onPause() {
        setAnswerShownResult();
        super.onPause();
    }
}
