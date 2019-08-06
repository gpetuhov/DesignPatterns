package com.gpetuhov.designpatterns.lazyeager;

// Lazy and Eager initialization

// Lazy initialization is done on demand,
// when the object is needed.
class Lazy {
    private static Lazy instance = null;

    public static Lazy getInstance() {
        if (instance == null) {
            instance = new Lazy();
        }

        return instance;
    }

    public String getType() {
        return "Lazy";
    }
}

// This is called eager initialization.
// Instance is created, when the class is loaded into the JVM.
class Eager {
    private static final Eager instance = new Eager();

    public static Eager getInstance() {
        return instance;
    }

    public String getType() {
        return "Eager";
    }
}

public class LazyEager {
    public static void main(String[] args) {
        System.out.println(Lazy.getInstance().getType());
        System.out.println(Eager.getInstance().getType());
    }
}