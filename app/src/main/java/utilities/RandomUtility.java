package utilities;

import java.util.Arrays;
import java.util.Random;

public class RandomUtility {

    private final static String[] THIRTYONE_DAYS_SET = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private final static String[] THIRTY_DAYS_SET = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private final static String[] FEBRUARY_DAYS_SET = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    private final static String[] LEAP_YEAR_FEBRUARY_DAYS_SET = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    private final static String[] MONTHS = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private final static String[] THIRTYONE_DAYS_MONTHS = {"01", "03", "05", "07", "08", "10", "12"};
    private final static String DEFAULT_YEAR = "2019";
    private final static int LEAP_YEAR_DIVISOR = 4;
    private final static String FEBRUARY = "02";
    private final static double PLUS_DECIMAL = 0.9999999999;

    private final Random random = new Random();
    private final StringBuilder stringBuilder = new StringBuilder();

    public Random getRandom() {
        return this.random;
    }

    public StringBuilder getStringBuilder() {
        return this.stringBuilder;
    }

    public String getRandomYYYYMMDDDate(String year) {
        int yearInt = 0;
        try {
            yearInt = Integer.parseInt(year);
        } catch (Exception exception) {
            year = RandomUtility.DEFAULT_YEAR;
        }
        String month = this.selectFrom(RandomUtility.MONTHS);
        String day;
        if (month.compareTo(RandomUtility.FEBRUARY) == 0) {
            if (yearInt % RandomUtility.LEAP_YEAR_DIVISOR == 0) {
                day = this.selectFrom(RandomUtility.LEAP_YEAR_FEBRUARY_DAYS_SET);
            } else {
                day = this.selectFrom(RandomUtility.FEBRUARY_DAYS_SET);
            }
        } else {
            if (Arrays.binarySearch(RandomUtility.THIRTYONE_DAYS_MONTHS, month) >= 0) {
                day = this.selectFrom(RandomUtility.THIRTYONE_DAYS_SET);
            } else {
                day = this.selectFrom(RandomUtility.THIRTY_DAYS_SET);
            }
        }
        String date = year + month + day;
        return date;
    }

    public String getRandomHourWithMinutes() {
        int hourTen;
        int hourUnit;
        int minuteTen;
        int minuteUnit;
        hourTen = this.random.nextInt(3);
        if (hourTen == 2) {
            hourUnit = this.random.nextInt(4);
        } else {
            hourUnit = this.random.nextInt(10);
        }
        minuteTen = this.random.nextInt(6);
        minuteUnit = this.random.nextInt(10);
        char[] hourWithMinutes = new char[5];
        hourWithMinutes[0] = (char) (hourTen + StringsUtility.NUMBER_ASCII_OFFSET);
        hourWithMinutes[1] = (char) (hourUnit + StringsUtility.NUMBER_ASCII_OFFSET);
        hourWithMinutes[2] = ':';
        hourWithMinutes[3] = (char) (minuteTen + StringsUtility.NUMBER_ASCII_OFFSET);
        hourWithMinutes[4] = (char) (minuteUnit + StringsUtility.NUMBER_ASCII_OFFSET);
        String hourWithMinutesString = new String(hourWithMinutes);
        return hourWithMinutesString;
    }

    public String getRandomAlphabeticAzString(int size) {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                int randomInt = this.random.nextInt(2);
                if (randomInt == 0) {
                    int supportLowerCharacterInt = (int) (this.random.nextFloat() * (StringsUtility.Z_MIN_ASCII - StringsUtility.A_MIN_ASCII + 1));
                    randomInt = StringsUtility.A_MIN_ASCII + supportLowerCharacterInt;
                } else {
                    int supportUpperCharacterInt = (int) (this.random.nextFloat() * (StringsUtility.Z_MAX_ASCII - StringsUtility.A_MAX_ASCII + 1));
                    randomInt = StringsUtility.A_MAX_ASCII + supportUpperCharacterInt;
                }
                char charCast = (char) randomInt;
                this.stringBuilder.append(charCast);
            }
            String alphabeticAzString = this.stringBuilder.toString();
            this.stringBuilder.delete(0, size);
            return alphabeticAzString;
        }
        return "";
    }

    public String selectFrom(String... strings) {
        if (strings != null) {
            if (strings.length > 0) {
                int index = this.random.nextInt(strings.length);
                return strings[index];
            }
        }
        return "";
    }

    public int selectFrom(int... ints) {
        if (ints != null) {
            if (ints.length > 0) {
                int index = this.random.nextInt(ints.length);
                return ints[index];
            }
        }
        return 0;
    }

    public double selectFrom(double... doubles) {
        if (doubles != null) {
            if (doubles.length > 0) {
                int index = this.random.nextInt(doubles.length);
                return doubles[index];
            }
        }
        return 0;
    }

    public double getRandomDoubleBetween(double min, double max) {
        if (min != max) {
            double nextDouble = this.random.nextDouble();
            double randomValue = min + (max - min) * nextDouble;
            return randomValue;
        }
        return min;
    }

    public int getRandomIntBetween(int min, int max) {
        if (min != max) {
            double nextDouble = this.random.nextDouble();
            double doubleMax = max + RandomUtility.PLUS_DECIMAL;
            double doubleMin = min;
            double randomValue = doubleMin + (doubleMax - doubleMin) * nextDouble;
            int randomInt = (int) (Math.floor(randomValue));
            return randomInt;
        }
        return min;
    }

    public int getRandomPositiveInt(int max) {
        if (max > 0) {
            int randInt = this.random.nextInt(max + 1);
            if (randInt == 0) {
                return 1;
            }
            return randInt;
        }
        return 1;
    }

    public boolean getRandomBoolean() {
        int randInt = this.random.nextInt(2);
        if (randInt == 0) {
            return false;
        }
        return true;
    }

}
