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

        /*String[] title=new String[100];
        String[] level=new String[100];
        String[] info=new String[100];
        String[] button=new String[100];

        for(int i=0; i<title.length; i++)
        {

         title[i]="Data Structures and Algorithms with Object-Oriented Design Patterns in Python";
         level[i]="Level " + (i+1);
         info[i]="This book is about the fundamentals of data structures and algorithms. It uses object oriented design patterns and teaches topics like stacks, queues, lists, hashing and graphs. There are also versions for other programming languages.";
         button[i]="Read Online";
        }
        Integer[] image_id={R.drawable.android_100x100, R.drawable.sample150};
        RVAdapter adapter=new RVAdapter(this,image_id,title,level,info,button);
        rv.setAdapter(adapter);*/
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

