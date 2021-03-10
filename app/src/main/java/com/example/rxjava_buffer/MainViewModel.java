package com.example.rxjava_buffer;

import androidx.lifecycle.ViewModel;

import com.jakewharton.rxbinding4.view.RxView;

import org.jetbrains.annotations.NotNull;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableRange;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public Observable<Comment> getComments(){

        return new RetrofitHolder().getApi()
                .getComments()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Comment>, ObservableSource<Comment>>() {
                    @Override
                    public ObservableSource<Comment> apply(@NotNull List<Comment> comments) throws Exception {
                        return Observable.fromIterable(comments).subscribeOn(Schedulers.io());
                    }
                });
        }

    public void subscirbeComment(Observable<Comment> commentObservable){

        commentObservable.subscribe(new Observer<Comment>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

//                disposable.add(d);
                System.out.println("<< Started ");
            }

            @Override
            public void onNext(@NotNull Comment comment){

                System.out.println("Comment Name : " + comment.getName());
                System.out.println("Comment Id : " + comment.getId());
//                textView.append("Name : " + comment.getName());
//                textView.append("\n" + "Comment Id : " + comment.getId());
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("Error : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(" Finished >>");
            }
        });
    }


}
