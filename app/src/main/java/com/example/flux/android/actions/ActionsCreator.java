package com.example.flux.android.actions;

import com.example.flux.android.dispatcher.Dispatcher;

/**
 * Flux的ActionCreator模块
 * Created by ntop on 18/12/15.
 */
public class ActionsCreator {

    private static ActionsCreator instance;
    final Dispatcher dispatcher;

    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }//这里传入Dispatcher实例

    public static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    public void sendMessage(String message) {
        dispatcher.dispatch(new MessageAction(MessageAction.ACTION_NEW_MESSAGE, message));
    }
}
/**
 * 这里的做法是传入dispatcher的实例，直接在sendMessage()中进行action的分发
 * 而不是将生成的action传给diapatcher
 * 这里体现回调
 */
