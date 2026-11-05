package mx.pm.sat.ape1.dtsbot.util;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class EvaluatorStaticValues {
    public static final BigDecimal LOW_LIMIT = BigDecimal.valueOf(0.5);
    public static final BigDecimal HIGH_LMIT = BigDecimal.valueOf(0.8);

    public static final String DOT_TXT = ".txt";
    public static final String DOT_DOCX = ".docx";
    public static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd_HHmm");
    public static final String UNDERSCORE = "_";
    public static final String PARENT_PATH = "results";
    public static final String CONFIRMED_PATH = "confirmed";
    public static final String SUSPICIOUS_PATH = "suspicious";
    public static final String UNCONFIRMED_PATH = "unconfirmed";
}
