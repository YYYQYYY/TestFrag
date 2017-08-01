package com.example.testfrag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testfrag.adapter.FragmentVPAdapter;
import com.example.testfrag.fragment.TestFm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private List<String> titleList = new ArrayList<>(); //标题链表
    private List<String> contentList = new ArrayList<>(); //内容链表
    private List<TestFm> fragmentList = new ArrayList<>(); //碎片链表
    private List<ImageView> mDots;//底部小圆点的集合
    private LinearLayout mLinearLayout;//声明将来放置底部小圆点的LinearLayout

    private int screenWidth; //屏幕宽度
    private ViewPager vp;
    private HorizontalScrollView scrollView;
    private List<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = findViewById(R.id.viewPager);
        scrollView = findViewById(R.id.scrollView);
        mLinearLayout = findViewById(R.id.viewpager_linerlayout);

        initList(); //初始化内容和标题
        initDots();

        //获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;

        //有多少个标题就有多少个碎片，动态添加
        for (int i = 0; i < titleList.size(); i++) {
            TestFm testFm = TestFm.newInstance(contentList, i);
            fragmentList.add(testFm);
        }

        //初始化导航栏布局
        LinearLayout navigationLl = new LinearLayout(this);
        LinearLayout.LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        navigationLl.setLayoutParams(mParams);
        navigationLl.setOrientation(LinearLayout.HORIZONTAL);
        navigationLl.setBackgroundColor(Color.GREEN);

        //往导航栏添加标题
        if (titleList.size() <= 3) { //标题栏小于4个时，平分屏幕宽度
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.weight = 1;
            for (int i = 0; i < titleList.size(); i++) {
                final TextView tv = new TextView(this);
                tv.setText(titleList.get(i));
                tv.setGravity(Gravity.CENTER);
                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv.setTextColor(Color.RED);
                        vp.setCurrentItem(finalI);
                    }
                });
                textViews.add(tv);
                navigationLl.addView(tv, params); //往导航栏添加标题
            }
        } else { //标题大于四个，重新规划textView大小
            LinearLayout.LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params1.width = 300;
            params1.height = LayoutParams.MATCH_PARENT;

            for (int i = 0; i < titleList.size(); i++) {
                final TextView tv = new TextView(this);
                tv.setText(titleList.get(i));
                tv.setGravity(Gravity.CENTER);
                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tv.setTextColor(Color.RED);
                        vp.setCurrentItem(finalI);
                    }
                });
                textViews.add(tv);
                navigationLl.addView(tv, params1);
            }
        }

        //第一个标题默认红色
        if (textViews != null && textViews.size() > 0) {
            textViews.get(0).setTextColor(Color.RED);
        }

        scrollView.addView(navigationLl); //往scrollView添加导航栏

        vp.setAdapter(new FragmentVPAdapter(getSupportFragmentManager(), (ArrayList<TestFm>) fragmentList));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setSelect(position);
                if (position >= 4) {
                    scrollView.scrollBy((int) (0.25 * screenWidth), 0);
                } else if (position < 4) {
                    scrollView.scrollBy(-(int) (0.25 * screenWidth), 0);
                }
                Toast.makeText(MainActivity.this, "selected" + (position + 1), Toast.LENGTH_SHORT).show();
                for (int i=0;i<titleList.size();i++){
                    //将所有的圆点设置为为选中时候的图片
                    mDots.get(i).setImageResource(R.drawable.vp_point_enable_false);
                }
                //将被选中的图片中的圆点设置为被选中的时候的图片
                mDots.get(position).setImageResource(R.drawable.vp_point_enable_true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initDots() {
        mDots = new ArrayList<ImageView>();//底部圆点集合的初始化
        for (int i = 0; i < titleList.size(); i++) {//根据界面数量动态添加圆点
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(35, 35));//设置ImageView的宽度和高度
            imageView.setPadding(5, 5, 5, 5);//设置圆点的Padding，与周围的距离
            imageView.setImageResource(R.drawable.vp_point_enable_false);//设置图片
            mDots.add(imageView);//将该图片添加到圆点集合中
            mLinearLayout.addView(imageView);//将图片添加到LinearLayout中
        }
        mDots.get(0).setImageResource(R.drawable.vp_point_enable_true);
    }

    public void setSelect(int position) {
        vp.setCurrentItem(position);
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setTextColor(Color.BLACK);
        }
        textViews.get(position).setTextColor(Color.RED);
    }

    public void initList() {
        //添加标题
        titleList.add("标题一");
        titleList.add("标题二");
        titleList.add("标题三");
        titleList.add("标题四");
        titleList.add("标题五");
        titleList.add("标题六");

        //添加内容
        contentList.add(
                "“”“”“”“”“”“”页面一\r\n" +
                        "\"\"\"\"\"\"\"\"\"\"\"页面一\r\n" +
                        "「」「」「」「」「」「」页面一\r\n" +
                        "页面一页面一页面一页面一页面一");
        contentList.add("页面二");
        contentList.add("页面三");
        contentList.add("页面四");
        contentList.add("页面五");
        contentList.add("页面六");
    }

    //activity 中小圆点的数量与页面的数量相同
//    public void xiaoyuandian() {
////小圆点与图片的数量一致
//        for (int i = 0; i < listimg.size(); i++) {
//// 创建imageView
//            ImageView imageView = new ImageView(this);
////小圆点的资源文件
//            imageView.setBackgroundResource(R.drawable.vp_point_bg_color);
//// LayoutParams对象的类型,取决于该控件的父控件类型
//            LayoutParams layoutparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
////设置右边距
//            layoutparams.rightMargin = 15;
////设置imageView属性
//            imageView.setLayoutParams(layoutparams);
//            if (i == 0) {
//                imageView.setEnabled(true);
//            } else {
//                imageView.setEnabled(false);
//            }
//            lin_xiaodian.addView(imageView);/////lin_xiaodian  小圆点的布局LinearLayout
//        }
//
//        //让小圆点与Viewpager页面进行联动
////获得正确的位置
//        int index=arg0%listimg.size();
////取出LinearLayout里当前的imageView
//        ImageView imageView = (ImageView) lin_xiaodian.getChildAt(index);
//        imageView.setEnabled(true);
//
//        ImageView lastimageView = (ImageView) lin_xiaodian.getChildAt(lastindex);
//        lastimageView.setEnabled(false);
//
//        lastindex=index;   //lastindex 全局变量   记录小圆点的位置
//    }

}
