package com.example.truthtable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    EditText expiration;
    GridView gridView;
    Button solve;
    String [] op = {"&" ,"|" ,"<->" ,"!" ,"->"};
    String expirationLine ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expiration = (EditText)findViewById(R.id.expiration);
        gridView = (GridView)findViewById(R.id.grid_view);
        solve = (Button)findViewById(R.id.solve);

        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expirationLine = expiration.getText().toString();
            }
        });
        String chars = "";
        int statments = 0;
        expirationLine.replaceAll(" ","");
        expirationLine.toLowerCase();
        for (int i=0 ;i<expirationLine.length() ; i++){
            if(expirationLine.charAt(i)>='a' && expirationLine.charAt(i)<='z'){
                if(chars.indexOf(expirationLine.charAt(i)) < 0){
                    statments++;
                    chars += expirationLine.charAt(i);
                }
            }
        }

        double convert = Math.pow(2,statments);
        for (int i=statments ; i>0 ; i--){
            
            char flag = 'T';
            int cols = i-1;

            double rows = Math.pow(2,statments);

            convert /= 2;

            while (rows > 0){
                for (int j=0 ; j<convert ; j++){
//                    col = flag
                    rows -- ;
                }

                flag = (flag =='T')?'F':'T';
            }
        }
    }
}
