package Rand_Imgs_ScrollView;

import android.os.Bundle;

import com.example.Rand_Imgs_ScrollView.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    NestedScrollView scrollView;
    LinearLayout mainLayout;
    SeekBar mSeekBar;

    String url = "https://picsum.photos/1000/1000?";
    int imageCount = 1;
    Picasso mPicasso;
    ImageView img;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        mainLayout = (LinearLayout)findViewById(R.id.LinearLayout);
        scrollView = (NestedScrollView)findViewById(R.id.ScrollView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        text = (TextView) findViewById(R.id.seekBarNumber);
        text.setText(getResources().getString(R.string.seekBarNumber, "1"));

        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText(getResources().getString(R.string.seekBarNumber, Integer.toString(progress)));
                imageCount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Get a new image when user clicks the refresh button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateUserPreference(imageCount);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 0);
            }
        });

        // Get a new image when at the bottom of the view.
        scrollView.getViewTreeObserver().addOnScrollChangedListener
                (new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                if (!scrollView.canScrollVertically(1)) {
                    getImage();
                }
            }
        });

        // Populate the view with a couple of pics.
        //populate();
    }

    public void populate() {
        for (int i = 0; i < 3; i++) {
            getImage();
        }
    }

    public void populateUserPreference(int imgCount) {
        mainLayout.removeAllViews();
        for (int i = 0; i < imgCount; i++) {
            getImage();
        }
    }

    // Use Picasso to fetch image from the url.
    public void getImage() {
        double token = 0;
        token = Math.random();
        img = new ImageView(this);
        mainLayout.addView(img);
        mPicasso.get().load(url+token).networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE).into(img);
    }
}