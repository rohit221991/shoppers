package com.shopnow.shoppers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WebCachedImageView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.shopnow.shoppers.app.AppController;
import com.shopnow.shoppers.model.Mall;
import com.shopnow.shoppers.model.ShopperProvider;
import com.shopnow.shoppers.util.AssetProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener  {

    // TODO: Fill in your name here
    private static final String SENDER_NAME = "Rohit Gupta";
    private List<Mall> malls;
    ShopperProvider database;
    private String urlJsonArry = "http://www.shopchilly.com/mall";
    LayoutInflater inflater;
    Activity curr;
    AdapterView.OnItemClickListener click_listner;
    // action bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set activity_main.xml to be the layout for this activity
        setContentView(R.layout.activity_main);

        // Fetch the {@link LayoutInflater} service so that new views can be created
         inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        // Find the {@link GridView} that was already defined in the XML layout
     //   buildDatabase();
        click_listner=this;
        makeJsonArrayRequest();

        GridView gridView = (GridView) findViewById(R.id.grid);

        // Initialize the adapter with all the coupons. Set the adapter on the {@link GridView}.
        gridView.setAdapter(new CouponAdapter(inflater, createAllCoupons()));



        // Set a click listener for each coupon in the grid
        gridView.setOnItemClickListener(this);




    }



    void buildDatabase(){

        database= new ShopperProvider(this);
        database.openDatabase();
        database.addMallEntryToDatabase(1234, 72, 73, "Magrath Road", "garuda", "http://www.shopchilly.com/img/5629499534213120");

       // database.addShopEntryToDatabase(1,1234,"first Floor","NIKE","spa.jpg","1233456");
        database.closeDatabase();




    }

    private void makeJsonArrayRequest() {
        database= new ShopperProvider(this);


        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject mall = (JSONObject) response
                                        .get(i);

                                String name = mall.getString("name");
                                String image = mall.getString("image1");

                                String address = mall.getString("city");
                                String mall_id= mall.getString("id");
                                database.openDatabase();
                                database.addMallEntryToDatabase(Long.parseLong(mall_id),i*2,i*3,address,name,image);
                                database.closeDatabase();




                            }
                            GridView gridView = (GridView) findViewById(R.id.grid);

                            gridView.setAdapter(new CouponAdapter(inflater, createAllCoupons()));
                            gridView.setOnItemClickListener(click_listner) ;



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);


    }


    /**
     * Actionbar navigation item select listener
     * */



    /**
     * Generate the list of all coupons.
     * @return The list of coupons.
     */
    private List<Coupon> createAllCoupons() {
        // TODO: Customize this list of coupons for your personal use.
        // You can add a title, subtitle, and a photo (in the assets directory).
        database.openDatabase();;
        malls= database.getMallList();
        List<Coupon> coupons = new ArrayList<Coupon>();


        for(int i=0;i< malls.size();i++) {
            Mall m = malls.get(i);
            coupons.add(new Coupon(m.getName(),m.getAddress(),m.getUri()));
        }
        database.closeDatabase();
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



        Intent shop_intent = new Intent( this , All_shop_acitivity.class).putExtra(Intent.EXTRA_TEXT ,malls.get(position).getMall_id()+"");

        startActivity(shop_intent);

    }

    /**
     * Create the share intent text based on the coupon title, subtitle, and whether or not
     * there is a {@link #SENDER_NAME}.
     *
     * @param coupon to create the intent text for.
     * @return string to be used in the share intent.
     */
    private String getShareText(Coupon coupon) {
        // If there is no sender name, just use the coupon title and subtitle
        if (TextUtils.isEmpty(SENDER_NAME)) {
            return getString(R.string.message_format_without_sender,
                    coupon.mTitle, coupon.mSubtitle);
        } else {
            // Otherwise, use the other string template and pass in the {@link #SENDER_NAME} too
            return getString(R.string.message_format_with_sender, SENDER_NAME,
                    coupon.mTitle, coupon.mSubtitle);
        }
    }

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