package com.example.truthtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


class MainAdapter extends BaseAdapter {
    private int statments;
    private LayoutInflater inflater;
    private Context context;
    private String chars;
    private String exepration;
    private String[][] data;
    int rows = 0;
    int rowStatmentCols = 2;
    int edge ;
    public MainAdapter(Context context ,int statments ,String chars ,String exepration ,String[][] data){
        this.statments = statments;
        this.context = context;
        this.chars = chars;
        this.exepration = exepration;
        this.data = data;
        edge =statments+1;
    }
    @Override
    public int getCount() {
        return ((int)Math.pow(2,statments)+1) * (statments+1);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView ==null){
            convertView = inflater.inflate(R.layout.row_item , null);
        }
        TextView textView = convertView.findViewById(R.id.text_view);

        if(position < statments){

            textView.setText(""+chars.charAt(position));
        }
        else if(position == statments){
            textView.setText(exepration);
        }

        else if(position <  statments+edge) {
            textView.setText(data[rows][position-edge]);
            rowStatmentCols++;
        }

        else if(position == statments+edge){
            textView.setText(data[rows][position-edge]);
            rows++;
            edge += statments+1;
        }
        return convertView;
    }
}



