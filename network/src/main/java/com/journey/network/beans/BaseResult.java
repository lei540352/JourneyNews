package com.journey.network.beans;

public class BaseResult<T> extends BaseResponse{
    public T data;
    public int ID;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
