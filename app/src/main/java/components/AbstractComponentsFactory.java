package components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.google.android.gms.maps.model.BitmapDescriptor;

import javax.mail.Authenticator;

import database.AbstractUserCache;
import graphiccomponents.DoubleThumbMultiSlider;
import graphiccomponents.FullscreenCalendarDialog;
import graphiccomponents.FullscreenChoicesDialog;
import graphiccomponents.FullscreenInsertDialog;
import graphiccomponents.FullscreenLoginDialog;
import graphiccomponents.FullscreenReviewDialog;
import graphiccomponents.FullscreenSigninDialog;
import models.ObjectsModel;

public abstract class AbstractComponentsFactory {

    private Activity activity;

    public abstract FullscreenLoginDialog buildFullscreenLoginDialog();

    public abstract FullscreenReviewDialog buildFullscreenReviewDialog();

    public abstract FullscreenChoicesDialog buildFullscreenChoicesDialog(int opzione);

    public abstract FullscreenCalendarDialog buildFullscreenCalendarDialog(int opzione);

    public abstract FullscreenInsertDialog buildFullscreenInsertDialog(int opzione);

    public abstract FullscreenSigninDialog buildFullscreenSigninDialog();

    public abstract AlertDialog buildWaitingDialog();

    public abstract AlertDialog buildAlertDialog();

    public abstract Dialog buildDialog();

    public abstract DoubleThumbMultiSlider buildDoubleThumbMultiSlider(int option);

    public abstract Spinner buildSpinner(int opzione, View view);

    public abstract NumberPicker buildNumberPicker();

    public abstract CompoundButton buildCompoundButton(int opzione);

    public abstract CompoundButton buildCompoundButton();

    public abstract BitmapDescriptor buildBitmapDescriptor(int opzione);

    public abstract String buildTitle(int option);

    public abstract View buildView(ObjectsModel objectsModel);

    public abstract String buildBarTitle(String string);

    public abstract AbstractUserCache buildUserCache();

    public abstract Authenticator buildAuthenticator();

    public abstract ViewGroup buildViewGroup();

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return this.activity;
    }

}
