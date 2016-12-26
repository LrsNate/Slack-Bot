package fr.nate.slackbot.slack;

import fr.nate.slackbot.actions.ActionHandler;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

@Slf4j
public class SlackClient extends ListenerAdapter {

    private static final Pattern CALL_PATTERN = compile("^!nb (.*)$");

    private final List<ActionHandler> actionHandlers;

    private final PircBotX bot;

    public SlackClient(String host, int port, String user, String password) {
        val configuration = new Configuration.Builder()
                .addServer(host, port)
                .setName(user)
                .setServerPassword(password)
                .setSocketFactory(SSLSocketFactory.getDefault())
                .addListener(this)
                .buildConfiguration();

        bot = new PircBotX(configuration);
        actionHandlers = new ArrayList<>();
    }

    public void start() throws IOException, IrcException {
        bot.startBot();
    }

    public void addHandler(ActionHandler handler) {
        actionHandlers.add(handler);
    }

    @Override
    public void onMessage(MessageEvent event) {
        String channel = event.getChannel().getName();
        if (!"#orbis_non_sufficit".equals(channel) && !"#nate-test".equals(channel)) return;
        val callMatcher = CALL_PATTERN.matcher(event.getMessage());
        if (!callMatcher.matches()) return;
        String command = callMatcher.group(1);

        Optional<ActionHandler> actionHandler = actionHandlers.stream().filter(x -> x.accept(command)).findFirst();
        String response = actionHandler.map(x -> x.respond(command)).orElse("J'ai pas compris :(");

        log.info(actionHandler.map(x -> x.getClass().getSimpleName()).orElse("No action found"));
        event.respondChannel(format("`Beep Boop!` %s", response));
    }
}
