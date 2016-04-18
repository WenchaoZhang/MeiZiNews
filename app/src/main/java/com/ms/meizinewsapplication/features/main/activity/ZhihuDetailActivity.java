package com.ms.meizinewsapplication.features.main.activity;

import android.os.Build;
import android.os.Bundle;

import com.ms.meizinewsapplication.features.base.activity.BaseActivityPresenterImpl;
import com.ms.meizinewsapplication.features.base.utils.tool.ConstantData;
import com.ms.meizinewsapplication.features.main.iview.ZhiHuDetailIView;
import com.ms.meizinewsapplication.features.main.json.ZhihuDetail;
import com.ms.meizinewsapplication.features.main.model.DbHtmlModel;
import com.ms.meizinewsapplication.features.main.model.ZhihuDetailModel;
import com.ms.meizinewsapplication.features.base.utils.tool.ZhiHuConstants;

import org.loader.model.OnModelListener;

/**
 * Created by 啟成 on 2016/3/10.
 */
public class ZhihuDetailActivity extends BaseActivityPresenterImpl<ZhiHuDetailIView> {

    private DbHtmlModel dbHtmlModel;
    private ZhihuDetailModel zhihuDetailModel;
    private boolean isCollect = false;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.init(this);
        initDbHtmlModel();
        initZhihuDetailModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mView.onResume();
    }


    @Override
    public void onBackPressed() {

        mView.onBackPressed();
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }

    //TODO Model=====================================

    private void initZhihuDetailModel() {
        zhihuDetailModel = new ZhihuDetailModel();
        addSubscription(

                zhihuDetailModel.loadWeb(
                        ZhihuDetailActivity.this,
                        zhihuDetailOnModelListener,
                        getIntent().getIntExtra(ZhiHuConstants.ID, 0) + ""
                )
        );
    }

    private void initDbHtmlModel() {
        dbHtmlModel = new DbHtmlModel(ZhihuDetailActivity.this);
    }

    private void addDbHtmlDate(ZhihuDetail zhihuDetail) {


        String strCollect;
        if (isCollect) {
            strCollect = ConstantData.DB_HTML_COLLECT_YES;
        } else {

            strCollect = ConstantData.DB_HTML_COLLECT_NO;
        }


        dbHtmlModel.addDate(
                zhihuDetail.getShare_url(),
                ConstantData.DB_HTML_TYPE_ZHIHU,
                zhihuDetail.getTitle(),
                zhihuDetail.getBody(),
                " ",
                strCollect
        );
    }

    //TODO Listener ===================================================

    OnModelListener<ZhihuDetail> zhihuDetailOnModelListener = new OnModelListener<ZhihuDetail>() {
        @Override
        public void onSuccess(ZhihuDetail zhihuDetail) {
            mView.initImg(ZhihuDetailActivity.this, zhihuDetail.getImage());
            mView.showDetail(zhihuDetail);
            addDbHtmlDate(zhihuDetail);
        }

        @Override
        public void onError(String err) {
            mView.progressGone();
        }

        @Override
        public void onCompleted() {

        }
    };
}
