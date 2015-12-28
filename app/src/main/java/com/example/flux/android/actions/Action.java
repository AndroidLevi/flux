package com.example.flux.android.actions;

/**
 * Created by ntop on 18/12/15.
 */
public class Action<T> {//Action的泛型类，让子类去继承
    private final String type;
    private final T data;

    Action(String type, T data) {
        this.type = type;
        this.data = data;
    }
//这两个方法子类会直接继承，拿过用
    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
