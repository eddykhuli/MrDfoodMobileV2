package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class exampleActivity extends AppCompatActivity  {

    private List<Restaurant> restaurants;
    private GridView gridView;
    private   ActionBarDrawerToggle toggle;
    private String[] activityTitles;
    private DrawerLayout dLayout;
    private NavigationView navView;
    private View navHeader;
    public static int navItemIndex = 0;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handler = new Handler();
       Log.d("Object2========",SessionSingleton.getInstance().personObject.toString());
//        try {
//            String email = SessionSingleton.getInstance().object.getString("email");
//            TextView emailTxt = findViewById(R.id.emailTxt);
//            emailTxt.setText("dfgfd");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //drawer = (DrawerLayout)findViewById(R.id.nav_view);
        //navView = (NavigationView)findViewById(R.id.);

//
//        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return false;
//            }
//        });



        gridView = (GridView)findViewById(R.id.gridview);
        getAllRestaurants();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       // inflater.inflate(R.menu.nav_items, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getAllRestaurants()
    {
        String url = "http://192.168.2.155:8080/api/restaurant/restaurant/getAllRestaurants";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    final List<Restaurant> temp = fetchRestaurants(response);


                    ExamoArrayAdapter adapter = new ExamoArrayAdapter(getApplicationContext(), temp);
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            //Restaurant restaurant = temp.get(i).getId();

                            long restId = temp.get(i).getId();
                            String restaurantName = temp.get(i).getRestaurantName();

                            SessionSingleton.getInstance().restaurantName = restaurantName;
                            SessionSingleton.getInstance().restaurantId = restId;
                            Intent intent = new Intent(exampleActivity.this,RestaurantActivity.class);
                            //intent.putExtra("restId",restaurant.getId());
                            startActivity(intent);


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);

    }

    public List<Restaurant> fetchRestaurants(String data)throws JSONException{
        try {
            restaurants = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(data);
            for(int x= 0; x < jsonArray.length();x++)
            {
                JSONObject obj = jsonArray.getJSONObject(x);
                Restaurant restaurant = new Restaurant();

                restaurant.setId(obj.getLong("id"));
                restaurant.setRestaurantName(obj.getString("restaurantName"));
                restaurant.setAccountId(obj.getLong("accountId"));
                restaurant.setCategories(null);
                restaurant.setAddress(obj.getString("address"));
                restaurant.setImage(obj.getString("image"));
                restaurants.add(restaurant);
                // Log.d("Restaurants ------------>>:",restaurants.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurants;
    }


}
