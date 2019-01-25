package com.kalbe.mobiledevknlibs.InputFilter;

import android.text.Spanned;
import android.widget.EditText;

/**
 * Created by Robert on 04/01/2018.
 */

public class InputFilter {
    public static void filterCapsAll(EditText editText, int length){
        android.text.InputFilter[] fArray = new android.text.InputFilter[3];

        android.text.InputFilter filter = new android.text.InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) { // Accept only letter & digits ; otherwise just return
                        return "";
                    }
                }
                return null;
            }
        };
        fArray[0] = new android.text.InputFilter.LengthFilter(length);
        fArray[1] = new android.text.InputFilter.AllCaps();
        fArray[2] = filter;
        editText.setFilters(fArray);
    }
    public static void filterSpaceAtoZ0to9(EditText editText){
            editText.setFilters(new android.text.InputFilter[]{
                new android.text.InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z0-9. ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }
    public final boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
