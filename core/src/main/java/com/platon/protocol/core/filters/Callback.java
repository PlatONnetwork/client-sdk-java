package com.platon.protocol.core.filters;

/**
 * Filter callback interface.
 */
public interface Callback<T> {
    void onEvent(T value);
}
