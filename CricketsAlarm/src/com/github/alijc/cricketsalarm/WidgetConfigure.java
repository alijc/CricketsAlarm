/*
 * © Copyright 2011 Thibault Jouannic <thibault@jouannic.fr>
 * © Copyright 2013 Ali Corbin <ali.corbin@gmail.com>
 *
 *  This file is part of CricketsAlarm.
 *
 *  CricketsAlarm is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CricketsAlarm is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CricketsAlarm. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.alijc.cricketsalarm;

//import fr.miximum.picker.NumberPicker;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WidgetConfigure extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    /** The default number of hours for the nap duration */
    private static final int DEFAULT_NAP_HOURS = 12;

    /** The default number of minutes for the nap duration */
    private static final int DEFAULT_NAP_MINUTES = 0;

	//private NumberPicker mHourPicker;
    //private NumberPicker mMinutePicker;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED. This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.widget_configure_layout);

        // Find widget id from intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Log.e(CricketsAlarm.TAG, "Configure: no appwidget id provided");
            finish();
        }

        //configurePickers();
        findViewById(R.id.create_widget).setOnClickListener(mOnCreateClickListener);
        findViewById(R.id.cancel_widget).setOnClickListener(mOnCancelClickListener);
    }

    /**
     * Click listener to handle create button click
     */
    View.OnClickListener mOnCreateClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = WidgetConfigure.this;
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            // Update widget view
            int napDuration = getNapDuration();
            CricketsAlarmWidget.setNapDuration(context, mAppWidgetId, napDuration);
            CricketsAlarmWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    /**
     * Click listener to handle cancel button click
     */
    View.OnClickListener mOnCancelClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * Configure nap duration pickers with correct ranges and default values
     */
    // private void configurePickers() {
    //     mHourPicker = (NumberPicker) findViewById(R.id.nap_hour);
    //     mHourPicker.setRange(0, 23);
    //     mHourPicker.setCurrent(DEFAULT_NAP_HOURS);

    //     mMinutePicker = (NumberPicker) findViewById(R.id.nap_minute);
    //     mMinutePicker.setRange(0, 59);
    //     mMinutePicker.setCurrent(DEFAULT_NAP_MINUTES);
    // }

    /**
     * Computes the total nap duration
     * @return the nap duration, in minutes
     */
    public int getNapDuration()
    {
		return 12 * 60;
        //return mHourPicker.getCurrent() * 60 + mMinutePicker.getCurrent();
    }
}
