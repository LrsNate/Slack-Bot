package fr.nate.slackbot.slack;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Slf4j
public class SlackEventListener extends ListenerAdapter {

    private static final Pattern CALL_PATTERN = compile("^Nate, (.*)$");

    @Override
    public void onMessage(MessageEvent event) {
        if (!"#orbis_non_sufficit".equals(event.getChannel().getName())) return;
        log.info(event.getChannel().getName());
        val callMatcher = CALL_PATTERN.matcher(event.getMessage());
        if (!callMatcher.find()) return;

        String command = callMatcher.group(1);

        if ("dis bonjour !".equals(command)) {
            event.respondChannel("`beep boop!` :wave:");
        }

        log.info(event.toString());
        log.info(event.getMessage());
    }
}
