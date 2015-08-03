package com.ea.eamobile.nfsmw.utils;

import java.io.InputStream;

public interface HTTPBodyResolver<T> {
    public T solve(InputStream in);
}
