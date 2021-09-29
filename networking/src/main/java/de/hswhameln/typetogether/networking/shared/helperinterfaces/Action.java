package de.hswhameln.typetogether.networking.shared.helperinterfaces;

import java.io.IOException;

@FunctionalInterface
public interface Action {
    void perform() throws IOException;
}
