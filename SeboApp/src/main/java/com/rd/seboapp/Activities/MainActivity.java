package com.rd.seboapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.rd.seboapp.R;
import com.rd.seboapp.Utils.EstanteVirtualRequester;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EstanteVirtualRequester es = new EstanteVirtualRequester();
        es.urlRequest(this, "http://www.estantevirtual.com.br/garimpepor/sebos-e-livreiros/");
        es.getSebosListResponseParsed(this, "http://www.estantevirtual.com.br/garimpepor/sebos-e-livreiros/");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
