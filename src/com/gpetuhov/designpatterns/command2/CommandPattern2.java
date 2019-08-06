package com.gpetuhov.designpatterns.command2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Command

// --- COMMANDS ---------

// All commands must have execute() method,
// that describes specific command actions.
interface Command {
    void execute();
}

// First command
class Print implements Command {
    @Override
    public void execute() {
        System.out.print("Print ");
    }
}

// Second command
class Some implements Command {
    @Override
    public void execute() {
        System.out.print("some ");
    }
}

// Third command
class Word implements Command {
    @Override
    public void execute() {
        System.out.print("word.");
    }
}


// --- EXECUTOR ---------

// Executes list of commands by calling execute() method of each command in the list
class CommandExecutor {

    public void execute(List<Command> commands) {
        if ( commands != null ) {
            for ( Command command : commands ) {
                command.execute();
            }
        }
    }
}


// --- BUILDER ---------

// Second level of abstraction above commands for their easy usage
enum Action {
    PRINT,
    SOME,
    WORD
}

// Takes list of actions and builds them into list of commands.
// Simplifies creation of command instances
class CommandBuilder {

    // Input list of actions
    private List<Action> actions;

    // Output list of commands
    private List<Command> commands;

    public CommandBuilder() {
        actions = new ArrayList<>();
        commands = new ArrayList<>();
    }

    // Call this to add actions to the input list of actions
    public CommandBuilder add(Action[] actions) {
        if ( actions != null ) {
            this.actions.addAll(Arrays.asList( actions ) );
        }

        return this;
    }

    // Transforms list of actions into list of commands
    public List<Command> build() {
        for ( Action action : actions ) {
            addCommand( action );
        }

        return commands;
    }

    // Creates command instance depending on input action and adds to list
    private void addCommand(Action action) {
        Command command;

        switch (action) {
            case PRINT:
                command = new Print();
                break;

            case SOME:
                command = new Some();
                break;

            case WORD:
                command = new Word();
                break;

            default:
                command = new Print();
                break;
        }

        commands.add( command );
    }
}


// --- MAIN ---------

public class CommandPattern2 {

    public static void main(String[] args) {

        // Build list of commands
        List<Command> commands = new CommandBuilder()
                .add( new Action[]{ Action.PRINT, Action.SOME, Action.WORD } )
                .build();

        // Execute list of commands
        new CommandExecutor().execute( commands );

        // Output: Print some word.
    }
}