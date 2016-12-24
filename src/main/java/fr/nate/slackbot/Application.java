package fr.nate.slackbot;

import fr.nate.slackbot.slack.SlackClient;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, IrcException {
        new Application().run();
    }

    private void run() throws IOException, IrcException {
        System.out.println("hello");
        SlackClient slackClient = new SlackClient();
        slackClient.start();
    }
}
