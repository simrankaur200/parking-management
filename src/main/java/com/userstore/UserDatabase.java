package com.userstore;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    
    // This static map will act as our temporary user database 🗄️
    private static final Map<String, String> USERS = new HashMap<>();

    /**
     * Provides global access to our single list of users.
     */
    public static Map<String, String> getUsers() {
        return USERS;
    }
}