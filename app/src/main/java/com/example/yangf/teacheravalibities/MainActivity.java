package com.example.yangf.teacheravalibities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.example.yangf.teacheravalibities.DataManagement.MyFragmentPagerAdapter;
import com.example.yangf.teacheravalibities.fragments.Fragment1;
import com.example.yangf.teacheravalibities.fragments.Fragment2;
import com.example.yangf.teacheravalibities.fragments.Fragment3;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{

    ViewPager viewPager;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
        initTabHost();
    }

    private void initTabHost(){
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        String[] tabNames = {"Tab1", "Tab2", "Tab3"};

        for(int i = 0 ; i < tabNames.length ; i++){     //Adding Tabs
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec("Tab1");
            tabSpec.setIndicator("", GetTabImage(i));       //Replace Text with Image
            tabSpec.setContent(new FakeContent(getApplicationContext()));

            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);
    }

    private Drawable GetTabImage(int i){
        Resources res = getResources();

        switch(i){
            case 0:
                return res.getDrawable(R.drawable.tab1_selector);
            case 1:
                return res.getDrawable(R.drawable.tab2_selector);
            case 2:
                return res.getDrawable(R.drawable.tab3_selector);
        }

        return null;
    }

    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        List<Fragment> listOfFragments = new ArrayList<Fragment>();
        listOfFragments.add(new Fragment1());
        listOfFragments.add(new Fragment2());
        listOfFragments.add(new Fragment3());

        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listOfFragments);
        viewPager.setAdapter(myAdapter);

        viewPager.setOnPageChangeListener(this);
    }

    public class FakeContent implements TabHost.TabContentFactory{
        Context context;

        public FakeContent(Context context){
            this.context = context;
        }
        @Override
        public View createTabContent(String tag) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);

        HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);

        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft() - (hScrollView.getWidth() - tabView.getWidth() / 2);

        hScrollView.smoothScrollTo(scrollPos, 0);

        //Hide Keyboard and remove focus on the EditText, Search
        /*if(selectedItem != 0) {
            View view = this.getCurrentFocus();
            //EditText mySearchText = (EditText) findViewById(R.id.editSearch);

            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            //mySearchText.clearFocus();
        }*/
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
