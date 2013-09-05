package com.rd.seboapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rd.seboapp.Beans.SebosItem;
import com.rd.seboapp.R;
import com.rd.seboapp.Utils.EstanteVirtualParserUtil;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {

    private List<SebosItem> sebosItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataFromServer("http://www.estantevirtual.com.br/garimpepor/sebos-e-livreiros/");
    }

    private void getDataFromServer(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                EstanteVirtualParserUtil es = new EstanteVirtualParserUtil();
                sebosItems = es.getSebosList(response);
                if(sebosItems != null){
                    Iterator<SebosItem> sebosItemIterator = sebosItems.iterator();
                    while (sebosItemIterator.hasNext()){
                        Log.d("HELP",""+sebosItemIterator.next().getCity());
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
