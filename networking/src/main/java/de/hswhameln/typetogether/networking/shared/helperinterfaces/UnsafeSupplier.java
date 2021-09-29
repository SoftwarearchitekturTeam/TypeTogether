package de.hswhameln.typetogether.networking.shared.helperinterfaces;

import java.io.IOException;

@FunctionalInterface
public interface UnsafeSupplier<T> {
    T supply() throws IOException;
}
