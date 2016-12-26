package fr.nate.slackbot.actions;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class SayHelloHandler implements ActionHandler {

    private static final Pattern CALL_PATTERN = compile("^hello$");

    @Override
    public boolean accept(String query) {
        return CALL_PATTERN.matcher(query).matches();
    }

    @Override
    public String respond(String query) {
        return ":wave:";
    }
}
