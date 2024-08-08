package com.example.nutrigrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    ImageView imageView;
    public List<Productdata> listdata;

    public ProductAdapter(Context context, List<Productdata> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item_view,parent,false);
        ImageView imageView = view.findViewById(R.id.imageView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {

        final Productdata item1 = listdata.get(position);
        holder.textView.setText(item1.getName().trim());
        holder.status.setText("Grade: "+item1.getGrade().trim());

        String imageUrl = Url.url+"storage/"+item1.getImage();
        Uri uri = Uri.parse(imageUrl);
        String encodedUrl = uri.buildUpon().encodedPath(uri.getPath()).build().toString(); // Escape spaces in image names

        // Define a RoundedCorners transformation with the desired corner radius
        int radius = 30; // You can adjust the radius as needed
        RoundedCorners roundedCorners = new RoundedCorners(radius);

// Load image using Glide
        Glide.with(context)
                .load(encodedUrl)
                .transform(new CenterCrop(), roundedCorners) // Apply CenterCrop and RoundedCorners transformations
//                .placeholder(R.drawable.deepfakelogo) // Placeholder image while loading
                .error(R.drawable.error)
                .into(holder.imageView);
            holder.linearrecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,Productdetails.class);
                    i.putExtra("prod_id",item1.getId());
                    i.putExtra("prod_name",item1.getName().trim());
                    i.putExtra("prod_details",item1.getDescription().trim());
                    i.putExtra("prod_ingredients",item1.getIngredients().trim());
                    i.putExtra("prod_pros",item1.getProns().trim());
                    i.putExtra("prod_cons",item1.getCons().trim());
                    i.putExtra("prod_calorie",item1.getCalorie().trim());
                    i.putExtra("prod_image",item1.getImage().trim());
                    i.putExtra("prod_grade",item1.getGrade().trim());
                    context.startActivity(i);
//                  ((Activity) context).overridePendingTransition(0, 0);
                }
            });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView,status;
        ImageView imageView;

        ConstraintLayout linearrecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.single_irem_textView);
            status = itemView.findViewById(R.id.status);
            imageView = itemView.findViewById(R.id.imageView);
            linearrecycler = itemView.findViewById(R.id.linearrecycler);
        }
    }
}
