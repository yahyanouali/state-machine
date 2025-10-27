package com.demo.order;

import java.util.function.Supplier;

public interface BiTransitionTo<T, U> {
    T transitionToFirst(Supplier<T> first);
    U transitionToSecond(Supplier<U> second);
}