package ss.ms1410.kitkateasteregg;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import ss.ms1410.kitkateasteregg.R;

import com.nineoldandroids.view.ViewHelper;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlatLogoActivity extends Activity {
    FrameLayout mContent;
    int mCount;
    final Handler mHandler = new Handler();
    static final int BGCOLOR = 0xffed1d24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Typeface bold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
        Typeface light = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");

        mContent = new FrameLayout(this);
        mContent.setBackgroundColor(0xC0000000);
        
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;

        final ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.platlogo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        logo.setVisibility(View.INVISIBLE);

        final View bg = new View(this);
        bg.setBackgroundColor(BGCOLOR);
        ViewHelper.setAlpha(bg, 0f);

        final TextView letter = new TextView(this);

        letter.setTypeface(bold);
        letter.setTextSize(300);
        letter.setTextColor(0xFFFFFFFF);
        letter.setGravity(Gravity.CENTER);
        letter.setText("K");

        final TextView tv = new TextView(this);
        if (light != null) tv.setTypeface(light);
        tv.setTextSize(30);
        tv.setPadding(52, 243, 0, 0);
        tv.setTextColor(0xFFFFFFFF);
        tv.setGravity(Gravity.CENTER);
        tv.setText("ANDROID 4.4" );
        tv.setVisibility(View.INVISIBLE);

        mContent.addView(bg);
        mContent.addView(letter, lp);
        mContent.addView(logo, lp);

        final LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        lp1.bottomMargin = (int) (-4*metrics.density);

        mContent.addView(tv, lp1);

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               animate(letter).cancel();
                animate(letter)
                .rotationBy((Math.random() > 0.5f ? 360 : -360) )
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(700).start();
            }
        });

        mContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (logo.getVisibility() != View.VISIBLE) {
                    ViewHelper.setScaleX(bg, 0.01f);
                    animate(bg).alpha(1f).scaleX(1f).setStartDelay(500).start();
                    animate(letter).alpha(0f).scaleY(0.5f).scaleX(0.5f)
                            .rotationBy(360)
                            .setInterpolator(new AccelerateInterpolator())
                            .setDuration(1000)
                            .start();
                    ViewHelper.setAlpha(logo, 0f);
                    logo.setVisibility(View.VISIBLE);
                    ViewHelper.setScaleX(logo, 0.5f);
                    ViewHelper.setScaleY(logo, 0.5f);
                    animate(logo).alpha(1f).scaleX(1f).scaleY(1f)
                        .setDuration(1000).setStartDelay(500)
                        .setInterpolator(new AnticipateOvershootInterpolator())
                        .start();
                    ViewHelper.setAlpha(tv, 0f);
                    tv.setVisibility(View.VISIBLE);
                    animate(tv).alpha(1f).setDuration(1000).setStartDelay(1000).start();
                    return true;
                }
                return false;
            }
        });

        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {                        
            	finish();
                return true;
            }
        });
        
        setContentView(mContent);
    }
}