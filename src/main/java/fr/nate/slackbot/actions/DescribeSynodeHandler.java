package fr.nate.slackbot.actions;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import fr.nate.slackbot.environment.Environment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Collections.sort;
import static java.util.Locale.FRANCE;
import static java.util.Locale.US;
import static java.util.regex.Pattern.compile;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DescribeSynodeHandler implements ActionHandler {

    private static final Pattern CMD_PATTERN = compile("^synode$");
    private final Environment environment;


    @Override
    public boolean accept(String query) {
        return CMD_PATTERN.matcher(query).find();
    }

    @Override
    public String respond(String query) {
        String synodeId = environment.get("synodeId");
        try {
            JBrowserDriver browserDriver = new JBrowserDriver();
            browserDriver.get(format("http://doodle.com/poll/%s", synodeId));
            Document document = Jsoup.parse(browserDriver.getPageSource());
            Synode synode = readFromDoodle(document);
            return synode.toString();
        } catch (Exception e) {
            log.warn(format("Something went wrong while getting Doodle ID: %s", synodeId), e);
            return "Whoops ! J'ai eu un problème. Demande à Nate de regarder ses logs.";
        }
    }

    private Synode readFromDoodle(Document document) throws ParseException {
        String title = document.body().select("h2#pollTitle").first().html();
        Synode result = new Synode(title);
        for (Element cell : document.body().select("td.partTableCell")) {
            result.addResponse(cell.attr("title"));
        }
        return result;
    }

    private static class Synode {

        private static final Pattern RESPONSE_PATTERN = compile("^(\\w+), ([A-Za-z0-9 /]+): (\\w+)$");

        private static final DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("EEE M/d/y", US);
        private static final DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("EEEE dd/MM/yyyy", FRANCE);

        private String title;

        private Map<Date, List<String>> responses;

        private Synode(String title) {
            this.title = title;
            responses = new HashMap<>();
        }

        private void addResponse(String response) throws ParseException {
            Matcher matcher = RESPONSE_PATTERN.matcher(response);
            checkArgument(matcher.matches());
            String user = matcher.group(1);
            Date date = INPUT_DATE_FORMAT.parse(matcher.group(2));
            String status = matcher.group(3);
            if (!responses.containsKey(date)) {
                responses.put(date, new ArrayList<>());
            }
            if ("No".equals(status)) return;
            responses.get(date).add("Ifneedbe".equals(status) ? format("(%s)", user) : user);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(format("Synode en cours : %s\n", title));
            List<Date> dates = new ArrayList<>(responses.keySet());
            sort(dates);
            for (Date date : dates) {
                String attendees = on(", ").join(responses.get(date));
                builder.append(format("%s: %s\n", OUTPUT_DATE_FORMAT.format(date), attendees.isEmpty() ? "(personne)" : attendees));
            }
            return builder.toString();
        }
    }
}
