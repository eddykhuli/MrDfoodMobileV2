package lagecy.live.desh.com.mrdfoodmobilev2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

import dmax.dialog.SpotsDialog;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Restaurant> restaurants;
    private GridView gridView;
    private TextView tvLoggedUser,tvEmailLoged;
    private  ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            String names = SessionSingleton.getInstance().personObject.getString("firstname")+" "+SessionSingleton.getInstance().personObject.getString("surname");
            String logEmail = SessionSingleton.getInstance().personObject.getString("email");

            tvLoggedUser = navigationView.getHeaderView(0).findViewById(R.id.tvLoggedUser);
            tvEmailLoged =navigationView.getHeaderView(0).findViewById(R.id.tvEmailLogged);
            tvLoggedUser.setText(names);

            tvEmailLoged.setText(logEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        gridView = (GridView)findViewById(R.id.gridview);
        getAllRestaurants();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(WelcomeActivity.this,RestaurantActivity.class);
            //intent.putExtra("restId",restaurant.getId());
            startActivity(intent);


        } else if (id == R.id.nav_gallery) {



        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(getApplicationContext(),OrdersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {


            Toast.makeText(getApplicationContext(),"You are logged out",Toast.LENGTH_SHORT).show();


           // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            //startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
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

                            Intent intent = new Intent(WelcomeActivity.this,RestaurantActivity.class);
//                            //intent.putExtra("restId",restaurant.getId());
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
