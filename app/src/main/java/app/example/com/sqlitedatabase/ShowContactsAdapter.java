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


    /**
     * @param context The context of the calling activity
     * @param list ArrayList of SingleContact that contains contacts
     */
    public ShowContactsAdapter(Context context, ArrayList<SingleContact> list){
        mContext=context;
        mList= list;
    }

    /**
     * @return the size of ArrayList
     */
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

    /**
     * @param position ListView Item position
     * @param convertView view of ListView row
     * @param parent viewGroup of parent layout
     * @return the view of Custom ListView row
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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

        //SetText to the TextViews
        singleRow.name.setText(mList.get(position).name);
        singleRow.phone.setText(mList.get(position).phone);
        singleRow.relationship.setText(mList.get(position).relation);

        //OnClickListener to respond if an item is clicked
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if the context is instance of ShowContact Activity
                if(mContext instanceof ShowContact){
                    //Intent to detailed view of the Contact
                    Intent intent = new Intent( mContext, ModifyContact.class);
                    //Send data that the item has via intent
                    intent.putExtra("name", mList.get(position).name);
                    intent.putExtra("phone",mList.get(position).phone);
                    intent.putExtra("relation", mList.get(position).relation);
                    mContext.startActivity(intent);
                }
            }
        });
        return convertView; //return then modified view of the contact row
    }

    //private class to use/modify data from convertView easily
    private class SingleRow{
        TextView name, phone, relationship;
    }
}

//package-private class SingleContact to store all the information of a single contact
class SingleContact{
    String name,phone,relation;
}