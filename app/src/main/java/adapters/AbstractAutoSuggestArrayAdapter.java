package adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Vector;

import comparators.StringComparator;

public abstract class AbstractAutoSuggestArrayAdapter extends ArrayAdapter {

    private AbstractList<String> items;
    private AbstractList<String> tempItems;
    private AbstractList<String> suggestions;
    private final StringComparator stringComparator = new StringComparator(null);
    private ContainsFilter containsFilter;
    private int numberOfSuggestions;
    private String limitString;

    public AbstractAutoSuggestArrayAdapter(Context context, int resource, AbstractList<String> items, int numberOfSuggestions, String limitString) {
        super(context, resource, items);
        this.items = items;
        this.tempItems = new Vector<>(items);
        this.suggestions = new Vector<>();
        this.numberOfSuggestions = numberOfSuggestions;
        this.limitString = limitString;
    }

    @Override
    public Filter getFilter() {
        return this.containsFilter;
    }

    public void setContainsFilter(ContainsFilter containsFilter) {
        this.containsFilter = containsFilter;
    }

    public AbstractList<String> getItems() {
        return this.items;
    }

    public void setItems(AbstractList<String> items) {
        this.items = items;
    }

    protected void setTempItems(AbstractList<String> tempItems) {
        this.tempItems = tempItems;
    }

    protected AbstractList<String> getTempItems() {
        return this.tempItems;
    }

    public void setSuggestions(AbstractList<String> suggestions) {
       this.suggestions = suggestions;
    }

    public AbstractList<String> getSuggestions() {
        return this.suggestions;
    }

    public void setNumberOfSuggestions(int numberOfSuggestions) {
        this.numberOfSuggestions = numberOfSuggestions;
    }

    public int getNumberOfSuggestions() {
        return this.numberOfSuggestions;
    }

    public void setLimitString(String limitString) {
        this.limitString = limitString;
    }

    public String getLimitString() {
        return this.limitString;
    }

    protected class ContainsFilter  extends Filter {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String string = (String) resultValue;
            return string;
        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                if (constraint instanceof String) {
                    if (suggestions != null && tempItems != null) {
                        suggestions.clear();
                        Iterator<String> iterator = tempItems.iterator();
                        String toString = (String) constraint;
                        if (limitString != null) {
                            if (limitString.length() > 0) {
                                while (iterator.hasNext()) {
                                    String string = iterator.next();
                                    int indexOf = string.indexOf(limitString);
                                    String newString = string.substring(0, indexOf);
                                    if (newString.contains(toString)) {
                                        suggestions.add(string);
                                    }
                                }
                            } else {
                                while (iterator.hasNext()) {
                                    String string = iterator.next();
                                    if (string.contains(toString)) {
                                        suggestions.add(string);
                                    }
                                }
                            }
                        } else {
                            while (iterator.hasNext()) {
                                String string = iterator.next();
                                if (string.contains(toString)) {
                                    suggestions.add(string);
                                }
                            }
                        }
                        FilterResults filterResults = new FilterResults();
                        iterator = suggestions.iterator();
                        LinkedList<String> arrayList = new LinkedList<>();
                        for (int i = 0; i < suggestions.size(); i++) {
                            arrayList.addFirst(iterator.next());
                        }
                        arrayList.sort(stringComparator);
                        LinkedList<String> finalArrayList = new LinkedList<>();
                        iterator = arrayList.iterator();
                        for (int i = 0; i < numberOfSuggestions; i++) {
                            try {
                                finalArrayList.addLast(iterator.next());
                            } catch (NoSuchElementException noSuchElementException) {
                                break;
                            }
                        }
                        filterResults.values = finalArrayList;
                        filterResults.count = finalArrayList.size();
                        return filterResults;
                    }
                }
            }
            return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            LinkedList<String> filterList = (LinkedList<String>) results.values;
            if (results != null) {
                if (results.count > 0) {
                    clear();
                    Iterator<String> iterator = filterList.iterator();
                    while (iterator.hasNext()) {
                        String item = iterator.next();
                        add(item);
                        notifyDataSetChanged();
                    }
                }
            }
        }

    }

}