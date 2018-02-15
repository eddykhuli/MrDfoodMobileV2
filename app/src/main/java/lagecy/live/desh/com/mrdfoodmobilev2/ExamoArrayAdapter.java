package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/01/16.
 */

public class ExamoArrayAdapter extends BaseAdapter {

    private List<Restaurant> restaurants = new ArrayList<>();
    private Context context;
    public ExamoArrayAdapter(Context context,List<Restaurant> restaurants)
    {
        this.context = context;
        this.restaurants = restaurants;
    }
    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       final Restaurant restaurant = restaurants.get(i);

       if (view == null) {
           final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.example_items, null);
       }

       final ImageView imageView = (ImageView)view.findViewById(R.id.imageview);
       final TextView nameTextView = (TextView)view.findViewById(R.id.textView);

        decodeImageString( restaurant.getImage(), imageView );
        nameTextView.setText(restaurant.getRestaurantName());

        nameTextView.setBackgroundColor(Color.parseColor("#fbdcbb"));






       return view;

    }
    private void decodeImageString(String imageString, ImageView imageView){

        final String pureBase64Encoded = imageString.substring(imageString.indexOf(",") + 1);

        byte[] decodeString = Base64.decode (pureBase64Encoded, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray (decodeString, 0, decodeString.length);
        imageView.setImageBitmap(decoded);
    }
}
