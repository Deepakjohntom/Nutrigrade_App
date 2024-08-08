package com.example.nutrigrade;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    Context context;
    ImageView imageView;
    public List<Feedbackdata> listdata;

    public FeedbackAdapter(Context context, List<Feedbackdata> listdata) {
        this.context = context;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item_view_feedback,parent,false);
//        ImageView imageView = view.findViewById(R.id.imageView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {

        final Feedbackdata item1 = listdata.get(position);
        holder.textView.setText(item1.getName().trim());
//        holder.status.setText("Ratings: "+item1.getRatings().trim());
        String reply = item1.getReply() == null ? "" : item1.getReply().trim();

//        String imageUrl = Url.url+"productimages/"+item1.getImage();
//        Uri uri = Uri.parse(imageUrl);
//        String encodedUrl = uri.buildUpon().encodedPath(uri.getPath()).build().toString(); // Escape spaces in image names
//
//        // Define a RoundedCorners transformation with the desired corner radius
//        int radius = 30; // You can adjust the radius as needed
//        RoundedCorners roundedCorners = new RoundedCorners(radius);
//
//// Load image using Glide
//        Glide.with(context)
//                .load(encodedUrl)
//                .transform(new CenterCrop(), roundedCorners) // Apply CenterCrop and RoundedCorners transformations
////                .placeholder(R.drawable.deepfakelogo) // Placeholder image while loading
//                .error(R.drawable.error)
//                .into(holder.imageView);
            holder.linearrecycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,Viewfeedback.class);
                    i.putExtra("id",item1.getId());
                    i.putExtra("name",item1.getName().trim());
                    i.putExtra("feedback",item1.getFeedback().trim());
                    i.putExtra("user_id",item1.getUser_id().trim());
                    i.putExtra("product_id",item1.getProduct_id().trim());
                    i.putExtra("ratings",item1.getRatings().trim());
                    i.putExtra("reply", reply);
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
//            status = itemView.findViewById(R.id.status);
//            imageView = itemView.findViewById(R.id.imageView);
            linearrecycler = itemView.findViewById(R.id.linearrecycler);
        }
    }
}
