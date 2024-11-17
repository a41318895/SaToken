package com.akichou.satokentest.functionalInterface;

// Functional interface to handle the Exception throwing of using RSAUtils methods
@FunctionalInterface
public interface Supplier<V> {

    V get() throws Exception ;
}
