package de.hswhameln.typetogether.networking.shared.helperinterfaces;

import de.hswhameln.typetogether.networking.api.exceptions.FunctionalException;

import java.io.IOException;

@FunctionalInterface
public interface ProxiedSupplier <T> {
    T supply() throws IOException, FunctionalException;
}
