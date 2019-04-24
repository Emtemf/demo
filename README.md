# Android Studio引用github第三方库完成美观的侧滑菜单


![在这里插入图片描述](https://github.com/kurumi2501314/demo/动态展示.gif）

欢迎下载
https://github.com/kurumi2501314/demo/blob/master/demo.apk
# 实现步骤

引用的地址为
  https://github.com/mzule/FantasySlide
原文有使用介绍，这里将步骤详
## 1添加依赖

在app下的build.gradle的依赖位置进行添加，因为更新，所以请把  **compile** 改为
**implementation**
```javascript
// 在dependencies的范围里添加
 implementation 'com.github.mzule.fantasyslide:library:1.0.5'
```

 

## 2进行布局

如同原博主所言，可以把这个当做抽屉布局就可以了，演示就直接用他的例子了。图片偷懒也使用博主的例子了。侵删。
篇幅有限，这里贴关键部分。
```javascript
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.github.mzule.fantasyslide.FantasyDrawerLayout                                        
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/drawerLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

      

        <com.github.mzule.fantasyslide.SideBar
            android:id="@+id/leftSideBar"
            android:layout_width="200dp"
            android:layout_height="match_parent"
            android:layout_gravity="start"
            android:background="@color/colorPrimary"
            android:gravity="center_vertical"
            app:maxTranslationX="66dp">

            <LinearLayout
                android:id="@+id/userInfo"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginBottom="30dp"
                android:gravity="center_horizontal"
                android:onClick="onClick"
                android:orientation="vertical">

                <com.example.demo.widget.CircleimageView
                    android:layout_width="110dp"
                    android:layout_height="110dp"
                    android:layout_margin="10dp"
                    android:src="@drawable/me"
                    app:civ_border_color="@color/menu_text_color"
                    app:civ_border_width="2dp" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="昵称"
                    android:textColor="@color/menu_text_color"
                    android:textSize="18sp" />
            </LinearLayout>

            <TextView
                style="@style/MenuText1"
                android:drawableLeft="@drawable/circle"
                android:text="朋友圈" />

            <TextView
                style="@style/MenuText1"
                android:drawableLeft="@drawable/wallet"
                android:text="钱包" />

         
        </com.github.mzule.fantasyslide.SideBar>
        <com.github.mzule.fantasyslide.SideBar
            android:id="@+id/rightSideBar"
            android:layout_width="133dp"
            android:layout_height="match_parent"
            android:layout_gravity="end"
            android:background="@drawable/bg_right_bar"
            android:gravity="center_vertical|right"
            app:maxTranslationX="-33dp">

            <TextView
                style="@style/MenuText1"
                android:text="星期一" />

            <TextView
                style="@style/MenuText1"
                android:text="星期二" />

            <TextView
                style="@style/MenuText1"
                android:text="星期三" />

           
        </com.github.mzule.fantasyslide.SideBar>

    </com.github.mzule.fantasyslide.FantasyDrawerLayout>

    <TextView
        android:id="@+id/tipView"
        android:layout_width="160dp"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:background="@color/colorPrimary"
        android:gravity="center"
        android:padding="20dp"
        android:textColor="@color/colorAccent"
        android:textSize="16sp"
        android:visibility="invisible" />
</RelativeLayout>
```
重点是在侧滑菜单的部分，其中 **start** 是说这个是左滑的，右滑是**end**，这个请注意。
另外关于头像部分，原博主是调用了一个github上的一个圆形视图库，链接
https://github.com/hdodenhof/CircleImageView
这里是继承后改了些，详情可以去看原博主或者我Git里widget部分。
另外drawble后缀是说图片的比例尺寸。

## 3设置Listener来监听侧滑菜单
```javascript
    private void setListener() {
        final TextView tipView = (TextView) findViewById(R.id.tipView);
        SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
        leftSideBar.setFantasyListener(new SimpleFantasyListener() {
            @Override
            public boolean onHover(@Nullable View view, int index) {
                tipView.setVisibility(View.VISIBLE);
                if (view instanceof TextView) {
                    tipView.setText(String.format("%s at %d", ((TextView) view).getText().toString(), index));
                } else if (view != null && view.getId() == R.id.userInfo) {
                    tipView.setText(String.format("个人中心 at %d", index));
                } else {
                    tipView.setText(null);
                }
                return false;

            }
            @Override
            public boolean onSelect(View view, int index) {
                tipView.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, String.format("%d selected", index), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onCancel() {
                tipView.setVisibility(View.INVISIBLE);
            }
        });
    }
```
这里只是确认是不是点击，因为考虑到还有后续，所以将每个选项的点击后的反应放在第二个activity，这里只用变色就可以了。
关于第二个活动：
原博主第二个界面是调用了另一个github的库，地址如下
https://github.com/ikew0ng/SwipeBackLayout
所以总共需要添加三个
```javascript
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'com.github.mzule.fantasyslide:library:1.0.5'
    implementation 'de.hdodenhof:circleimageview:3.0.0'
    implementation 'me.imid.swipebacklayout.lib:library:1.1.0'
}
```
然后可以在这个位置通过对标题的确认来添加事件
```
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
        swipeBackActivityHelper = new SwipeBackActivityHelper(this);
        swipeBackActivityHelper.onActivityCreate();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra("title"));
       // if(getTitle().equals("xxx"));
    }
  ```
  注释的部分就是了，在那里通过比较不同的字符串标题来添加事件。



## 4Transformer的改变

原博主的实例特效是平移的，我这里将其改变为旋转，是在右边将这个特效做了实现。
效果如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190405191402852.gif)


欢迎下载
https://github.com/kurumi2501314/demo/blob/master/demo.apk

### 
