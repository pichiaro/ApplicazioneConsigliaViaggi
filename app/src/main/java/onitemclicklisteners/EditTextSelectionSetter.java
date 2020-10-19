package onitemclicklisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

public class EditTextSelectionSetter implements OnItemClickListener {

    private EditText editText;
    private int selection;

    public EditTextSelectionSetter(EditText editText, int selection) {
        this.editText = editText;
        this.selection = selection;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditText() {
        return this.editText;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getSelection() {
        return this.selection;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      if (this.editText != null) {
          this.editText.setSelection(0);
      }
    }

}
