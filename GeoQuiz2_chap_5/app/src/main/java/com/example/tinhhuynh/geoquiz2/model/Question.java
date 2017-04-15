package com.example.tinhhuynh.geoquiz2.model;

import java.io.Serializable;

/**
 * Created by TINH HUYNH on 4/13/2017.
 */

public class Question implements Serializable {
    private int mTextResId;
    private boolean mTrueAnswer;

    public Question(int textResId, boolean trueAnswer) {
        mTextResId = textResId;
        mTrueAnswer = trueAnswer;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isTrueAnswer() {
        return mTrueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        mTrueAnswer = trueAnswer;
    }
}
