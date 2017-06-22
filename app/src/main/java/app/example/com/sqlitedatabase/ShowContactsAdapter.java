package app.example.com.sqlitedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adnan Sharif on 6/22/17.
 */

class ShowContactsAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<SingleContact> mList;

    public ShowContactsAdapter(Context context, ArrayList<SingleContact> list){
        mContext=context;
        mList= list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //This block is written for AddMember Activity
        SingleRow singleRow;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_contact, parent, false);
            singleRow = new SingleRow();
            singleRow.name = convertView.findViewById(R.id.contact_name);
            singleRow.phone = convertView.findViewById(R.id.contact_phone);
            singleRow.relationship = convertView.findViewById(R.id.contact_relation);
            convertView.setTag(singleRow);
        } else {
            singleRow = (SingleRow) convertView.getTag();
        }
        singleRow.name.setText(mList.get(position).name);
        singleRow.phone.setText(mList.get(position).phone);
        singleRow.relationship.setText(mList.get(position).relation);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mContext instanceof ShowContact){
                    Intent intent = new Intent( mContext, ModifyContact.class);
                    intent.putExtra("name", mList.get(position).name);
                    intent.putExtra("phone",mList.get(position).phone);
                    intent.putExtra("relation", mList.get(position).relation);
                    mContext.startActivity(intent);
                }
            }
        });
        return convertView;
    }
    private class SingleRow{
        TextView name, phone, relationship;
    }
}

class SingleContact{
    String name,phone,relation;
}