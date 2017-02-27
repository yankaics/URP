package com.neau.urp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.neau.app.BaseActivity;
import com.neau.models.TzggModels;
import com.neau.util.UrpUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TzggActivity extends BaseActivity {
    private ListView listView;
    private List<String> lists;
    private ArrayAdapter<String> arrayAdapter;
    private List<TzggModels> list_tz;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            progressDialogDismiss();
            switch (msg.what) {
                case 1:
                    arrayAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Snackbar.make(listView, "获取数据失败", Snackbar.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzgg);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.lv_news);
        lists = new ArrayList<>();
        list_tz = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(TzggActivity.this, android.R.layout.simple_list_item_1, lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String urlclick;
                urlclick = list_tz.get(i).getUrl();
                System.out.println(urlclick);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(UrpUrl.URL_JWC + urlclick));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TzggActivity.this, list_tz.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        progressDialogShow();
        getInfo();
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                long start = System.currentTimeMillis();
                Document doc=null;
                try {

                    //String url = UrpUrl.URL_JWC + UrpUrl.URL_TZGG;;
                    String url = "http://jwc.neau.edu.cn/wejlist.jsp?a3t=20&a3p=1&a3c=100&urltype=tree.TreeTempUrl&wbtreeid=1888";

                    doc = Jsoup.connect(url).timeout(2000).ignoreHttpErrors(true).get();

                    Elements es = doc.select("[class=c60062]");
                    Elements date = doc.select("[class=timestyle60062]");
                    //Log.d("内容",es.toString());
                    //Log.d("日期，看看是否对应",date.toString());

                    for (int i = 0; i < es.size(); i++) {
                        Element element = es.get(i);
                        Element elementData = date.get(i);
                        //Log.d("内容", element.toString() + elementData.toString());
                        String linkHref = element.select("a").attr("href");
                        //Log.d("网址", linkHref);
                        String title = element.text() + "("+ elementData.text().substring(5,10) + ")";
                        lists.add(title);
                        TzggModels tzggModels = new TzggModels(title,linkHref);
                        list_tz.add(tzggModels);
                    }
                    handler.sendEmptyMessage(1);

                } catch (IOException e) {
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
                finally{
                    System.out.println("Time is:"+(System.currentTimeMillis()-start) + "ms");
                }
            }
        }.start();
    }
}
