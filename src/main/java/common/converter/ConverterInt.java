package common.converter;

/**
 * Created by govind on 12/11/2015.
 */
public interface ConverterInt<T, D> {

    // convert T to D
    D serialize(T entity) throws Exception;

    // convert D to T
    T deSerialize(D entity) throws Exception;
}
