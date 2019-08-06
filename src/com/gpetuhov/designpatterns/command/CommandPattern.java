package com.gpetuhov.designpatterns.command;

// Command

// Command interface
interface Command {
    void execute();
}

// Command for downloading
class Download implements Command {
    private Receiver receiver;

    public Download(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.Action();
    }
}

// Receiver interface
interface Receiver {
    void Action();
}

// Receiver for online mode
class OnlineReceiver implements Receiver {
    @Override
    public void Action() {
        System.out.println("Online mode. Downloading from network");
    }
}

// Receiver for offline mode
class OfflineReceiver implements Receiver {
    @Override
    public void Action() {
        System.out.println("Offline mode. Using cached data");
    }
}

// Client
public class CommandPattern {
    public static void main(String[] args) {
        Receiver online = new OnlineReceiver();
        Receiver offline = new OfflineReceiver();

        Command command = new Download(online);
        command.execute();
        // Out: Online mode. Downloading from network

        command = new Download(offline);
        command.execute();
        // Out: Offline mode. Using cached data
    }
}