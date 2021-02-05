package Services;

import Services.Abstract.MailService;
import Entities.Abstract.Sendable;
import Entities.MailPackage;
import Entities.Package;

public class Thief implements MailService {
    private int stolenValue;
    private int minCostToSteal;
    {
        stolenValue = 0;
    }

    public Thief(int minCostToSteal) {
        this.minCostToSteal = minCostToSteal;
    }

    public int getStolenValue() {
        return stolenValue;
    }

    @Override
    public Sendable processMail(Sendable mail) {
        if (mail instanceof MailPackage) {
            Package realPackage = ((MailPackage) mail).getContent();
            if (realPackage.getPrice() >= minCostToSteal) {
                stolenValue += realPackage.getPrice();

                Package fakePackage = new Package("stones instead of " + realPackage.getContent(), 0);
                return new MailPackage(mail.getFrom(), mail.getTo(), fakePackage);
            }
        }
        return mail;
    }
}
