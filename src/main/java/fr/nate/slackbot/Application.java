package fr.nate.slackbot;

import fr.nate.slackbot.slack.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.pircbotx.exception.IrcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Application {

    private final SlackClient slackClient;

    public static void main(String[] args) throws IOException, IrcException {
        val applicationContext = new ClassPathXmlApplicationContext("spring-objects.xml");
        applicationContext.getBean(Application.class).run();
    }

    private void run() throws IOException, IrcException {
        slackClient.start();
    }
}
