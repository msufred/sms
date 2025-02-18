package org.gemseeker.sms;

import java.nio.file.FileSystems;

public final class Utils {

    public static final String VERSION = "1.0.5";

    public static final String USER_HOME = System.getProperty("user.home");
    public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    public static final String APP_FOLDER = USER_HOME + FILE_SEPARATOR + "sms_app_v2";
    public static final String LOG_FOLDER = APP_FOLDER + FILE_SEPARATOR + "logs";
    public static final String DATA_FOLDER = APP_FOLDER + FILE_SEPARATOR + "data";
    public static final String IMAGE_FOLDER = APP_FOLDER + FILE_SEPARATOR + "images";
    public static final String EXPENSES_IMAGE_FOLDER = IMAGE_FOLDER + FILE_SEPARATOR + "expenses";
    public static final String REVENUES_IMAGE_FOLDER = IMAGE_FOLDER + FILE_SEPARATOR + "revenues";
    public static final String CASH_TRANSACTIONS_IMAGE_FOLDER = IMAGE_FOLDER + FILE_SEPARATOR + "cash_transactions";
    public static final String TEMP_FOLDER = APP_FOLDER + FILE_SEPARATOR + "temp";

    public static final String SETTINGS_PATH = APP_FOLDER + FILE_SEPARATOR + "settings.xml";
    public static final String SPLASH_MEDIA_PATH = APP_FOLDER + FILE_SEPARATOR + "splash.mp4";

    public static final String H2_DB_PATH = DATA_FOLDER + FILE_SEPARATOR + "sms_app.db";

    public static final String H2_PREFIX = "jdbc:h2:file:";
    public static final String MYSQL_PREFIX = "jdbc:mysql://";
}
