package com.infusion.digimenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.infusion.digimenu.framework.DownloadImageTask;
import com.infusion.digimenu.model.MenuItem;

import java.text.NumberFormat;

public class MenuItemActivity extends ActionBarActivity {

    public static final String BUNDLE_MENU_ITEM = "com.infusion.digimenu.extra.BUNDLE_MENU_ITEM";

    private AsyncTask<String, Void, Bitmap> mDownloadImageTask;
    private ImageButton mLikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // bundle expected - guard
            return;
        }

        // retrieve the menu item from the bundle
        final MenuItem menuItem = (MenuItem) bundle.get(BUNDLE_MENU_ITEM);

        // start download the menu item image
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        mDownloadImageTask = new DownloadImageTask(imageView).execute(menuItem.imageUri);

        // setup the activity title
        setTitle(menuItem.name);

        // setup the menu item description
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(menuItem.description);

        // setup the menu item price
        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(menuItem.price));

        // setup the menu item like button
        mLikeButton = (ImageButton) findViewById(R.id.likeButton);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: (5) call animateLikeButtonOut() method to play an exit animation for the button
                sendLike(menuItem.id);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // stop the menu item image from downloading (if it hasn't already completed)
        if (mDownloadImageTask != null && mDownloadImageTask.getStatus() != AsyncTask.Status.FINISHED) {
            mDownloadImageTask.cancel(true);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //We will be achieving the same animation effect by few different methods available to us from Android framework
        //We will be implementing appropriately named method (animateLikeButtonIn....()) and call it here
        animateLikeButtonInViewCode();
    }

    private void sendLike(int menuItemId) {
        // perform service to notify of like
        Intent intent = new Intent(this, LikeMenuItemService.class);
        intent.putExtra(LikeMenuItemService.EXTRA_MENU_ITEM_ID, menuItemId);

        startService(intent);
    }

    private void animateLikeButtonInViewCode() {
        //TODO: (1) Create an animation object in code, attach it to the 'Like' button and play it
        //Description of the Animation:
        //  'Like' button drops from the top of the screen to it's resting location
        //  When the 'Like' button reaches the resting location, it bounces before it settles
        //  The total duration of the animation is 1 second
        //
        //  Hint: Look for TranslateAnimation class provided by Android Animation Framework.
        //  Use the constructor TranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue)
        //  Set duration and interpolator
        //  Set the animation on mLikeButton
        //  Start the animation
    }

    private void animateLikeButtonInViewXml() {
        //TODO: (2) Create an animation in XML, load it an attach to the 'Like' button.
        //Call this method in onWindowFocusChanged()
        //Description of the Animation:
        //  'Like' button drops from the top of the screen to it's resting location
        //  When the 'Like' button reaches the resting location, it bounces before it settles
        //  The total duration of the animation is 1 second
        //
        //  Hint: create the animation xml file (like_button_in.xml) in /res/anim folder (right-click folder->New->Animation Resource File)
        //  Use AnimationUtils to load the animation onto mLikeButton
        //  Set duration and interpolator
        //  Start the animation
    }

    private void animateLikeButtonInPropertyAnim() {
        //TODO: (3) Create a property animation for the "y" property of 'Like' button
        //Call this method in onWindowFocusChanged()
        //Description of the Animation:
        //  'Like' button drops from the top of the screen to it's resting location
        //  When the 'Like' button reaches the resting location, it bounces before it settles
        //  The total duration of the animation is 1 second
        //
        //  Hint: Use ObjectAnimator->ofFloat(Object target, String propertyName, float... values) method for creating the animation
        //  Pass mLikeButton as target, use "y" for property name and specify starting and ending values
        //  Set duration and interpolator
        //  Start the animation

    }

    private void animateLikeButtonInViewPropertyAnim() {
        //TODO: (4) Animate the 'Like' button using ViewPropertyAnimator
        //Call this method in onWindowFocusChanged()
        //Description of the Animation:
        //  'Like' button drops from the top of the screen to it's resting location
        //  When the 'Like' button reaches the resting location, it bounces before it settles
        //  The total duration of the animation is 1 second
        //
        //  Hint: Call animate() method of the button to access ViewPropertyAnimator
        //  ViewPropertyAnimator doesn't set the initial position of the view. You will have to place it above to top of the screen
        //  before you start the animation.
    }

    private void animateLikeButtonOut() {
        //TODO: (6) LAB 5 : Animations - Play an animation when the 'Like' button is clicked
        // Make the button gradually shrink and increase transparency until it disappears. Use AnticipateInterpolator and set duration to 500ms
        // Use ViewPropertyAnimator of the button for this
    }
}
