package de.hswhameln.typetogether.networking.shared.helperinterfaces;

/**
 * Interface for a function that has a functional meaning
 */
// TODO name may be changed later
@FunctionalInterface
public interface FunctionalFunction <T> {
    T apply() throws Exception;
}
