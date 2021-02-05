package Services;

import Services.Abstract.MailService;
import Entities.Abstract.Sendable;

public class UntrustworthyMailWorker implements MailService {
    private final MailService[] services;
    private RealMailService realMailWorker;

    public UntrustworthyMailWorker(MailService[] services) {
        this.services = services;
        this.realMailWorker = null;
    }
    
    @Override
    public Sendable processMail(Sendable mail) {
        Sendable res = mail;
        for (MailService service : services) {
            res = service.processMail(res);
        }
        return getRealMailService().processMail(mail);
    }

    public MailService getRealMailService() {
        if (realMailWorker == null)
            realMailWorker = new RealMailService();
        return realMailWorker;
    }
}
