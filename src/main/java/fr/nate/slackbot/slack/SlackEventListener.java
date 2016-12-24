package fr.nate.slackbot.slack;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class SlackEventListener extends ListenerAdapter {
    @Override
    public void onMessage(MessageEvent event) {
        System.out.println(event.getMessage());
    }
}
