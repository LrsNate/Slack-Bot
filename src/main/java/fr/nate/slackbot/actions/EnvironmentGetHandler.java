package fr.nate.slackbot.actions;

import fr.nate.slackbot.environment.Environment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentGetHandler implements ActionHandler {

    private static final Pattern CALL_PATTERN = compile("^get (\\w+)$");

    private final Environment environment;

    @Override
    public boolean accept(String query) {
        return CALL_PATTERN.matcher(query).matches();
    }

    @Override
    public String respond(String query) {
        Matcher matcher = CALL_PATTERN.matcher(query);
        checkArgument(matcher.matches());
        String key = matcher.group(1);
        String value = environment.get(key);

        return format("%s: %s", key, value != null ? value : "(null)");
    }
}
