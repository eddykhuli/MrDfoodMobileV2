package lagecy.live.desh.com.mrdfoodmobilev2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardActivity extends AppCompatActivity {

    private EditText etPin,etAccNo;
    private AutoCompleteTextView etBankName;
    private Button btPAy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etPin = findViewById(R.id.etPin);
        etAccNo = findViewById(R.id.etAccno);
        btPAy =  findViewById(R.id.btPay);
        etBankName = findViewById(R.id.tvBankName);
        String[] banks={"Absa Bank","Standard Bank","Capitec Bank","Nedbank"};
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,banks);

        etBankName.setAdapter(adapter);
        etBankName.setThreshold(1);

        Log.d("Order Info-------", SessionSingleton.getInstance().orderObject.toString());
        Log.d("Person id-------", SessionSingleton.getInstance().personObject.toString());
            btPAy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String accNo = etAccNo.getText().toString();
                    String pin = etPin.getText().toString();
                    String url2 = "http://192.168.2.155:8080/payment/creditCardpayment?accountNumber="+accNo+"&pin="+pin;
                    //http://192.168.2.155:8080/payment/creditCardpayment?accountNumber=123456789&pin=1234
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                           try {
                                String id = SessionSingleton.getInstance().orderObject.getString("orderId");

                            Log.d("Order id-------", id);

                                Toast.makeText(getApplicationContext(),"Account found",Toast.LENGTH_LONG).show();
                                makePayment(id,SessionSingleton.getInstance().personObject.getLong("id"),"Paid","Card",SessionSingleton.getInstance().orderObject.getInt("total"));
                                Intent intent = new Intent(getApplicationContext(),CardReviewActivity.class);
                                startActivity(intent);
                           } catch (JSONException e) {
                               e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Account not found",Toast.LENGTH_LONG).show();

                        }
                    });
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                }
            });




    }


    public void makePayment(String id,long userId,String status,String paymentT,double amountPayed)
    {
        String url = "http://192.168.2.155:8080/api/Payment/order/payment";

        Map<String,Object> data = new HashMap<>();

        data.put("orderId",id);
        data.put("userId",userId);
        data.put("status",status);
        data.put("paymentType",paymentT);
        data.put("amountPayed",amountPayed);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Order Review");
//                builder.setMessage("Thank you for ordering with us, your order is being processed and check your email\nWe will be you contacting there");
//
//                // create and show the alert dialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//                // add a button
//                builder.setPositiveButton("Continue Shopping", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class) ;
//                        SessionSingleton.getInstance().logout();
//                        startActivity(intent);
//                    }
//                });
//                builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class) ;
//                        SessionSingleton.getInstance().logout();
//                        startActivity(intent);
//                    }
//                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Wrong bank details provided",Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

}
