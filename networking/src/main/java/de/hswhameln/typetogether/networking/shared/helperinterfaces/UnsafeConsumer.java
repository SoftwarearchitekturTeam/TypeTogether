package de.hswhameln.typetogether.networking.shared.helperinterfaces;

import java.io.IOException;

@FunctionalInterface
public interface UnsafeConsumer <T> {
    void accept(T t) throws IOException;
}
