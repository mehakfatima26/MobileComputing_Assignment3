package com.edu.pucit.assignment_three;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity
{
    private List<Object> viewItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv;
        rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RVAdapter adapter=new RVAdapter(this,viewItems);
        rv.setAdapter(adapter);

        addItemsFromJSON();
    }

    private void addItemsFromJSON()
    {
        try {

            String jsonDataString = readJSONDataFromFile();
            JSONArray jsonArray = new JSONArray(jsonDataString);

            for (int i=0; i<jsonArray.length(); ++i) {

                JSONObject itemObj = jsonArray.getJSONObject(i);

                DataContainer dataContainer = new DataContainer();
                dataContainer.title=itemObj.getString("title");
                dataContainer.level=itemObj.getString("level");
                dataContainer.info=itemObj.getString("info");
                int length=itemObj.getString("url").length();
                if(itemObj.getString("url").substring(length - 3).equals("pdf"))
                    dataContainer.buttonText="DOWNLOAD";
                else
                    dataContainer.buttonText="READ ONLINE";
                dataContainer.imagePath=itemObj.getString("cover");
                viewItems.add(dataContainer);
            }

        } catch (IOException | JSONException e) {

        }
    }

    private String readJSONDataFromFile() throws IOException{

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.data);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
}

