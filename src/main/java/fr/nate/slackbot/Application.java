package fr.nate.slackbot;

import fr.nate.slackbot.actions.DescribeSynodeHandler;
import fr.nate.slackbot.actions.EnvironmentGetHandler;
import fr.nate.slackbot.actions.EnvironmentSetHandler;
import fr.nate.slackbot.actions.SayHelloHandler;
import fr.nate.slackbot.slack.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.pircbotx.exception.IrcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Application {

    private final SlackClient slackClient;

    private final SayHelloHandler sayHelloHandler;
    private final EnvironmentGetHandler environmentGetHandler;
    private final EnvironmentSetHandler environmentSetHandler;
    private final DescribeSynodeHandler describeSynodeHandler;

    public static void main(String[] args) throws IOException, IrcException {
        val applicationContext = new ClassPathXmlApplicationContext("spring-objects.xml");
        applicationContext.getBean(Application.class).run();
    }

    private void run() throws IOException, IrcException {
        slackClient.addHandler(sayHelloHandler);
        slackClient.addHandler(environmentGetHandler);
        slackClient.addHandler(environmentSetHandler);
        slackClient.addHandler(describeSynodeHandler);
        slackClient.start();
    }
}
