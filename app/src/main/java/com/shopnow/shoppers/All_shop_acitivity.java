package com.shopnow.shoppers;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WebCachedImageView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shopnow.shoppers.adapter.TitleNavigationAdapter;
import com.shopnow.shoppers.app.SpinnerNavItem;
import com.shopnow.shoppers.model.Shop;
import com.shopnow.shoppers.model.ShopperProvider;
import com.shopnow.shoppers.util.AssetProvider;

import java.util.ArrayList;
import java.util.List;

public class All_shop_acitivity extends ActionBarActivity implements AdapterView.OnItemClickListener ,android.app.ActionBar.OnNavigationListener {

    private String[] categories;
    MenuItem categories_spinner;
    Spinner cat;


    private List<Shop> shops;
    private String mall_id;
    private ShopperProvider database ;

    // action bar
    private ActionBar actionBar;

    // Title navigation Spinner data
    private ArrayList<SpinnerNavItem> navSpinner;

    // Navigation adapter
    private TitleNavigationAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shop_acitivity);
        database = new ShopperProvider(this);

        Intent intent = this.getIntent();
        if( intent !=  null && intent.hasExtra(Intent.EXTRA_TEXT)){
            mall_id = intent.getStringExtra(Intent.EXTRA_TEXT);
            getAllShopsInMall(mall_id);
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        GridView gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new CouponAdapter(inflater, createAllCoupons()));
        gridView.setOnItemClickListener(this);



        actionBar = getActionBar();

        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);

        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Spinner title navigation data
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem("Food"));
        navSpinner.add(new SpinnerNavItem("Clothing"));
        navSpinner.add(new SpinnerNavItem("Gaming"));
        navSpinner.add(new SpinnerNavItem("Footwear"));

        // title drop down adapter
        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);

        // assigning the spinner navigation
        actionBar.setListNavigationCallbacks(adapter, this);


    }


    void getAllShopsInMall(String mall_id){
        database.openDatabase();
        shops=database.getShopListInAMall(Integer.parseInt(mall_id));
        database.closeDatabase();
    }

    /*
    void initialize_spinner(){
        ActionBar actionBar = getSupportActionBar();
        final Context themedContext = actionBar.getThemedContext();

        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        // Find the {@link GridView} that was already defined in the XML layout
        GridView gridView = (GridView) findViewById(R.id.grid);

        // Initialize the adapter with all the coupons. Set the adapter on the {@link GridView}.
        gridView.setAdapter(new CouponAdapter(inflater, createAllCoupons()));
        gridView.setOnItemClickListener(this);
    }
    */

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_shop_acitivity, menu);
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }
    */


    /*
    * implement makeJsonArrayRequest()
    *
    * */



    /**
     * Actionbar navigation item select listener
     * */
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        // Action to be taken after selecting a spinner item
        return false;
    }


    private List<Coupon> createAllCoupons() {
        List<Coupon> coupons = new ArrayList<Coupon>();
        for (int i=0;i<shops.size();i++){
            Shop s = shops.get(i);
            coupons.add(new Coupon(s.getName(),s.getContact(),s.getUri()));
        }
        return coupons;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Coupon coupon = (Coupon) parent.getItemAtPosition(position);
        Intent shopDetail_intent = new Intent( this , ShopDetailActivity.class);
        Shop current =  shops.get(position);
        shopDetail_intent.putExtra( "SHOP_ID" , current.getShop_id());
        shopDetail_intent.putExtra( "MALL_ID" , current.getMall_id());
        shopDetail_intent.putExtra( "TIMING" , current.getTiming());
        shopDetail_intent.putExtra( "ADDRESS" , current.getAddress());
        shopDetail_intent.putExtra( "URI" , current.getUri());

        startActivity(shopDetail_intent);
    }


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
            viewCache.mImageView.setImageUrl(coupon.image_url);

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
        private final WebCachedImageView mImageView;

        /**
         * Constructs a new {@link ViewCache}.
         *
         * @param view which contains children views that should be cached.
         */
        private ViewCache(View view) {
            mTitleView = (TextView) view.findViewById(R.id.name);
            mSubtitleView = (TextView) view.findViewById(R.id.shops);
            mImageView = (WebCachedImageView) view.findViewById(R.id.image);
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
        String image_url;

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
            image_url=imageAssetFilePath;
        }
    }
}
