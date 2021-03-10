package com.example.rxjava_buffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding4.view.RxView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

import static io.reactivex.android.schedulers.AndroidSchedulers.*;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable disposable = new CompositeDisposable();

    TextView textView ;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.subscirbeComment(viewModel.getComments());

        RxView.clicks(findViewById(R.id.getButton))
                .map(new Function<Unit, Integer>() {
                    @Override
                    public Integer apply(Unit unit) throws Throwable {
                        return 1;
                    }
                }).observeOn(mainThread())
                .buffer(4, TimeUnit.SECONDS)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("Subscirbed");
                    }

                    @Override
                    public void onNext(@NonNull List<Integer> integers) {
                        for (int i = 0; i < integers.size(); i++) {

                            System.out.println("Integer : " + integers);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("Error : " + e.getMessage() );
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Finished");
                    }
                })
    }

    private void makeToast(String message) {

        Toast.makeText(this , message , Toast.LENGTH_SHORT).show();
    }

    private void initView() {

        //initializations :

        textView = findViewById(R.id.txtv);
        button = findViewById(R.id.getButton);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposable.clear();
    }
}