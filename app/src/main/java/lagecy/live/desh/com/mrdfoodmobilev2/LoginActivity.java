package lagecy.live.desh.com.mrdfoodmobilev2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private Button btLogin2;
    private String TAG = "Message";
    private String email,password;
    private Long sessionId = null;
    private TextView regText;
    private DrawerLayout dLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        regText = findViewById(R.id.regText);
        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin2 = findViewById(R.id.btLogin2);
        btLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(etEmail == null  && etPassword == null)
//                {
//                    Toast.makeText(getApplicationContext(),"Field cannot be left blank",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                loginVolley("eddyk@gmail.com","12345");
                //}
            }
        });

    }

    public void loginVolley(String email,String password)
    {

        String url = "http://192.168.2.155:8080/api/account/acc/login?email=" + email + "&password=" + password;

//
//        final AlertDialog dialog = new SpotsDialog(getApplicationContext());
//        dialog.show();
//       final ProgressDialog dialog = new ProgressDialog(getApplicationContext());
//        dialog.setMessage("Doing something, please wait.");
//        dialog.show();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG,"Response :"+response);
                          //  Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                            sessionId = response.getLong("id");
                            Toast.makeText(getApplicationContext(), "Session Is :"+sessionId, Toast.LENGTH_SHORT).show();
                            SessionSingleton.getInstance().userSession = sessionId;
                            JsonObjectRequest request2 =  new JsonObjectRequest(Request.Method.GET, "http://192.168.2.155:8080/api/person/account/person/getPerson/" + SessionSingleton.getInstance().userSession,
                                    null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();

                                    SessionSingleton.getInstance().personObject = response;
                                    Log.d("Object========",SessionSingleton.getInstance().personObject.toString());
                                   // dialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext() ,WelcomeActivity.class);
                                  //  intent.putExtra(response.getString("id"),sessionId);
                                    startActivity(intent);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request2);
                            request2.setRetryPolicy(new DefaultRetryPolicy(500000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                Log.d("------------>", error.toString() );
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);
    }

}
