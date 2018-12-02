package com.syezdsultanov.observerproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);


        Observable<String> newObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                try {
                    mEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            emitter.onNext(s.toString());

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("TAG", "onNext: " + s);
                mTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onCompleted");
            }

        };

        newObservable.subscribe(observer);
    }
}
