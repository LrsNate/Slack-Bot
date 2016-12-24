package fr.nate.slackbot.slack;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class SlackClient {


    private final PircBotX bot;

    public SlackClient() {
        Configuration configuration = new Configuration.Builder()
                .setName("nate")
                .setServerPassword("foo")
                .addServer("42born2code.irc.slack.com", 6667)
                .addAutoJoinChannel("#orbis-non-sufficit")
                .setSocketFactory(SSLSocketFactory.getDefault())
                .addListener(new SlackEventListener())
                .buildConfiguration();

        bot = new PircBotX(configuration);
    }

    public void start() throws IOException, IrcException {
        bot.startBot();
    }
}
