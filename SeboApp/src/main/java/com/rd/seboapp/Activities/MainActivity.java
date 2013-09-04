package com.rd.seboapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.rd.seboapp.Beans.SebosItem;
import com.rd.seboapp.R;
import com.rd.seboapp.Utils.EstanteVirtualRequester;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EstanteVirtualRequester es = new EstanteVirtualRequester();
        es.urlRequest(this, "http://www.estantevirtual.com.br/garimpepor/sebos-e-livreiros/", "sebosHTML");
        List<SebosItem> sebosItems = es.getSebosListResponseParsed(this, "http://www.estantevirtual.com.br/garimpepor/sebos-e-livreiros/", "sebosHTML");
        if(sebosItems != null){
            Log.d("HELP",sebosItems.toString());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
