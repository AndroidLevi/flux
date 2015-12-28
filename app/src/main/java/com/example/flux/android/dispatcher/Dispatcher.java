package com.example.flux.android.dispatcher;

import com.example.flux.android.actions.Action;
import com.example.flux.android.stores.Store;

import java.util.ArrayList;
import java.util.List;;

/**
 * Flux的Dispatcher模块
 * Created by ntop on 18/12/15.
 */
public class Dispatcher {
    private static Dispatcher instance;
    private final List<Store> stores = new ArrayList<>();

    public static Dispatcher get() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    Dispatcher() {}

    //这里的注册只是添加到List集合，不是Strore类里面的EventBus的注册
    public void register(final Store store) {
        if (!stores.contains(store)) {
            stores.add(store);
        }
    }

    public void unregister(final Store store) {
        stores.remove(store);
    }

    public void dispatch(Action action) {
        post(action);
    }

    private void post(final Action action) {
        for (Store store : stores) {//注意这里的post会遍历Store集合里面的所有，话句话说，就是所有的
                                    //store都会发送action
            store.onAction(action);
        }
    }
}
