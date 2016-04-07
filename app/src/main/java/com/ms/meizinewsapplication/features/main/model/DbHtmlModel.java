package com.ms.meizinewsapplication.features.main.model;

import android.app.Activity;

import com.ms.meizinewsapplication.features.base.model.DbModel;
import com.ms.meizinewsapplication.features.base.utils.tool.ConstantData;
import com.ms.meizinewsapplication.features.base.utils.tool.DebugUtil;
import com.ms.retrofitlibrary.util.RxJavaUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by 啟成 on 2016/4/5.
 */
public class DbHtmlModel extends DbModel {


    public DbHtmlModel(Activity mActivity) {
        super(mActivity);

        dbUtil.initHtmlDb(mActivity);
    }


    public void addDate(

            String url,
            String title,
            String html
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("title", title);
        map.put("html", html);

        Observable ob = Observable.just(map)
                .map(new Func1<Map<String, String>, Boolean>() {
                    @Override
                    public Boolean call(Map<String, String> stringMap) {

                        dbUtil.addHtml(
                                stringMap.get("url"),
                                ConstantData.DB_HTML_TYPE_ADD,
                                stringMap.get("title"),
                                stringMap.get("html")
                        );
                        return true;
                    }
                });
        RxJavaUtil.rxIoAndMain(ob, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                DebugUtil.debugLogErr(e, "DbHtmlModel+++++\n" + e.toString());
            }

            @Override
            public void onNext(Boolean aBoolean) {

                DebugUtil.debugLogD("DbHtmlModel+++++\n" + aBoolean);
            }

        });

    }

}
