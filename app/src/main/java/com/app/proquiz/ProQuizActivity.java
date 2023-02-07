package com.app.proquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProQuizActivity extends AppCompatActivity {
    Random rand = new Random();
    int sports = 0;
    private int scores = 0;
    private String correctAnswer;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()!=  MotionEvent.ACTION_DOWN) return super.onTouchEvent(event);

        initGame();

        ImageView ballView = findViewById(R.id.imageView);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1200);
        animator.setDuration(8000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                ballView.setTranslationY(progress);
                ballView.setRotation(progress);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        animator.start();

        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proquiz);
        Toast.makeText(this,"Tap on screen to play", Toast.LENGTH_LONG).show();
    }

    public void initGame() {

        ImageView ballView = findViewById(R.id.imageView);
        sports = randInt(0, 3);
        switch (sports) {
            case 0:
                ballView.setImageResource(R.drawable.ball_football);
                break;
            case 1:
                ballView.setImageResource(R.drawable.hockey_puck);
                break;
            case 2:
                ballView.setImageResource(R.drawable.ball_basket);
                break;
            case 3:
                ballView.setImageResource(R.drawable.ball_socker);
                break;
        }

        setButtons(sports);
    }

   // public void onAnimate(View view) {

    //    view.animate().rotation(60).setDuration(8000).start();
        //.x(10).y(10).setDuration(1000).start();
  //  }


    void setButtons(int sports) {

        List<String> list = ProSportData.getBtnNames();
        correctAnswer = list.get(sports);
        Collections.shuffle(list);

        FrameLayout frameLayout = findViewById(R.id.button_frame);
        for (int index = 0; index < frameLayout.getChildCount(); index++) {
            View nextChild = frameLayout.getChildAt(index);
            if (nextChild instanceof Button) {
                Button btn = ((Button) nextChild);
                btn.setText(list.get(index));
                btn.setBackgroundColor(Color.parseColor("#0055EE"));
                btn.setEnabled(true);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button clickedBtn = (Button) view;
                        if (checkAnswer(clickedBtn.getText().toString())) {
                            btn.setBackgroundColor(Color.parseColor("#00AA77"));
                            scores +=5;
                            TextView tv = findViewById(R.id.textView2);
                            tv.setText(String.format("SCORES %d", scores));
                            clickedBtn.setEnabled(false);
                        } else {
                            btn.setBackgroundColor(Color.parseColor("#FFAA77"));
                        }

                    }
                });
            }
        }


    }

    boolean checkAnswer(String btnText) {
        return btnText.equals(correctAnswer);
    }

    public int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}