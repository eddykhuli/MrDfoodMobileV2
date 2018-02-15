package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MANDELACOMP7 on 2018/01/12.
 */

public class CustomArrayAdapter extends ArrayAdapter{

    private List<Restaurant> restaurants = new ArrayList<>();

    public CustomArrayAdapter(@NonNull Context context, int resource, @NonNull List<Restaurant> objects) {
        super(context, resource, objects);
        restaurants = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.listview_items, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        textView.setText(restaurants.get(position).getRestaurantName());
        decodeImageString( restaurants.get(position).getImage(), imageView );
        return v;

    }
    private void decodeImageString(String imageString, ImageView imageView){

        final String pureBase64Encoded = imageString.substring(imageString.indexOf(",") + 1);

        byte[] decodeString = Base64.decode (pureBase64Encoded, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray (decodeString, 0, decodeString.length);
        imageView.setImageBitmap(decoded);
    }
}
