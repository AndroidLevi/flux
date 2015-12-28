package com.example.flux.android.stores;

import com.example.flux.android.actions.Action;
import com.example.flux.android.actions.MessageAction;
import com.example.flux.android.model.Message;
import com.squareup.otto.Subscribe;

/**
 * MessageStore类主要用来维护MainActivity的UI状态
 * Created by ntop on 18/12/15.
 */
public class MessageStore extends Store {
    private static MessageStore singleton;
    private Message mMessage = new Message();

    public MessageStore() {
        super();
    }//调用父类的构造方法，初始化Store里面的EventBus静态实例

    public String getMessage() {
        return mMessage.getMessage();
    }//向外界暴露Message的get方法，
                                                                // 注意这里没有set方法，因为Message的数据改变
                                                //只能通过dispatcher来更新，在onAction里面set新的message

    @Override
    @Subscribe//这里的Subscribe可以不要，还是可以正常运行，的达到同样的效果。为什么要写上呢？？
    public void onAction(Action action) {
        switch (action.getType()) {
            case MessageAction.ACTION_NEW_MESSAGE:
                mMessage.setMessage((String) action.getData());//更新message的数据
                break;
            default:
        }
        emitStoreChange();//调用父类的方法，让订阅了的View拿到更新的message来刷新UI
    }


    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }
}
