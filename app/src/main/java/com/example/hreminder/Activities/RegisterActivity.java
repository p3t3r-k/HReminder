package com.example.hreminder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hreminder.Database.HRepository;
import com.example.hreminder.Database.IDataSource;
import com.example.hreminder.Local.CreateDatabase;
import com.example.hreminder.Local.ReminderDAO;
import com.example.hreminder.Local.ReminderDataSource;
import com.example.hreminder.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    private ListView lst;
    private Button buttonRegister;

    private CompositeDisposable compositeDisposable;
    private HRepository hRepository;


    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //lst = (ListView)findViewById(R.id.lst);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);


        compositeDisposable = new CompositeDisposable();

        //com.example.hreminder.Activities.Database
        CreateDatabase createDatabase = CreateDatabase.getInstance(this);
        hRepository = HRepository.getInstance(ReminderDataSource.getInstance(createDatabase.reminderDAO()));

        loadData();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        Database database = new Database();
                        hRepository.insertDB(database);
                        e.onComplete();
                    }

                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                                       @Override
                                       public void accept(Object o) throws Exception {
                                           Toast.makeText(RegisterActivity.this, "User added", Toast.LENGTH_SHORT).show();
                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           Toast.makeText(RegisterActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                       }

                                   }, new Action() {
                                       @Override
                                       public void run() throws Exception {
                                           loadData();
                                       }
                                   }
                        );
            }
        });

    }

    private void loadData() {
        //Use RxJava
        Disposable disposable = hRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Database>>() {
                    @Override
                    public void accept(List<Database> databases) throws Exception {
                        onGetAllUserSuccess(databases);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RegisterActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);

    }

    private void onGetAllUserSuccess(List<Database> databases) {

        adapter.notifyDataSetChanged();

    }

    public void onClickSwitchToFingerprint(View view) {
        Intent intent = new Intent(this, Fingerprint_login.class);
        startActivity(intent);
        finish();
    }
}
