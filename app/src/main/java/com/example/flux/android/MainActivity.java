package com.example.flux.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flux.android.actions.ActionsCreator;
import com.example.flux.android.dispatcher.Dispatcher;
import com.example.flux.android.stores.MessageStore;
import com.example.flux.android.stores.Store;
import com.squareup.otto.Subscribe;

/**
 * Flux的Controller-View模块
 * Created by ntop on 18/12/15.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText vMessageEditor;
    private Button vMessageButton;
    private TextView vMessageView;

    private Dispatcher dispatcher;//持有dispatcher的引用是为了添加Store
    private ActionsCreator actionsCreator;
    private MessageStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDependencies();
        setupView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dispatcher.unregister(store);
    }

    private void initDependencies() {
        dispatcher = Dispatcher.get();//获得dispatvher实例
        actionsCreator = ActionsCreator.get(dispatcher);//将dispatcher传给actionCreater，待会一座回调
        store = new MessageStore();
        dispatcher.register(store);
    }

    private void setupView() {
        vMessageEditor = (EditText) findViewById(R.id.message_editor);
        vMessageView = (TextView) findViewById(R.id.message_view);
        vMessageButton = (Button) findViewById(R.id.message_button);
        vMessageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.message_button) {
            if (vMessageEditor.getText() != null) {
                actionsCreator.sendMessage(vMessageEditor.getText().toString());
                vMessageEditor.setText(null);
            }
        }
    }
//这里应该会根据判断store的种类不同，来跟新不同的View控件
    private void render(MessageStore store) {
        vMessageView.setText(store.getMessage());
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);//在onResume而不是在OnStart里面注册EventBus，因为当activity有交互能力的时候
                            //注册EventBUS才有意义，因为activity没有交互能力的话，也就没有更新UI能力
                            //所以计算有订阅的书更新了，还是不能更新界面
    }

    @Override
    protected void onPause() {
        super.onPause();
        store.unregister(this);//对应上面的在OnPause注册EventBus，在activity没有交互更新UI的能力，就应该取消订阅
    }

    @Subscribe//这里的订阅注解要自己手动标记，
                // 订阅eventbus是否有新的StoreChangeEvent实例返回，以作为更新的标记？
                //网友的解释：在Store的变更事件会传递一个参数ChangeEvent，这个参数可以用来细力度的UI控制
    public void onStoreChange(Store.StoreChangeEvent event) {
        render(store);

  //啦啦啦
    }
}
