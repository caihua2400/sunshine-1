package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new DetailFragment()).commit();
        }
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private String mForecastStr;
        private ShareActionProvider mShareActionProvider;
        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.detailfragment,menu);
            MenuItem menuItem=menu.findItem(R.id.menu_item_share);
            ShareActionProvider mShareActionProvider= (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if(mShareActionProvider!=null){
                mShareActionProvider.setShareIntent(createShareForeCastIntent());
            }else{
                Log.d(LOG_TAG, "Share Action Provider is null?");
            }
        }
        private Intent createShareForeCastIntent(){
            Intent shareIntent=new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastStr+FORECAST_SHARE_HASHTAG);
            return shareIntent;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent=getActivity().getIntent();
            if(intent!=null&&intent.hasExtra(Intent.EXTRA_TEXT)){
                mForecastStr=intent.getStringExtra(Intent.EXTRA_TEXT);
                TextView textView=(TextView)rootView.findViewById(R.id.t1);
                textView.setText(mForecastStr);
            }
            return rootView;
        }
    }
}
