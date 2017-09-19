package com.example.projecttopaz.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Youssef El Moumni on 5/17/16.
 */

public class NetworkRequest {
    private static Action1<? super Object> mOnAction = throwable -> {
    };
    private static Action1<Throwable> mOnError = throwable -> {
        Log.d("Error",throwable.toString());
    };
    private static Action0 mOnComplete = () -> {
    };

    public static <T> Subscription perform(Observable<T> observable) {
        return perform(observable, mOnAction, mOnError, mOnComplete);
    }

    public static <T> Subscription perform(Observable<T> observable, Action1<? super T> onAction) {
        return perform(observable, onAction, mOnError, mOnComplete);
    }

    public static <T> Subscription perform(Observable<T> observable, Action1<? super T> onAction, Action1<Throwable> onError) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onAction, onError, mOnComplete);
    }

    public static <T> Subscription perform(Observable<T> observable, Action1<? super T> onAction, Action1<Throwable> onError, Action0 onComplete) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onAction, onError, onComplete);
    }
    public static <T> Subscription performDelayed(Observable<T> observable, Action1<? super T> onAction, int delay) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onAction, mOnError, mOnComplete);
    }
}
