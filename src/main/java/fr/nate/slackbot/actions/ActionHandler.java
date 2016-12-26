package fr.nate.slackbot.actions;

public interface ActionHandler {
    boolean accept(String query);

    String respond(String query);
}
