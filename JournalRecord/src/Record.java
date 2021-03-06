/**
 * Class Record
 *
 * @author cortisol
 */

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Record implements Comparable {

    /**
     * Block of fields
     */
    private Date date;
    private int importance;
    private String source;
    private String errorMessage;

    /**
     * Block of constants
     */
    private static final int COUNT_OF_FIELDS = 4;
    private static final String SEPARATOR = " ";
    private static final String timePattern = "yyyy-MM-dd HH:mm:ss";
    private static final int IMPORTANCE_MIN_VALUE = 1;
    private static final int IMPORTANCE_MAX_VALUE = 4;

    /**
     * First constructor which gets parameters as separated values
     *
     * @param date         instance of Date class
     * @param importance   integer value in 1..4 range
     * @param source       String without spaces
     * @param errorMessage String which can contain spaces
     */
    public Record(Date date, int importance, String source, String errorMessage) {
        setParameters(date, importance, source, errorMessage);
    }

    /**
     * Second constructor which gets parameters as String value
     *
     * @param parametersAsString contains String as parameters "date importance source errorMessage"
     *                           'date' instance of Date class
     *                           'importance' integer value in 1..4 range
     *                           'source' String without spaces
     *                           'errorMessage' String which can contain spaces
     */
    public Record(String parametersAsString) {
        // get all the arguments as array of objects
        Object[] objParameters = parseParameters(parametersAsString);
        // and separate them
        Date date = (Date) objParameters[0];
        int importance = Integer.parseInt((String) objParameters[1]);
        String source = (String) objParameters[2];
        String errorMessage = (String) objParameters[3];
        // set the parameters
        setParameters(date, importance, source, errorMessage);
    }

    /**
     * Method checks and sets the fields into the values of arguments
     *
     * @param date         instance of Date class
     * @param importance   integer value in 1..4 range
     * @param source       String without spaces
     * @param errorMessage String which can contain spaces
     */
    public void setParameters(Date date, int importance, String source, String errorMessage) {
        checkParameters(date, importance, source, errorMessage);
        this.date = date;
        this.importance = importance;
        this.source = source.trim();
        this.errorMessage = errorMessage.trim();
    }

    /**
     * Method checks the values of arguments and throws an exception in case of error
     *
     * @param date         instance of Date class
     * @param importance   integer value in 1..4 range
     * @param source       String without spaces
     * @param errorMessage String which can contain spaces
     * @throws IllegalArgumentException in case of inappropriate values of arguments
     */
    private void checkParameters(Date date, int importance, String source, String errorMessage) {
        if (check(date)) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (check(importance)) {
            throw new IllegalArgumentException("Importance must be in " + IMPORTANCE_MIN_VALUE + ".." + IMPORTANCE_MAX_VALUE + " range!");
        }
        if (check(source)) {
            throw new IllegalArgumentException("Source cannot be empty!");
        }
        if (check(errorMessage)) {
            throw new IllegalArgumentException("Error message cannot be empty!");
        }
    }

    /**
     * Set of check methods
     */
    private boolean check(Date date) {
        return (date == null);
    }

    private boolean check(int importance) {
        return (importance < IMPORTANCE_MIN_VALUE || importance > IMPORTANCE_MAX_VALUE);
    }

    private boolean check(String source) {
        return (source == null || source.trim().equals(""));
    }

    /**
     * Method parses the String into the array of Objects
     *
     * @param parametersAsString contains String as parameters "111 2 aaa bbb_bbb"
     * @return array of Objects (Date, Integer, String, String)
     */
    private Object[] parseParameters(String parametersAsString) {
        // split the whole string
        String[] strParameters = parametersAsString.split(SEPARATOR);
        StringBuilder sb = new StringBuilder();
        int c = 0;
        for (String str : strParameters) {
            // ignore the empty strings,string which consists of space or CRLF
            if (!str.isEmpty() && !str.equals(SEPARATOR) && !str.contains("\n") && c < COUNT_OF_FIELDS) {
                sb.append(str);
                sb.append(SEPARATOR);
                c++;
            } else if (c >= COUNT_OF_FIELDS) {
                //starting from this index we don't care about spaces
                if (!str.contains("\n")) {
                    sb.append(str);
                } else {
                    sb.append(str.replace("\n", ""));
                }
                sb.append(SEPARATOR);
            }
        }
        strParameters = sb.toString().split(SEPARATOR);
        if (strParameters.length < (COUNT_OF_FIELDS + 1)) {
            throw new IllegalArgumentException("Too few arguments!");
        }
        // create an array of Object class
        Object[] objParameters = new Object[COUNT_OF_FIELDS];
        // the first parameter is the instance of Date class
        objParameters[0] = parseDate(strParameters[0] + SEPARATOR + strParameters[1]);
        // second and others - Integer and 2 String objects, where 3-th parameter cannot contain ' '
        for (int i = 1; i < COUNT_OF_FIELDS; i++) objParameters[i] = strParameters[i + 1];
        // if 'errorMessage' contains ' ', add the remained words to the last parameter
        if (strParameters.length > (COUNT_OF_FIELDS + 1)) {
            c = strParameters.length - (COUNT_OF_FIELDS + 1);
            while (c > 0) {
                objParameters[3] += (SEPARATOR + strParameters[strParameters.length - c]);
                c--;
            }
        }
        return objParameters;
    }

    /**
     * Method parses the String into the Date object
     *
     * @param strDate String contains date in a specified format 'timePattern'
     * @return Date object which contains parsed date
     * @throws IllegalArgumentException instead of ParseException;
     */
    private Date parseDate(String strDate) {
        try {
            return new SimpleDateFormat(timePattern).parse(strDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format!");
        }
    }

    /**
     * Method converts integer argument into its String representation
     *
     * @return String representation of importance field
     * @throws IllegalArgumentException in case of error value
     */
    private String getStatus(int importance) {
        switch (importance) {
            case 1:
                return ".    ";
            case 2:
                return "!    ";
            case 3:
                return "!!!  ";
            case 4:
                return "!!!!!";
            default:
                throw new IllegalArgumentException("Importance must be in 1..4 range!");
        }
    }

    /**
     * Overrides toString() method for proper representation of our Object
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return new SimpleDateFormat(timePattern).format(date) + SEPARATOR +
                getStatus(importance) + SEPARATOR +
                source + SEPARATOR +
                errorMessage;
    }

    /**
     * Method compares this.object to another
     *
     * @param otherRecord other instance of Record class
     * @return boolean result of comparing objects
     */
    public boolean equals(Record otherRecord) {
        if (otherRecord != null) {
            boolean eqDate = this.date.equals(otherRecord.date);
            boolean eqImportance = this.importance == otherRecord.importance;
            boolean eqSource = this.source.equals(otherRecord.source);
            boolean eqErrorMessage = this.errorMessage.equals(otherRecord.errorMessage);
            return (eqDate && eqImportance && eqSource && eqErrorMessage);
        }
        return false;
    }

    /**
     * @return Date object
     */
    Date getDate() {
        return (Date) this.date.clone();
    }

    /**
     * @return importace int value
     */
    int getImportance() {
        return this.importance;
    }

    /**
     * @return source String value
     */
    String getSource() {
        return this.source;
    }

    /**
     * @return errorMessage String value
     */
    String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * @param otherRecord
     * @return
     */
    @Override
    public int compareTo(Object otherRecord) {
        int eqDate = this.date.compareTo(((Record) otherRecord).date);
        int eqImportance = this.importance - ((Record) otherRecord).importance;
        int eqSource = this.source.compareTo(((Record) otherRecord).source);
        int eqErrorMessage = this.errorMessage.compareTo(((Record) otherRecord).errorMessage);
        if (this.equals((Record) otherRecord)) return 0;
        //else if (eqDate < 0 && eqSource < 0 && eqErrorMessage < 0 && eqImportance < 0) return 1;
        //else return -1;
        return 1;
    }
}
