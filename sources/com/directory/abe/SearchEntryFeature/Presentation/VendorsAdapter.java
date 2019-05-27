package com.directory.abe.SearchEntryFeature.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.directory.abe.C0246R;
import com.directory.abe.GSONModels.EntryModel;
import java.util.ArrayList;

public class VendorsAdapter extends ArrayAdapter<EntryModel> {
    private static final String TAG = VendorsAdapter.class.getSimpleName();
    private Context context;
    private ImageView image;
    private LayoutInflater inflater;
    private ArrayList<EntryModel> objs;
    private int resource;

    public class VendorViewHolder {
        private TextView addy1;
        private TextView addy2;
        private TextView addy3;
        private TextView email;
        private TextView id;
        private TextView name;
        private TextView postcode;
        private TextView tel;
        private TextView type;
        private TextView web;
    }

    public VendorsAdapter(Context context, int resource, ArrayList<EntryModel> objs) {
        super(context, resource, objs);
        this.context = context;
        this.objs = objs;
        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        VendorViewHolder holder;
        if (convertView == null) {
            convertView = this.inflater.inflate(this.resource, null);
            holder = new VendorViewHolder();
            holder.name = (TextView) convertView.findViewById(C0246R.id.tv_vendor_name_list_item);
            holder.addy3 = (TextView) convertView.findViewById(C0246R.id.tv_vendor_address3_list_item);
            holder.postcode = (TextView) convertView.findViewById(C0246R.id.tv_vendor_postcode_list_item);
            holder.addy1 = (TextView) convertView.findViewById(C0246R.id.tv_vendor_address1_list_item);
            holder.addy2 = (TextView) convertView.findViewById(C0246R.id.tv_vendor_address2_list_item);
            holder.tel = (TextView) convertView.findViewById(C0246R.id.tv_vendor_telephone_list_item);
            holder.email = (TextView) convertView.findViewById(C0246R.id.tv_vendor_email_list_item);
            holder.web = (TextView) convertView.findViewById(C0246R.id.tv_vendor_website_list_item);
            holder.type = (TextView) convertView.findViewById(C0246R.id.tv_vendor_type_list_item);
            this.image = (ImageView) convertView.findViewById(C0246R.id.image_vendor_data_item);
            this.image.setImageResource(C0246R.drawable.logo);
            convertView.setTag(holder);
        } else {
            holder = (VendorViewHolder) convertView.getTag();
        }
        holder.name.setText(((EntryModel) this.objs.get(position)).getVendorName());
        holder.addy3.setText(((EntryModel) this.objs.get(position)).getVendorAddress3());
        holder.postcode.setText(((EntryModel) this.objs.get(position)).getVendorPostcode());
        holder.addy2.setText(((EntryModel) this.objs.get(position)).getVendorAddress1());
        holder.addy1.setText(((EntryModel) this.objs.get(position)).getVendorAddress2());
        holder.tel.setText(((EntryModel) this.objs.get(position)).getVendorTelephone());
        holder.email.setText(((EntryModel) this.objs.get(position)).getVendorEmail());
        holder.web.setText(((EntryModel) this.objs.get(position)).getVendorWebsite());
        holder.type.setText(((EntryModel) this.objs.get(position)).getVendorType());
        this.image = (ImageView) convertView.findViewById(C0246R.id.image_vendor_data_item);
        this.image.setImageResource(C0246R.drawable.logo);
        return convertView;
    }
}
