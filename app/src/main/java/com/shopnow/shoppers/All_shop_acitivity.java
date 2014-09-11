package com.shopnow.shoppers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shopnow.shoppers.model.Shop;
import com.shopnow.shoppers.model.ShopperProvider;
import com.shopnow.shoppers.util.AssetProvider;

import java.util.ArrayList;
import java.util.List;

public class All_shop_acitivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private String[] categories;
    MenuItem categories_spinner;
    Spinner cat;
    private android.app.ActionBar actionBar;
    private List<Shop> shops;
    private String mall_id;
    private ShopperProvider database ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shop_acitivity);
        database = new ShopperProvider(this);

        Intent intent = this.getIntent();
        if( intent !=  null && intent.hasExtra(Intent.EXTRA_TEXT)){
            mall_id = intent.getStringExtra(Intent.EXTRA_TEXT);
            database.openDatabase();
            shops=database.getShopListInAMall(Integer.parseInt(mall_id));
            database.closeDatabase();

        }
else {
            Log.d("All_sho activity"," else message");


        }

/*
        cat = (Spinner)findViewById(R.id.categories_spinner);


        List<String> list = new ArrayList<String>();
        list.add("clothing");
        list.add("footwear");
        list.add("Spinner Data");
        list.add("Spinner Adapter");
        list.add("Spinner Example");





        actionBar= getActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_dropdown_item);

        android.app.ActionBar.OnNavigationListener mOnNavigationListener = new android.app.ActionBar.OnNavigationListener() {
            // Get the same strings provided for the drop-down's ArrayAdapter
            String[] strings = getResources().getStringArray(R.array.category_array);

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                // Create new fragment from our own Fragment class

                return true;
            }
        };
        */
        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        GridView gridView = (GridView) findViewById(R.id.grid);

        // Initialize the adapter with all the coupons. Set the adapter on the {@link GridView}.
        gridView.setAdapter(new CouponAdapter(inflater, createAllCoupons()));



        // Set a click listener for each coupon in the grid
        gridView.setOnItemClickListener(this);

    }


    void showDetailsOnScreen(String mall_id){
        database.openDatabase();
        shops=database.getShopListInAMall(Integer.parseInt(mall_id));
        database.closeDatabase();
    }

    void initialize_spinner(){
        ActionBar actionBar = getSupportActionBar();
        final Context themedContext = actionBar.getThemedContext();
//


        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        // Find the {@link GridView} that was already defined in the XML layout
        GridView gridView = (GridView) findViewById(R.id.grid);

        // Initialize the adapter with all the coupons. Set the adapter on the {@link GridView}.
        gridView.setAdapter(new CouponAdapter(inflater, createAllCoupons()));
        gridView.setOnItemClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_shop_acitivity, menu);

        getMenuInflater().inflate( R.menu.main, menu );






        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private List<Coupon> createAllCoupons() {
        // TODO: Customize this list of coupons for your personal use.
        // You can add a title, subtitle, and a photo (in the assets directory).
        List<Coupon> coupons = new ArrayList<Coupon>();





        for (int i=0;i<shops.size();i++){
            Shop s = shops.get(i);
            coupons.add(new Coupon(s.getName(),s.getContact(),s.getUri()));
        }



        return coupons;
    }




    /**
     * Callback method for a when a coupon is clicked. A new share intent is created with the
     * coupon title. Then the user can select which app to share the content of the coupon with.
     *
     * @param parent The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     *            will be a view provided by the adapter).
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Find coupon that was clicked based off of position in adapter
        Coupon coupon = (Coupon) parent.getItemAtPosition(position);

       /*
        Intent shop_intent = new Intent( this , All_shop_acitivity.class);
        shop_intent.putExtra(malls.get(position).getMall_id()+"",true);
        startActivity(shop_intent);
*/

    }

    /**
     * Create the share intent text based on the coupon title, subtitle, and whether or not
     * .
     *
     * @param coupon to create the intent text for.
     * @return string to be used in the share intent.
     */


    /**
     * Adapter for grid of coupons.
     */
    private static class CouponAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<Coupon> mAllCoupons;

        /**
         * Constructs a new {@link CouponAdapter}.
         *
         * @param inflater to create new views
         * @param allCoupons for list of all coupons to be displayed
         */
        public CouponAdapter(LayoutInflater inflater, List<Coupon> allCoupons) {
            if (allCoupons == null) {
                throw new IllegalStateException("Can't have null list of coupons");
            }
            mAllCoupons = allCoupons;
            mInflater = inflater;
        }

        @Override
        public int getCount() {
            return mAllCoupons.size();
        }

        @Override
        public Coupon getItem(int position) {
            return mAllCoupons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result = convertView;
            if (result == null) {
                result = mInflater.inflate(R.layout.grid_item, parent, false);
            }

            // Try to get view cache or create a new one if needed
            ViewCache viewCache = (ViewCache) result.getTag();
            if (viewCache == null) {
                viewCache = new ViewCache(result);
                result.setTag(viewCache);
            }

            // Fetch item
            Coupon coupon = getItem(position);

            // Bind the data
            viewCache.mTitleView.setText(coupon.mTitle);
            viewCache.mSubtitleView.setText(coupon.mSubtitle);
            viewCache.mImageView.setImageURI(coupon.mImageUri);

            return result;
        }
    }

    /**
     * Cache of views in the grid item view to make recycling of views quicker. This avoids
     * additional {@link View#findViewById(int)} calls after the {@link ViewCache} is first
     * created for a view. See
     * {@link CouponAdapter#getView(int position, View convertView, ViewGroup parent)}.
     */
    private static class ViewCache {

        /** View that displays the title of the coupon */
        private final TextView mTitleView;

        /** View that displays the subtitle of the coupon */
        private final TextView mSubtitleView;

        /** View that displays the image associated with the coupon */
        private final ImageView mImageView;

        /**
         * Constructs a new {@link ViewCache}.
         *
         * @param view which contains children views that should be cached.
         */
        private ViewCache(View view) {
            mTitleView = (TextView) view.findViewById(R.id.name);
            mSubtitleView = (TextView) view.findViewById(R.id.shops);
            mImageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    /**
     * Model object for coupon.
     */
    private static class Coupon {

        /** Title of the coupon. */
        private final String mTitle;

        /** Description of the coupon. */
        private final String mSubtitle;

        /** Content URI of the image for the coupon. */
        private final Uri mImageUri;

        /**
         * Constructs a new {@link Coupon}.
         *
         * @param titleString is the title
         * @param subtitleString is the description
         * @param imageAssetFilePath is the file path from the application's assets folder for
         *                           the image associated with this coupon
         */
        private Coupon(String titleString, String subtitleString, String imageAssetFilePath) {
            mTitle = titleString;
            mSubtitle = subtitleString;
            mImageUri = Uri.parse("content://" + AssetProvider.CONTENT_URI + "/" +
                    imageAssetFilePath);
        }
    }

}
