/**
 * Class Journal
 *
 * @author cortisol
 */

import java.util.Arrays;
import java.util.Date;

public class Journal {

    /**
     * Class fields
     */
    private Record[] setOfRecords;

    /**
     * Index of cursor
     */
    private int index;

    /**
     * Constructors
     */
    public Journal() {
        setOfRecords = new Record[1];
        index = 0;
    }

    public Journal(int size) {
        setOfRecords = new Record[size];
        index = 0;
    }

    /**
     * Method adds Record to Journal, expands array and shifts cursor to next position
     *
     * @param record instance of Record class
     */
    public void add(Record record) {
        // check record
        checkRecord(record);
        // add record to th current position
        if (setOfRecords[index] == null) setOfRecords[index] = record;
        else {
            // expand the array if needed
            if (index + 1 >= setOfRecords.length) expandJournal(setOfRecords.length + 1);
            // add record to the next position
            setOfRecords[++index] = record;
        }
    }

    /**
     * Method adds Record from other Journal to this.Journal
     *
     * @param journal instance of Journal class
     * @throws IllegalArgumentException in case if other Journal is null
     */
    public void add(Journal journal) {
        if (journal != null) {
            for (Record record : journal.setOfRecords) add(record);
        } else throw new IllegalArgumentException("Journal cannot be null");
    }


    /**
     * Method removes one Record from this.Journal and rebuilds this.Journal
     *
     * @param record instance of Record class to be removed
     */
    public void remove(Record record) {
        checkRecord(record);
        for (int i = 0; i < this.setOfRecords.length; i++) {
            if (this.setOfRecords[i].equals(record)) {
                this.setOfRecords[i] = null;
                break;
            }
        }
        rebuildJournal();
    }

    /**
     * Method removes one Record with index from this.Journal and rebuilds this.Journal
     *
     * @param index int value of index
     * @throws IllegalArgumentException in checkIndex method in case of illegal index value
     */
    public void remove(int index) {
        checkIndex(index);
        this.setOfRecords[index] = null;
        rebuildJournal();
    }

    /**
     * Method removes Records in range 'fromIndex' to 'toIndex' from this.Journal and rebuilds this.Journal
     *
     * @param fromIndex int value of index
     * @param toIndex   int value of index
     * @throws IllegalArgumentException in checkIndex method in case of illegal index value
     */
    public void remove(int fromIndex, int toIndex) {
        checkIndex(fromIndex, toIndex);
        for (int i = fromIndex; i <= toIndex; i++) this.setOfRecords[i] = null;
        rebuildJournal();
    }

    /**
     * Method removes all Records from this.Journal and sets its size to 1 and index to 0
     */
    public void removeAll() {
        this.setOfRecords = new Record[1];
        this.index = 0;
    }

    /**
     * Method filters Records and returns new Journal with them
     *
     * @param strFilter String which contains values to be filtered
     * @return new Journal with filtered Records
     * @throws IllegalArgumentException in case of wrong Date values
     */
    public Journal filter(String strFilter) {
        if (strFilter != null && !strFilter.isEmpty()) {
            Journal filterJournal = new Journal();
            for (Record record : this.setOfRecords) {
                if (record.toString().toLowerCase().contains(strFilter.toLowerCase())) filterJournal.add(record);
            }
            return filterJournal;
        } else throw new IllegalArgumentException("Filter cannot be empty!");
    }

    /**
     * Method filters Records and returns new Journal with them starting from 'fromDate' to 'toDate'
     *
     * @param fromDate Date object
     * @param toDate   Date object
     * @return new Journal with filtered Records
     * @throws IllegalArgumentException in case of wrong Date values
     */
    public Journal filter(Date fromDate, Date toDate) {
        checkDate(fromDate);
        checkDate(toDate);
        if (fromDate.compareTo(toDate) < 0) {
            Journal filterJournal = new Journal();
            for (Record record : this.setOfRecords) {
                if (record.getDate().compareTo(fromDate) >= 0 && record.getDate().compareTo(toDate) <= 0)
                    filterJournal.add(record);
            }
            return filterJournal;
        } else throw new IllegalArgumentException("Date 'from' equals or less than date 'to'!");
    }

    public void sortByDate() {
    }

    public void sortByImportanceDate() {
    }

    public void sortByImportanceSourceDate() {
    }

    public void sortBySourceDate() {
    }

    /**
     * Set of helper methods
     */

    /**
     * Checks if index is in 0..this.index range
     *
     * @param index int value
     */
    private void checkIndex(int index) {
        if (index < 0 || index > this.index) throw new IllegalArgumentException("Wrong index value!");
    }

    /**
     * Checks if index is in 0..this.index range and fromIndex is less than toIndex
     *
     * @param fromIndex int value
     * @param toIndex   int value
     */
    private void checkIndex(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        if (fromIndex > toIndex) throw new IllegalArgumentException("Wrong index value!");
    }

    /**
     * Checks if Record is not null
     *
     * @param record Record object
     */
    private void checkRecord(Record record) {
        if (record == null) throw new IllegalArgumentException("Record cannot be null!");
    }

    /**
     * Expands size of this.Journal and copies content
     *
     * @param newSize int value
     */
    private void expandJournal(int newSize) {
        if (newSize < this.setOfRecords.length) throw new IllegalArgumentException("Wrong array size!");
        this.setOfRecords = Arrays.copyOf(this.setOfRecords, newSize);
    }

    /**
     * Checks if Date is not null
     *
     * @param date Date object
     */
    private void checkDate(Date date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
    }

    /**
     * Rebuilds journal: null values are ignored, not null copied to new Journal,
     * than new Journal stored in this.Journal
     */
    private void rebuildJournal() {
        Record[] tmpArray = new Record[this.setOfRecords.length];
        int tmpIndex = 0;
        for (int i = 0; i < this.setOfRecords.length; i++) {
            if (this.setOfRecords[i] != null) {
                tmpArray[tmpIndex] = this.setOfRecords[i];
                tmpIndex++;
            }
        }
        this.setOfRecords = Arrays.copyOf(tmpArray, tmpIndex);
        this.index = this.setOfRecords.length - 1;
    }

    /**
     * Overrides toString() method for proper representation of our Object
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Journal{" +
                "setOfRecords=" + Arrays.toString(setOfRecords) +
                ", index=" + index +
                '}';
    }
}
