package com.jonas.nackbutik;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int categoryid = getIntent().getIntExtra("categoryid", 0);
        final ArrayList<Product> productList = new ArrayList<Product>();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "http://nackbutik.azurewebsites.net/GetProductsByCategoryId?categoryid=" + categoryid, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject j = response.getJSONObject(i);

                                Product p = new Product();
                                p.Id = j.getInt("Id");
                                p.Name = j.getString("Name");
                                productList.add(p);

                            }

                            ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<Product>(ProductListActivity.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, productList);

                            ListView listView = (ListView) findViewById(R.id.listView2);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent i = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                                    i.putExtra("productid", productList.get(position).Id);
                                    startActivity(i);
                                }
                            });

                            listView.setAdapter(arrayAdapter);


                        } catch (JSONException e) {
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        queue.add(jsArrayRequest);

    }

}
