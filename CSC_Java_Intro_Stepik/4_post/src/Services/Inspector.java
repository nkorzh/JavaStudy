//package Services;
//
//import Entities.MailPackage;
//import Exceptions.IllegalPackageException;
//import Exceptions.StolenPackageException;
//import Services.Abstract.MailService;
//import Entities.Abstract.Sendable;
//
////public class Inspector implements MailService {
////    public static final String WEAPONS = "weapons";
////    public static final String BANNED_SUBSTANCE = "banned substance";
////    public static final String STOLEN_ATTRIBUTE = "stones";
////
////    @Override
////    public Sendable processMail(Sendable mail) {
////        String contentToCheck = "";
////        if (mail instanceof MailPackage)
////            contentToCheck = ((MailPackage) mail).getContent().getContent();
////
////        if (!contentToCheck.isEmpty()) {
//             separate method:
////            if (contentToCheck.contains(WEAPONS) || contentToCheck.contains(BANNED_SUBSTANCE))
////                throw new IllegalPackageException();
////            if (contentToCheck.contains(STOLEN_ATTRIBUTE))
////                throw new StolenPackageException();
////        }
////        return mail;
////    }
////}
