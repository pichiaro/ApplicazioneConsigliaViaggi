package utilities;

import android.widget.CalendarView;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class DatesUtility {

    public static void setDate(CalendarView calendarView, int day, int month, int year) {
        if (calendarView != null) {
            month = month - 1;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            long milliseconds = calendar.getTimeInMillis();
            calendarView.setDate(milliseconds, true, true);
        }
    }

    public static void setLocalDate(CalendarView calendarView) {
        if (calendarView != null) {
            LocalDate localDate = LocalDate.now();
            String date = localDate.toString();
            String parts[] = date.split("-");
            int day = Integer.parseInt(parts[2]);
            int month = Integer.parseInt(parts[1]);
            month = month - 1;
            Calendar calendar = Calendar.getInstance();
            int year = Integer.parseInt(parts[0]);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            long milliseconds = calendar.getTimeInMillis();
            calendarView.setDate(milliseconds, true, true);
        }
    }

    public static String toDDMMYYYYFormat(String YYYYMMDDDate, boolean hasSeparator) {
        String DDMMYYYYDate = "";
        if (YYYYMMDDDate != null) {
            YYYYMMDDDate = YYYYMMDDDate.replaceAll("/","");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date date = simpleDateFormat.parse(YYYYMMDDDate);
                if (!hasSeparator) {
                    simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                }
                else {
                    simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                }
                DDMMYYYYDate = simpleDateFormat.format(date);
            } catch (ParseException exception) {

            }
        }
        return DDMMYYYYDate;
    }

    public static String getLocalYYYYMMDDDate(boolean hasSeparator) {
        LocalDate localDate = LocalDate.now();
        String localDateString = localDate.toString();
        String localYYYYMMDDDate = "";
        if (hasSeparator) {
            localYYYYMMDDDate = localDateString.replaceAll("-", "/");
        }
        else {
            localYYYYMMDDDate = localDateString.replaceAll("-", "");
        }
        return localYYYYMMDDDate;
    }

    public static int getYearsBetweenDDMMYYYY(String firstDate, String secondDate) {
        if (firstDate != null) {
            if (secondDate != null) {
                firstDate = firstDate.replaceAll("/","");
                secondDate = secondDate.replaceAll("/", "");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
                Date dateOne;
                Date dateTwo;
                try {
                    dateOne = simpleDateFormat.parse(firstDate);
                    dateTwo = simpleDateFormat.parse(secondDate);
                }
                catch (ParseException exception) {
                    return 0;
                }
                long difference = dateOne.getTime() - dateTwo.getTime();
                long seconds = difference / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                long years = days / 365;
                return (int) years;
            }
        }
        return 0;
    }

    public static String getHHMM(TimePicker timePicker, boolean hasSeparator) {
        String hourWithMinutes = "";
        if (timePicker != null) {
            int hour = timePicker.getHour();
            String stringHour = String.valueOf(hour);
            if (hour < 10) {
                stringHour = "0" + stringHour;
            }
            int minute = timePicker.getMinute();
            String stringMinute = String.valueOf(minute);
            if (minute < 10) {
                stringMinute = "0" + stringMinute;
            }
            if (hasSeparator) {
                hourWithMinutes = stringHour + ":" + stringMinute;
            }
            else {
                hourWithMinutes = stringHour + stringMinute;
            }
        }
        return hourWithMinutes;
    }

    public static void setNumberPicker(NumberPicker numberPicker, int min, int max) {
        if (numberPicker != null) {
            if (min < max) {
                Vector<String> strings = new Vector<>();
                for (int i = min; i <= max ; ++i) {
                    strings.add(String.valueOf(i));
                }
                List<String> subList = strings.subList(0, strings.size());
                String[] subArray = new String[subList.size()];
                subArray = subList.toArray(subArray);
                numberPicker.setDisplayedValues(subArray);
                numberPicker.setMinValue(min);
                numberPicker.setMaxValue(max);
            }
        }
    }

}
