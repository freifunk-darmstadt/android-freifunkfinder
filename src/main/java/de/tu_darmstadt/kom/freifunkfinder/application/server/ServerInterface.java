package de.tu_darmstadt.kom.freifunkfinder.application.server;

/**
 * Created by govind,sooraj,puneet on 12/17/2015.
 */
public interface ServerInterface<T> {

    T getRequest(String url);

    //int postRequest(T entity);
}
