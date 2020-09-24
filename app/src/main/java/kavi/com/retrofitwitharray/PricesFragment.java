package kavi.com.retrofitwitharray;

import kavi.com.retrofitwitharray.R;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kavitha on 1.04.2019.
 */
public class PricesFragment extends Fragment {
    Pricebinding pricebinding;
    Database mydbase;
    DataAdapter adapter;
    private ArrayList<AndroidVersion> data;
    private static String BASE_URL="https://api.coincap.io/v2/assets";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        pricebinding= DataBindingUtil.inflate(inflater,R.layout.activity_main3, container, false);
        View vw=pricebinding.getRoot();
        pricebinding.setContainer(this);

        loadJSON();
        mydbase=new Database(getActivity());
        data=mydbase.getAllUserlist();


        pricebinding.recycle1.setHasFixedSize(true);
        adapter=new DataAdapter(data,getActivity());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        pricebinding.recycle1.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        pricebinding.recycle1.setAdapter(adapter);

        search(pricebinding.search);
       pricebinding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        pricebinding.swiperefresh.setRefreshing(true);
        data=mydbase.getAllUserlist();
        adapter.notifyDataSetChanged();
      //  loadJSON();
        pricebinding.swiperefresh.setRefreshing(false);


    }
});
        return vw;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void loadJSON() {
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            Integer deletedRows=   mydbase.truncateTable();
//                                if (deletedRows > 0) {
//                                    Toast.makeText(TaskListActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
//                                } else {
//                                    Toast.makeText(TaskListActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
//
//
//
//                            }

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject1 = heroArray.getJSONObject(i);
                                HashMap<String,String>queryvalues1=new HashMap<>();
                                queryvalues1.put("name",jsonObject1.getString("name"));
                                queryvalues1.put("priceUsd",jsonObject1.getString("priceUsd"));
                                queryvalues1.put("changePercent24Hr",jsonObject1.getString("changePercent24Hr"));
                                queryvalues1.put("symbol",jsonObject1.getString("symbol"));


                                boolean isInserted=mydbase.insertUser(queryvalues1);

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                    }
                }
        ) {

        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }




}

