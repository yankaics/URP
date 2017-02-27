package com.neau.urp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neau.adapter.MainAdapter;
import com.neau.app.BaseActivity;
import com.neau.models.MainModels;
import com.neau.util.ErrorCode;
import com.neau.util.HttpUtil;
import com.neau.util.UrpSpUtil;
import com.neau.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends BaseActivity {
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private TextView tv_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getInfo();
    }

    @Override
    protected void initView() {
        progressDialog.setMessage(getString(R.string.getUserData));
        progressDialog.show();
        /**
         * 初始化各种控件
         * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        listView = (ListView) findViewById(R.id.lv_main);
        if (navigationView != null) {
            tv_head = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_head);
        }


    }

    @Override
    protected void initData() {
        MainModels[] mainModels = new MainModels[6];
        mainModels[0] = new MainModels(R.mipmap.xjxx_2, getString(R.string.title_activity_xjxx));
        mainModels[1] = new MainModels(R.mipmap.cj_2, getString(R.string.cj));


        mainModels[2] = new MainModels(R.mipmap.kb_2, getString(R.string.bxqkb));
        mainModels[3] = new MainModels(R.mipmap.pj_2, getString(R.string.yjpj));
        mainModels[4] = new MainModels(R.mipmap.tzgg_2, getString(R.string.tzgg));
        mainModels[5] = new MainModels(R.mipmap.xfjd_2, getString(R.string.title_activity_kwgl));
        List<MainModels> lists = new ArrayList<>();
        Collections.addAll(lists, mainModels);

        MainAdapter adapter = new MainAdapter(lists, MainActivity.this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //学籍信息
                        start(XjxxActivity.class);
                        break;
                    case 1:
                        //成绩查询
                        start(CjActivity.class);
                        break;

                    case 2:
                        //本学期课表
                        Snackbar.make(listView, "开发中", Snackbar.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //一键评教
                        start(JxpgListActivity.class);
                        break;
                    case 4:
                        //通知公告
                        start(TzggActivity.class);
                        break;
                    case 5:
                        //考务管理
                        start(KwglActivity.class);
                        break;
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navItem1:
                        Snackbar.make(listView, "开发中", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem2:
                        // start(ChangeColor.class);
                        Snackbar.make(listView, "开发中", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.navItem3:
                        start(AboutActivity.class);
                        break;
                    case R.id.navItem4:
                        //退出
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.app_name).setMessage("退出登录").setNegativeButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UrpSpUtil.setCookie("");
                                UrpSpUtil.setAuto(false);
                                finish();
                                start(LoginActivity.class);
                            }
                        }).create().show();
                        break;
                }
                return false;
            }
        });
    }


    /**
     * 获取个人信息/姓名
     */
    public void getInfo() {
        HttpUtil.getXjxx(this, new JsoupCallBack() {
            @Override
            public void onSuccess(Document document) {
                progressDialogDismiss();
                Elements es = document.select("[width=275]");
                if (es.size() > 0) {
                    tv_head.setText(TextUtils.concat(es.get(1).text(), "  ", es.get(0).text()));
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    reLogin();
                }
            }

            @Override
            public void onError(ErrorCode errorCode, Response response) {
                super.onError(errorCode, response);
                progressDialogDismiss();
            }
        });
    }

    /**
     * 重新登录
     */
    private void reLogin() {
        UrpSpUtil.setAuto(false);
        start(LoginActivity.class);
        finish();
    }


    void start(Class cls) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, cls);
        startActivity(intent);
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Snackbar.make(listView, R.string.doubleBack, Snackbar.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)
                        ) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }

        return true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
