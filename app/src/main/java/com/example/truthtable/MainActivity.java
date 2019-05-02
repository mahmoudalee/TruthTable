package com.example.truthtable;

import android.content.Context;
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
    StatementSplitters ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expiration = (EditText)findViewById(R.id.expiration);
        gridView = (GridView)findViewById(R.id.grid_view);
        solve = (Button)findViewById(R.id.solve);

        final Context context =this;


//        ss = new StatementSplitters();


        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expirationLine = expiration.getText().toString();

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


                gridView.setNumColumns(statments+1);

                double rowNum = Math.pow(2,statments);
                double convert = rowNum;

                String[][] data = new String[(int)rowNum][statments+1];

                for (int i=statments ,I=0 ; i>0 ; i--,I++){

                    char flag = 'T';

                    double rows = rowNum;

                    convert /= 2;

                    int rowsToData = 0;

                    while (rows > 0){
                        for (int j=0 ; j<convert ; j++){
                            data[rowsToData][I] = ""+flag;
                            rows -- ;
                            rowsToData++;
                        }

                        flag = (flag =='T')?'F':'T';
                    }
                }

                boolean[] booleans = new boolean[statments];

                for (int i =0 ; i<rowNum ;i++){
                    for (int j=0 ; j<statments ;j++){
                        if (data[i][j].equals("T"))
                            booleans[j] = true;
                        else
                            booleans[j] = false;
                    }
                    ss = new StatementSplitters(expirationLine ,booleans);
//                    ss.
//                    ss.setSimpleStatementValues(booleans);

                    data[i][statments] = ss.solve()?"T":"F" ;
                }

                MainAdapter adapter = new MainAdapter(context ,statments ,chars ,expirationLine ,data);
                gridView.setAdapter(adapter);
            }
        });
    }
}
