package com.neau.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neau.adapter.CjAdapter;
import com.neau.app.BaseFragment;
import com.neau.models.CjModels;
import com.neau.urp.R;
import com.neau.util.ErrorCode;
import com.neau.util.HttpUtil;
import com.neau.util.JsoupUtil;
import com.neau.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by jin123d on 2015/9/14.
 **/
public class CjJgFragment extends BaseFragment {
    private CjAdapter adapter;
    private List<CjModels> lists;

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_cjjg, container, false);
    }


    @Override
    protected void initView(View view) {
        ListView lv_zjsj = (ListView) view.findViewById(R.id.lv_zjsj);
        progressDialog.setMessage(getString(R.string.getJg));
        progressDialog.show();
        lists = new ArrayList<>();
        adapter = new CjAdapter(lists, getActivity());
        lv_zjsj.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getInfo();
    }


    void getInfo() {
        HttpUtil.getAllCjList(this, new JsoupCallBack() {
            @Override
            public void onSuccess(Document document) {
                progressDialogDismiss();
                lists.clear();
                lists.addAll(JsoupUtil.getAllCj(document));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(ErrorCode errorCode, Response response) {
                super.onError(errorCode, response);
                progressDialogDismiss();
            }
        });
    }

}
