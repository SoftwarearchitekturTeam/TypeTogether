package de.hswhameln.typetogether.networking.util;

public class ExceptionUtil {
    @SuppressWarnings("unchecked")
    public static <T extends Exception> RuntimeException sneakyThrow(Exception e) throws T {
        throw (T) e;
    }

}
