package comparators;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    private String compareString;

    public StringComparator(String compareString) {
        this.compareString = compareString;
    }

    public void setCompareString(String compareString) {
        this.compareString = compareString;
    }

    public String getCompareString() {
        return this.compareString;
    }

    @Override
    public int compare(String stringOne, String stringTwo) {
        if (stringOne != null) {
            if (stringTwo != null) {
                if (this.compareString != null) {
                    int compareOne = stringOne.compareTo(this.compareString);
                    int compareTwo = stringTwo.compareTo(this.compareString);
                    if (compareOne > compareTwo) {
                        return 1;
                    }
                    else {
                        if (compareOne < compareTwo) {
                            return -1;
                        }
                        else {
                            return 0;
                        }
                    }
                } else {
                    return stringOne.compareTo(stringTwo);
                }
            }
        }
        return 0;
    }

}