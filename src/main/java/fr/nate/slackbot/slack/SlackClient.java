package fr.nate.slackbot.slack;

import lombok.Getter;
import lombok.val;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class SlackClient {

    @Getter
    private final PircBotX bot;

    public SlackClient(String host, int port, String user, String password) {
        val configuration = new Configuration.Builder()
                .addServer(host, port)
                .setName(user)
                .setServerPassword(password)
                .setSocketFactory(SSLSocketFactory.getDefault())
                .addListener(new SlackEventListener())
                .buildConfiguration();

        bot = new PircBotX(configuration);
    }

    public void start() throws IOException, IrcException {
        bot.startBot();
    }
}
