package com.gpetuhov.designpatterns.delegation;

// Delegation

// This is the one, that really does the job of making sound
class Sound {
    public String makeSound() {
        return "Meow";
    }
}

// This one delegates the job of making sound to the instance of the Sound class
class Animal {
    // This field is called the delegate object
    private Sound sound;

    Animal(Sound sound) {
        this.sound = sound;
    }

    public String makeSound() {
        // Here is the delegation
        return sound.makeSound();
    }
}

public class Delegation {
    public static void main(String[] args) {
        Animal animal = new Animal(new Sound());
        System.out.println(animal.makeSound());
    }
}