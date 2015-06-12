package com.infusion.digimenu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusion.digimenu.framework.DownloadImageTask;
import com.infusion.digimenu.model.MenuItem;

import java.text.NumberFormat;

public class MenuItemActivity extends ActionBarActivity {
    public static final String BUNDLE_MENU_ITEM = "com.infusion.digimenu.extra.BUNDLE_MENU_ITEM";

    private ImageButton mLikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        // retrieve the menu item from the bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // invalid argument - no menu item specified
            return;
        }

        final MenuItem menuItem = (MenuItem) bundle.get(BUNDLE_MENU_ITEM);
        setTitle(menuItem.name);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        new DownloadImageTask(imageView).execute(menuItem.imageUri);

        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(menuItem.description);

        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));

        mLikeButton = (ImageButton)findViewById(R.id.likeButton);

        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateLikeButtonOut();
                sendLikeAsync(menuItem.id);
            }
        });
    }


    private void sendLikeAsync(int menuItemId) {
        // perform service to notify of like
        Intent intent = new Intent(this, LikeMenuItemService.class);
        intent.putExtra(LikeMenuItemService.EXTRA_MENU_ITEM_ID, menuItemId);

        startService(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animateLikeButtonInViewXml();
    }

    private void animateLikeButtonInViewPropertyAnim() {
        float y = mLikeButton.getY();
        mLikeButton.setY(-mLikeButton.getHeight());
        mLikeButton.animate().setInterpolator(new BounceInterpolator()).setDuration(1000).y(y);
        Log.d(this.getClass().getName(), "onWindowFocusChanged");
    }

    private void animateLikeButtonInViewCode() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, //From X
                Animation.RELATIVE_TO_SELF, 0f, //To X
                Animation.RELATIVE_TO_PARENT, -1f, //From Y
                Animation.RELATIVE_TO_SELF, 0f);  //To Y
        animation.setFillBefore(true);
        animation.setInterpolator(new BounceInterpolator());
        animation.setDuration(1000);
        mLikeButton.setAnimation(animation);
        animation.start();
    }

    private void animateLikeButtonInViewXml() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.like_button_in);
        mLikeButton.setAnimation(animation);
        animation.start();
    }

    private void animateLikeButtonInPropertyAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLikeButton, "y", -mLikeButton.getHeight(), mLikeButton.getY());
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }

    private void animateLikeButtonOut(){
        mLikeButton.animate().scaleY(0f).scaleX(0f).setInterpolator(new AnticipateInterpolator()).alpha(0).setDuration(500);
    }
}
