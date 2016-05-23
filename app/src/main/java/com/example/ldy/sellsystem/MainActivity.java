package com.example.ldy.sellsystem;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldy.sellsystem.layoutFragment.homePagefragment;
import com.example.ldy.sellsystem.layoutFragment.myOrderFragment;
import com.example.ldy.sellsystem.layoutFragment.settingFragment;
import com.example.ldy.sellsystem.layoutFragment.shoppingCarfragment;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static int ORANGECOLOR = 0;
    private static int GRAYCOLOR = 0;
    private homePagefragment homepage_fragment = new homePagefragment();
    private myOrderFragment myorder_fragment = new myOrderFragment();
    private settingFragment setting_fragment = new settingFragment();
    private shoppingCarfragment shoppingcar_fragment = new shoppingCarfragment();
    private LinearLayout homelayout;
    private LinearLayout shopingcarlayout;
    private LinearLayout myorderlayout;
    private LinearLayout settinglayout;
    private ImageView home_up_image;
    private ImageView shoppingcar_up_image;
    private ImageView myorder_up_image;
    private ImageView setting_up_image;
    private TextView home_down_tv;
    private TextView shoppingcar_down_tv;
    private TextView myorder_down_tv;
    private TextView setting_down_tv;
    ArrayList<android.app.Fragment> fragments = new ArrayList<>();//为了模拟返回时页面按按下时候跳转需要的结构
    ArrayList<ImageView> up_Images = new ArrayList<>();
    ArrayList<TextView> down_Texts = new ArrayList<>();
    private android.app.FragmentManager fragmentManger = getFragmentManager();
    private Stack<Integer> stack = new Stack<>();
    private Typeface typeface;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        typeface = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        GRAYCOLOR = ContextCompat.getColor(this, R.color.colorBottombarGray);
        ORANGECOLOR = ContextCompat.getColor(this, R.color.colorBottombarOrange);
        init();
        initFragments();
        initTextViews();
        initImageViews();
        initAttr();
        initEvents();


    }

    private void initEvents() {
        homelayout.setOnClickListener(this);
        myorderlayout.setOnClickListener(this);
        settinglayout.setOnClickListener(this);
        shopingcarlayout.setOnClickListener(this);
    }

    private void initAttr() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        shoppingcar_down_tv.setTypeface(this.typeface);
        setting_down_tv.setTypeface(this.typeface);
        myorder_down_tv.setTypeface(this.typeface);
        home_down_tv.setTypeface(this.typeface);
        fragmentManger.beginTransaction().add(R.id.frame_home_page,homepage_fragment).commit();
        replaceFragment(homepage_fragment);
        stack.clear();
        pushToStack(0);//这里的0表示主页index,0-3依次为:home,shoppingcar,myorder,setting
        initTabColor();
        home_up_image.setColorFilter(ORANGECOLOR);
        home_down_tv.setTextColor(ORANGECOLOR);

    }

    private long lastpushtime = Long.MAX_VALUE;//这两个变量是为了防止用户误触退出的,连续按两次才能退出
    private long currentpushtime;

    @Override
    public void onBackPressed() {
        if (stack.isEmpty()) {
            currentpushtime = System.currentTimeMillis();
            if (currentpushtime - lastpushtime >= 0 && currentpushtime - lastpushtime <= 1000) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                lastpushtime = currentpushtime;
            }
        } else {
            int top = stack.peek();
            stack.pop();
            replaceFragment(fragments.get(top));
            initTabColor();
            up_Images.get(top).setColorFilter(ORANGECOLOR);
            down_Texts.get(top).setTextColor(ORANGECOLOR);
            if(top==0)
                toolbar.setVisibility(View.VISIBLE);


        }
    }

    private void initTabColor() {

        home_down_tv.setTextColor(GRAYCOLOR);
        myorder_down_tv.setTextColor(GRAYCOLOR);
        setting_down_tv.setTextColor(GRAYCOLOR);
        shoppingcar_down_tv.setTextColor(GRAYCOLOR);
        home_up_image.clearColorFilter();
        myorder_up_image.clearColorFilter();
        setting_up_image.clearColorFilter();
        shoppingcar_up_image.clearColorFilter();
    }

    private void pushToStack(int i) {
        if (stack.isEmpty()) {
            stack.push(i);
        } else {
            int top = stack.peek();
            if (top == i) {

            } else {
                stack.push(i);

            }
        }
    }

    private void replaceFragment(Fragment t1) {//这个方法是为了点击底下按钮时候更改界面碎片
        fragmentManger.beginTransaction().replace(R.id.frame_home_page, t1).commit();

    }

    private void initImageViews() {
        up_Images.add(0, home_up_image);
        up_Images.add(1, shoppingcar_up_image);
        up_Images.add(2, myorder_up_image);
        up_Images.add(3, setting_up_image);
    }

    private void initTextViews() {
        down_Texts.add(0, home_down_tv);
        down_Texts.add(1, shoppingcar_down_tv);
        down_Texts.add(2, myorder_down_tv);
        down_Texts.add(3, setting_down_tv);
    }

    private void initFragments() {
        fragments.add(0, homepage_fragment);
        fragments.add(1, shoppingcar_fragment);
        fragments.add(2, myorder_fragment);
        fragments.add(3, setting_fragment);
    }

    private void init() {
        homelayout = (LinearLayout) findViewById(R.id.linear_homepage_home);
        shopingcarlayout = (LinearLayout) findViewById(R.id.linear_shoppingCar);
        myorderlayout = (LinearLayout) findViewById(R.id.linear_order);
        settinglayout = (LinearLayout) findViewById(R.id.linear_setting);
        home_up_image = (ImageView) findViewById(R.id.image_home_up);
        shoppingcar_up_image = (ImageView) findViewById(R.id.image_shoppingcar_up);
        myorder_up_image = (ImageView) findViewById(R.id.image_order_up);
        setting_up_image = (ImageView) findViewById(R.id.image_setting_up);
        home_down_tv = (TextView) findViewById(R.id.tv_homepage_home_fill_down);
        myorder_down_tv = (TextView) findViewById(R.id.tv_order_fill_down);
        setting_down_tv = (TextView) findViewById(R.id.tv_setting_fill_down);
        shoppingcar_down_tv = (TextView) findViewById(R.id.tv_homepage_shoppingcar_filldown);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_homepage_home:
                initTabColor();
                home_down_tv.setTextColor(ORANGECOLOR);
                home_up_image.setColorFilter(ORANGECOLOR);
                replaceFragment(fragments.get(0));
                toolbar.setVisibility(View.VISIBLE);
                stack.clear();
                pushToStack(0);
                break;
            case R.id.linear_shoppingCar:
                initTabColor();
                shoppingcar_down_tv.setTextColor(ORANGECOLOR);
                shoppingcar_up_image.setColorFilter(ORANGECOLOR);
                toolbar.setVisibility(View.GONE);
                replaceFragment(fragments.get(1));
                stack.push(1);
                break;
            case R.id.linear_order:
                initTabColor();
                toolbar.setVisibility(View.GONE);
                myorder_down_tv.setTextColor(ORANGECOLOR);
                myorder_up_image.setColorFilter(ORANGECOLOR);
                replaceFragment(fragments.get(2));
                stack.push(2);
                break;
            case R.id.linear_setting:
                initTabColor();
                toolbar.setVisibility(View.GONE);
                setting_down_tv.setTextColor(ORANGECOLOR);
                setting_up_image.setColorFilter(ORANGECOLOR);
                replaceFragment(fragments.get(3));
                stack.push(3);
                break;
            default:
                break;

        }

    }
}
