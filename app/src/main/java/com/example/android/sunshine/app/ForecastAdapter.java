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
    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;
    private Context mContext;
    public ForecastAdapter(Context context, Cursor c, int flags) {


        super(context, c, flags);
        mContext=context;
    }
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
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
        int viewType = getItemViewType(cursor.getPosition());
                int layoutId = -1;
                switch (viewType) {
                        case VIEW_TYPE_TODAY: {
                                layoutId = R.layout.list_item_forecast_today;
                                break;
                           }
                        case VIEW_TYPE_FUTURE_DAY: {
                                layoutId = R.layout.list_item_forecast;
                                break;
                            }
                    }
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;


    }
    /*
        This is where we fill-in the views with the contents of the cursor.

    */


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //TextView tv = (TextView)view;
        //tv.setText(convertCursorRowToUXFormat(cursor));
        ViewHolder viewHolder = (ViewHolder) view.getTag();
                // Use placeholder image for now
        int viewType = getItemViewType(cursor.getPosition());
                switch (viewType) {
                        case VIEW_TYPE_TODAY: {
                                // Get weather icon
                                        viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
                                                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                                break;
                            }
                        case VIEW_TYPE_FUTURE_DAY: {
                                // Get weather icon
                                        viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(
                                                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                                break;
                            }
                }

                        // Read date from cursor
        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
                // Find TextView and set formatted date on it
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

                        // Read weather forecast from cursor
        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
                // Find TextView and set weather forecast on it
        viewHolder.descriptionView.setText(description);
        boolean isMetric = Utility.isMetric(context);

                        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        viewHolder.highTempView.setText(Utility.formatTemperature(context, high, isMetric));

                        // Read low temperature from cursor
        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.lowTempView.setText(Utility.formatTemperature(context, low, isMetric));



    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
}
