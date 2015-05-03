package cz.cvut.nss.utils;

/**
 * DateTime utils.
 *
 * @author jakubchalupa
 * @since 29.11.14
 */
public class DateTimeUtils {

    public static final String dateTimePattern = "dd.MM.yyyy HH:mm";

    public static final String datePattern = "dd.MM.yyyy";

    public static final String timePattern = "HH:mm";

    public static final String noDelimiterEnDatePattern = "yyyyMMdd";

    public static final String timeWithSecondsPattern = "HH:mm:ss";

    /**
     * pocet milisekund za 24 hodin
     */
    public static final int MILLIS_IN_DAY = 86400000;

    /**
     * 5 minut penalizace za prestup
     */
    public static final int TRANSFER_PENALTY_MILLIS = 300000;

}
