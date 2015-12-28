package com.example.flux.android.stores;

import com.example.flux.android.actions.Action;
import com.squareup.otto.Bus;

/**
 * Flux的Store模块
 * Created by ntop on 18/12/15.
 */
public abstract class Store {
    private  static final Bus bus = new Bus();

    protected Store() {
    }
//让子类继承register、unregister、emitStoreChange这几个方法，子类可以直接调用这几个方法
    public void register(final Object view) {
        this.bus.register(view);
    }

    public void unregister(final Object view) {
        this.bus.unregister(view);
    }

    void emitStoreChange() {
        this.bus.post(changeEvent());
    }//调用下面的changeEvent方法

    //子类重写StoreChangeEvent、onAction
    public abstract StoreChangeEvent changeEvent();//返回类型是下面的内部类StoreChangeEvent
    public abstract void onAction(Action action);

    public class StoreChangeEvent {}//这里的内部类StoreChangeEvent有什么用？
                                    //难道这是EventBU用来标记是否有更新的标记？
}
