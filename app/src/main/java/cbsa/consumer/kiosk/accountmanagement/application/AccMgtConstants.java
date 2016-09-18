package cbsa.consumer.kiosk.accountmanagement.application;

/**
 * Account Management Constants
 */
public class AccMgtConstants {
    public static final String MONEY_FORMAT_CUSTOM = "###,##0";
    public static final String MONEY_FORMAT_DEFAULT = "###,##0.00";

    private AccMgtConstants() {
        //do nothing
    }

    public static class ApiCode {
        public static final int UNKNOWNS = -1;
        public static final int SUCCESS = 0;
        public static final int GENERAL = 99;

        private ApiCode() {
            // do nothing
        }
    }

    public static class AccountStatementType {
        public static final int LASTMONTH = 1;
        public static final int LAST2MONTH = 2;
        public static final int LAST3MONTH = 3;
        public static final int LAST6MONTH = 6;

        private AccountStatementType() {
            // do nothing
        }
    }
}
