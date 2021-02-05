package Services;

import Entities.MailPackage;
import Services.Abstract.MailService;
import Entities.Abstract.Sendable;
import Entities.MailMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Spy implements MailService {
    private Logger logger;
    public static final String AUSTIN_POWERS = "Austin Powers";

    public Spy(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Sendable processMail(Sendable mail) {
        if (mail instanceof MailMessage) {
            if (mail.getTo().equals(AUSTIN_POWERS) || mail.getFrom().equals(AUSTIN_POWERS)) {
                Object[] mailAttribs = {mail.getFrom(), mail.getTo(), ((MailMessage) mail).getMessage()};
                logger.log(Level.WARNING, "Detected target mail correspondence: from {0} to {1} \"{2}\"", mailAttribs);
            } else {
                Object[] mailAttribs = {mail.getFrom(), mail.getTo()};
                logger.log(Level.INFO, "Usual correspondence: from {0} to {1}", mailAttribs);
            }
        }
        return mail;
    }
}