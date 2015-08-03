package com.ea.eamobile.nfsmw.action;

import com.ea.eamobile.nfsmw.model.User;

public class NfsThreadLocal {

    private static final ThreadLocal<User> tlUser = new ThreadLocal<User>();

    public static User getUser() {
        return tlUser.get();
    }

    public static void set(User user) {
        if (user != null)
            tlUser.set(user);
    }

    public static void clear() {
        tlUser.remove();
    }
}
