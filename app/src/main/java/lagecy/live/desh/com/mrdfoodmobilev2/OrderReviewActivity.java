package lagecy.live.desh.com.mrdfoodmobilev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class OrderReviewActivity extends AppCompatActivity {

    private  List<Double> grandTot = new ArrayList<>();
    private  TextView tvOrderId,tvNames,tvOemail,tvphoneNo,tvCount,tvTotp,tvSub;
    private RadioGroup radioGroup;
    public String deliveryAddr , collectionType,cellNo,email ;
    public EditText etDaddress;
    public Button btCard,btCash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        tvOrderId = (TextView)findViewById(R.id.tvOrderId);
        tvOrderId.setText("Order :"+makeId());
        tvNames = findViewById(R.id.tvNames);
        tvOemail = findViewById(R.id.tvOemail);
        etDaddress = findViewById(R.id.etDaddress);
        radioGroup = findViewById(R.id.myRadioGroup);
        tvphoneNo = findViewById(R.id.tvphoneNo);
        tvCount = findViewById(R.id.tvCount);
        tvTotp = findViewById(R.id.tvTotp);
        tvSub = findViewById(R.id.tvSub);

        JSONObject obj = SessionSingleton.getInstance().personObject;
        try {
            showOrderDetails(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selected) {

                if(selected ==  R.id.mrD)
                {
                    collectionType = "MrD Food Delivery";
                    Log.d("Order :",collectionType);
                    //Toast.makeText(getApplicationContext(),"MrD Food Delivery",Toast.LENGTH_LONG).show();
                }
                else if (selected == R.id.selfCollect)
                {
                    collectionType = "Self Collection";
                    Log.d("Order :",collectionType);
                    //Toast.makeText(getApplicationContext(),"Self Collection...payment only made through paypal",Toast.LENGTH_LONG).show();
                }
            }
        });


        btCard = findViewById(R.id.btCard);

        btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(collectionType == null)
                {
                    Toast.makeText(getApplicationContext(),"Select collection type",Toast.LENGTH_LONG).show();
                }
                else{
                placeOrder();
                    Toast.makeText(getApplicationContext(),"Order placed succefully",Toast.LENGTH_LONG).show();}

                Intent intent = new Intent(OrderReviewActivity.this,CardActivity.class);
                startActivity(intent);
            }
        });
        btCash = findViewById(R.id.btCash);
        btCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(collectionType == null)
                {
                    Toast.makeText(getApplicationContext(),"Select collection type",Toast.LENGTH_LONG).show();
                }
                else{
                placeOrder();
                    Toast.makeText(getApplicationContext(),"Order placed succefully",Toast.LENGTH_LONG).show();}

               Intent intent = new Intent(OrderReviewActivity.this,CashActivity.class);
               startActivity(intent);
            }
        });



    }
    public void showOrderDetails(JSONObject object) throws JSONException {



            tvNames.setText(object.getString("firstname")+" "+object.getString("surname"));
            tvOemail.setText(object.getString("email"));
            cellNo = object.getString("cellNo");
            tvphoneNo.setText(cellNo);
            tvCount.setText(""+SessionSingleton.getInstance().cartItems.size());
            deliveryAddr = etDaddress.getText().toString();

             grandTot = SessionSingleton.getInstance().itemTot;
            double total = 0;
            for(int i =0; i < grandTot.size();i ++)
            {
                total = total + grandTot.get(i);
            }

            tvSub.setText("R"+total);
             total = total+26;
            tvTotp.setText("R"+total);
            email = object.getString("email");




    }
    public void placeOrder()
    {
        String url = "http://192.168.2.155:8080/api/orderInfo/order/saveOrder";
        //RequestQueue queue = Volley.newRequestQueue(this);

        List<Product> toAddproducts = new ArrayList<>();
        Map<String,Object> data = new HashMap<>();
        long id = 0 ;
        String productName = "";
        String category = "";
        double price = 0.0;
        String description = "";
        int quantity = 0;
        double serviceFee = 27.00;

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObjectBuilder objectBuilder = null;

        for(Product product : SessionSingleton.getInstance().cartItems )
        {

            id = product.getId();
            productName = product.getProductName();
            category = product.getCategory();
            price = product.getPrice();
            description = product.getDescription();
            quantity = product.getQuantity();

            objectBuilder = Json.createObjectBuilder()
                    .add("id", id)
                    .add("productName", productName)
                    .add("category", category)
                    .add("price", price)
                    .add("description", description)
                    .add("quantity",quantity);
            arrayBuilder.add(objectBuilder);
        }


            JsonObject jsonParams = Json.createObjectBuilder()
                    .add("orderId", makeId())
                    .add("userId", SessionSingleton.getInstance().userSession)
                    .add("subTotal", calculateTotal())
                    .add("total", calculateTotal()+serviceFee)
                    .add("status", "Processing")
                    .add("collectionType", collectionType)
                    .add("restaurantName", SessionSingleton.getInstance().restaurantName)
                    .add("orderDate",getDate())
                    .add("cartItems",arrayBuilder)
                    .add("deliveryAddress",deliveryAddr)
                    .add("contactNO",cellNo)
                    .add("emailAddress",email)
                    .build();

            SessionSingleton.getInstance().orderObject = jsonParams;
        //Log.d("Order Info-------", SessionSingleton.getInstance().orderObject.toString());
            JsonArrayRequest request =  new JsonArrayRequest(Request.Method.POST, url, jsonParams.toString(), new Response.Listener<JsonObject>() {
                @Override
                public void onResponse(JsonObject response) {

                    // Log.d("Order Info-------",response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("",error.toString());
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
        }








    public String makeId()
    {
        String text = "MDM";
        String possibleValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int  i = 0; i < 10; i++)
            text += possibleValues.charAt((int) Math.floor(Math.random() * possibleValues.length()));

        return text;
    }

    public double calculateTotal()
    {
        grandTot = SessionSingleton.getInstance().itemTot;

        double total = 0.00;
        for(int i =0; i < grandTot.size();i ++)
        {
            total  = total + grandTot.get(i);

        }

        return total;
    }

    public String getDate()
    {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);

    }

}
