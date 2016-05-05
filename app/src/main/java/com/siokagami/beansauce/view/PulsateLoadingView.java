package com.siokagami.beansauce.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.siokagami.beansauce.R;

import java.lang.ref.WeakReference;
public class PulsateLoadingView extends FrameLayout {
  private static final int ANIMATION_DURATION = 600;
  private static float distance = 100;
  public float factor = 1.1f;
  private ImageView shadowView;
  private ImageView pulsateView;
  private AnimatorSet animatorSetUpThrow;
  private AnimatorSet animatorSetFreeFall;
  private AnimatorSet animatorSet = null;
  private Runnable freeFallRunnable;
  public PulsateLoadingView(Context context) {
    super(context);
    init();
  }
  public PulsateLoadingView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }
  public PulsateLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }
  private void init() {
    LayoutInflater inflater = LayoutInflater.from(getContext());
    View rootView = inflater.inflate(R.layout.pulsate_loading, this, false);
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    params.gravity = Gravity.CENTER;
    shadowView = (ImageView) rootView.findViewById(R.id.pulsate_loading_shadow);
    pulsateView = (ImageView) rootView.findViewById(R.id.pulsate_loading_view);
    addView(rootView);
    distance = dip2px(54f);
    initAnimator();
    startLoading(900);
  }
  private void startLoading(long delay) {
    if (animatorSet != null && animatorSet.isRunning()) return;
    this.removeCallbacks(freeFallRunnable);
    if (delay > 0) {
      this.postDelayed(freeFallRunnable, delay);
    } else {
      this.post(freeFallRunnable);
    }
  }
  private void stopLoading() {
    if (animatorSet != null) {
      if (animatorSet.isRunning()) animatorSet.cancel();
      animatorSet = null;
    }
    this.removeCallbacks(freeFallRunnable);
  }
  @Override public void setVisibility(int visibility) {
    super.setVisibility(visibility);
    if (visibility == VISIBLE) {
      startLoading(200);
    } else {
      stopLoading();
    }
  }
  private void initAnimator() {
    freeFallRunnable = new FreeFallRunnable(new WeakReference<>(this));
    ObjectAnimator translationViewUpThrow =
        ObjectAnimator.ofFloat(pulsateView, "translationY", distance, 0);
    ObjectAnimator scaleXViewUpThrow = ObjectAnimator.ofFloat(pulsateView, "scaleX", 0.2f, 1);
    ObjectAnimator scaleYViewUpThrow = ObjectAnimator.ofFloat(pulsateView, "scaleY", 0.2f, 1);
    ObjectAnimator scaleShadowUpThrow = ObjectAnimator.ofFloat(shadowView, "scaleX", 0.2f, 1);
    translationViewUpThrow.setInterpolator(new DecelerateInterpolator(factor));
    animatorSetUpThrow = new AnimatorSet();
    animatorSetUpThrow.setDuration(ANIMATION_DURATION);
    animatorSetUpThrow.playTogether(translationViewUpThrow, scaleXViewUpThrow, scaleYViewUpThrow,
        scaleShadowUpThrow);
    animatorSetUpThrow.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
      }
      @Override public void onAnimationEnd(Animator animation) {
        freeFall();
      }
      @Override public void onAnimationCancel(Animator animation) {
      }
      @Override public void onAnimationRepeat(Animator animation) {
      }
    });
    ObjectAnimator translationViewFreeFall =
        ObjectAnimator.ofFloat(pulsateView, "translationY", 0, distance);
    ObjectAnimator scaleXViewFreeFall = ObjectAnimator.ofFloat(pulsateView, "scaleX", 1, 0.2f);
    ObjectAnimator scaleYViewFreeFall = ObjectAnimator.ofFloat(pulsateView, "scaleY", 1, 0.2f);
    //ObjectAnimator rotateViewFreeFall = ObjectAnimator.ofFloat(pulsateView, "rotation", 0, -180);
    ObjectAnimator scaleShadowFreeFall = ObjectAnimator.ofFloat(shadowView, "scaleX", 1, 0.2f);
    translationViewFreeFall.setInterpolator(new AccelerateInterpolator(factor));
    animatorSetFreeFall = new AnimatorSet();
    animatorSetFreeFall.setDuration(ANIMATION_DURATION);
    animatorSetFreeFall.playTogether(translationViewFreeFall, scaleXViewFreeFall,
        scaleYViewFreeFall, scaleShadowFreeFall);
    animatorSetFreeFall.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
      }
      @Override public void onAnimationEnd(Animator animation) {
        upThrow();
      }
      @Override public void onAnimationCancel(Animator animation) {
      }
      @Override public void onAnimationRepeat(Animator animation) {
      }
    });
  }
  public void upThrow() {
    System.gc();
    animatorSet = animatorSetUpThrow;
    animatorSet.start();
  }
  public void freeFall() {
    System.gc();
    animatorSet = animatorSetFreeFall;
    animatorSet.start();
  }
  public int dip2px(float dipValue) {
    final float scale = getContext().getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }
  private static class FreeFallRunnable implements Runnable {
    private WeakReference<PulsateLoadingView> weakReference;
    public FreeFallRunnable(WeakReference<PulsateLoadingView> weakReference) {
      this.weakReference = weakReference;
    }
    @Override public void run() {
      weakReference.get().freeFall();
    }
  }
}