package com.example.android.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.app.data.WeatherContract;

/**
 * Created by caihua2300 on 22/07/2016.
 */
public class ForecastAdapter extends CursorAdapter {
    private Context mContext;
    public ForecastAdapter(Context context, Cursor c, int flags) {


        super(context, c, flags);
        mContext=context;
    }

    /**
     * Prepare the weather high/lows for presentation.
     */


    /*
        This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
        string.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_forecast,parent,false);
        return view;


    }
    /*
        This is where we fill-in the views with the contents of the cursor.
    */


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //TextView tv = (TextView)view;
        //tv.setText(convertCursorRowToUXFormat(cursor));
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);
                // Use placeholder image for now
        ImageView iconView = (ImageView) view.findViewById(R.id.list_item_icon);
        iconView.setImageResource(R.drawable.ic_launcher);

                        // Read date from cursor
        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
                // Find TextView and set formatted date on it
        TextView dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

                        // Read weather forecast from cursor
        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
                // Find TextView and set weather forecast on it
        TextView descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
        descriptionView.setText(description);
        boolean isMetric = Utility.isMetric(context);

                        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
        highView.setText(Utility.formatTemperature(high, isMetric));

                        // Read low temperature from cursor
        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        TextView lowView = (TextView) view.findViewById(R.id.list_item_low_textview);
        lowView.setText(Utility.formatTemperature(low, isMetric));



    }
}
