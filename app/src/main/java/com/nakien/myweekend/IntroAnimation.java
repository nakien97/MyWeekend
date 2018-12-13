package com.nakien.myweekend;

import android.content.Intent;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroAnimation extends AppCompatActivity implements Animation.AnimationListener{
    @BindView(R.id.introLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.imageViewLogo)
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_animation);

        ButterKnife.bind(this);

        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate_icon);
        imgLogo.setAnimation(translate);

        Animation alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha_background);
        constraintLayout.setAnimation(alphaAnim);

        alphaAnim.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroAnimation.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
