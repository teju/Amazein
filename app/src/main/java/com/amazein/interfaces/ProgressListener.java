package com.amazein.interfaces;

public interface ProgressListener {
    public abstract void onError(String s);
    public abstract void preExecute();
    public abstract void postExecute();

}
