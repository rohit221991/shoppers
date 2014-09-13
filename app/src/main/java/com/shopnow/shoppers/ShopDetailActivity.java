package com.shopnow.shoppers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shopnow.shoppers.model.Offer;
import com.shopnow.shoppers.model.ShopperProvider;

import java.util.List;


public class ShopDetailActivity extends ActionBarActivity {


        private CustomListAdapter adapter;
        private ListView listView;

        private ShopperProvider provider;

        private int MALL_ID;
        private int SHOP_ID;
        private String Timings;
        private String URI;


        private List<Offer> offers;



    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shop_detail);

            Intent intent = this.getIntent();

             if( intent !=  null ){
                MALL_ID = intent.getIntExtra("MALL_ID", -1);
                SHOP_ID = intent.getIntExtra("SHOP_ID", -1);
                Timings = intent.getStringExtra("TIMING");
                URI = intent.getStringExtra("URI");

            }

            ImageView shopImageView = (ImageView)findViewById(R.id.Image);
            shopImageView.setImageResource(R.drawable.shop_image);
            ImageButton callButton = (ImageButton)findViewById(R.id.idCallButton);

            TextView textTiming = (TextView)findViewById(R.id.idTimingText);
            /*textTiming.setText(
                            " MONDAY    9:00 TO 18:00 \n " +
                            " TUESDAY   9:00 TO 18:00 \n" +
                            " WEDNESDAY 9:00 TO 18:00 \n" +
                            " THURSDAY  9:00 TO 18:00 \n" +
                            " FRIDAY    9:00 TO 18:00 \n" +
                            " SATURDAY  9:00 TO 18:00 ");
            */
            textTiming.setText(Timings);





            provider = new ShopperProvider(this);
            provider.openDatabase();
            offers = provider.getAllOffers( MALL_ID , SHOP_ID);
            provider.closeDatabase();
            adapter = new CustomListAdapter(this,offers);
            listView = (ListView)findViewById(R.id.idOffersList);
            listView.setAdapter(adapter);


        }

        public void call(View view){
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "7353789490"));
            startActivity(callIntent);
        }


        public class CustomListAdapter extends BaseAdapter {
            private Activity activity;
            private LayoutInflater inflater;
            private List<Offer> Offers;

            public CustomListAdapter (Activity activity,List<Offer> Offers){
                this.activity= activity;
                this.Offers = Offers;
            }

            @Override
            public int getCount() {
                return Offers.size();
            }

            @Override
            public Object getItem(int location) {
                return Offers.get(location);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (inflater == null)
                    inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (convertView == null)
                    convertView = inflater.inflate(R.layout.offer_list_item, null);
                Offer current = offers.get(position);
                TextView descriptionText = (TextView) convertView.findViewById(R.id.idOfferDescription);
                descriptionText.setText(current.getDESCRIPTION());
                TextView validity = (TextView)convertView.findViewById(R.id.idOfferValidity);
                validity.setText(current.getVALID_FROM() + " TO " + current.getVALID_TO());
                return convertView;
            }
        }

    }


