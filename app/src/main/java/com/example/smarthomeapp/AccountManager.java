package com.example.smarthome;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class AccountManager {
    private static final String PREF_ACCOUNTS = "SmartHomeAccounts";
    private static final String KEY_ACCOUNTS   = "accounts";
    private static final String PREF_SESSION   = "SmartHomeSession";
    private static final String KEY_CURRENT    = "current_user";
    private static final String SEP            = "|||";

    /** Đăng ký – trả về false nếu email đã tồn tại */
    public static boolean register(Context ctx, String email, String password) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_ACCOUNTS, Context.MODE_PRIVATE);
        Set<String> accounts = new HashSet<>(prefs.getStringSet(KEY_ACCOUNTS, new HashSet<>()));
        for (String a : accounts) {
            if (a.split("\\|\\|\\|")[0].equalsIgnoreCase(email)) return false;
        }
        accounts.add(email + SEP + password);
        prefs.edit().putStringSet(KEY_ACCOUNTS, accounts).apply();
        return true;
    }

    /** Đăng nhập – trả về true nếu đúng tài khoản */
    public static boolean login(Context ctx, String email, String password) {
        Set<String> accounts = ctx.getSharedPreferences(PREF_ACCOUNTS, Context.MODE_PRIVATE)
                .getStringSet(KEY_ACCOUNTS, new HashSet<>());
        for (String a : accounts) {
            String[] p = a.split("\\|\\|\\|");
            if (p[0].equalsIgnoreCase(email) && p[1].equals(password)) return true;
        }
        return false;
    }

    /** Kiểm tra email có tồn tại không */
    public static boolean emailExists(Context ctx, String email) {
        Set<String> accounts = ctx.getSharedPreferences(PREF_ACCOUNTS, Context.MODE_PRIVATE)
                .getStringSet(KEY_ACCOUNTS, new HashSet<>());
        for (String a : accounts) {
            if (a.split("\\|\\|\\|")[0].equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    /** Đặt lại mật khẩu */
    public static boolean resetPassword(Context ctx, String email, String newPass) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_ACCOUNTS, Context.MODE_PRIVATE);
        Set<String> accounts = new HashSet<>(prefs.getStringSet(KEY_ACCOUNTS, new HashSet<>()));
        String found = null;
        for (String a : accounts) {
            if (a.split("\\|\\|\\|")[0].equalsIgnoreCase(email)) { found = a; break; }
        }
        if (found == null) return false;
        accounts.remove(found);
        accounts.add(email.toLowerCase() + SEP + newPass);
        prefs.edit().putStringSet(KEY_ACCOUNTS, accounts).apply();
        return true;
    }

    public static void saveCurrentUser(Context ctx, String email) {
        ctx.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE)
                .edit().putString(KEY_CURRENT, email).apply();
    }

    public static String getCurrentUser(Context ctx) {
        return ctx.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE)
                .getString(KEY_CURRENT, "Người dùng");
    }

    public static void logout(Context ctx) {
        ctx.getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE)
                .edit().remove(KEY_CURRENT).apply();
    }
}