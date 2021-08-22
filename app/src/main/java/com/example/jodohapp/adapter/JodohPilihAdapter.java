package com.example.jodohapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jodohapp.R;
import com.example.jodohapp.entity.UserModel;

import java.util.ArrayList;

public class JodohPilihAdapter extends BaseAdapter {

    private ArrayList<UserModel> userList;
    private Context context;

    public JodohPilihAdapter(ArrayList<UserModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pilih_jodoh_card,viewGroup,false);
            ImageView image = view.findViewById(R.id.image_pilih);
            ((TextView) view.findViewById(R.id.text_pilih_name)).setText(userList.get(i).getName());
            ((TextView) view.findViewById(R.id.text_pilih_age)).setText(userList.get(i).getAge());
            Glide.with(context).load("http://8392-139-192-45-236.ngrok.io/user/image/"+userList.get(i).getPicture()).into(image);
        }

        return view;
    }
}
