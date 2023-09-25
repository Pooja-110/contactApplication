package com.example.contactapplication;

import static androidx.core.app.ActivityCompat.requestPermissions;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder>{

    private List<ContactVO> contactVOList;
    private Context mContext;
    public AllContactsAdapter(List<ContactVO> contactVOList, Context mContext){
        this.contactVOList = contactVOList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.single_contact_view,parent,false);
        //ContactViewHolder contactViewHolder =
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        ContactVO contactVO = contactVOList.get(position);
        holder.tvContactName.setText(contactVO.getContactName());
        holder.tvPhoneNumber.setText(contactVO.getContactNumber());

        holder.call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                String number = contactVO.getContactNumber().toString();
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + number));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                //intent.setData(Uri.parse("tel:" + number));
               // Toast.makeText(mContext, "Call Placed" + number, Toast.LENGTH_SHORT).show();

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    mContext.startActivity(intent);
                } else {
                    requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 123);
                }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mContext, "Faild"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.msg.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view) {
                try {
                    String number =  contactVO.getContactNumber().toString();
                    String msg = "hello..";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, msg, null, null);
                    Toast.makeText(mContext, "SMS sent" + number, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mContext, "Faild"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

                }
                else {
                    ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.SEND_SMS},101);
                }

            }

        });
    }
    @Override
    public int getItemCount() {
        return contactVOList.size();
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView ivContactImage;
        TextView tvContactName;
        TextView tvPhoneNumber;
        Button call;
        Button msg;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            call = (Button) itemView.findViewById(R.id.call);
            msg = (Button) itemView.findViewById(R.id.message);
        }

    }

}
