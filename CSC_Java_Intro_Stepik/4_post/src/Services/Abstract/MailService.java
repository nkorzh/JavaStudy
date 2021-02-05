package Services.Abstract;

import Entities.Abstract.Sendable;

public interface MailService {
        Sendable processMail(Sendable mail);
}
