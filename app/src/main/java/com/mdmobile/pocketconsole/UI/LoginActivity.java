package com.mdmobile.pocketconsole.UI;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.mdmobile.pocketconsole.Adapters.LogInViewPagerAdapter;
import com.mdmobile.pocketconsole.R;

public class LoginActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        viewPager = (ViewPager) findViewById(R.id.login_view_pager);
        TabLayout dotsIndicator = (TabLayout) findViewById(R.id.login_view_pager_dots_indicator);

        //Set adapter to viewPager and adjust margin between pages
        viewPager.setAdapter(new LogInViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageMargin(80);

        //Attach custom indicator to view pager
        dotsIndicator.setupWithViewPager(viewPager, true);

        //Adjust margin between tabs in tabLayout
        View dot;
        //Get dots container
        ViewGroup dotsContainer = (ViewGroup) dotsIndicator.getChildAt(0);
        //Instantiate margin to apply
        ViewGroup.MarginLayoutParams p;

        //Navigate through all the indicator dots and apply margins
        for (int i = 0; i < dotsIndicator.getTabCount(); i++) {
            dot = dotsContainer.getChildAt(i);
            p = (ViewGroup.MarginLayoutParams) dot.getLayoutParams();
            p.setMargins(5, 0, 5, 0);
            dot.requestLayout();
        }


    }


}
