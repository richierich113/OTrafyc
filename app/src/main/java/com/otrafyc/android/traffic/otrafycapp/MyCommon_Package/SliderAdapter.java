package com.otrafyc.android.traffic.otrafycapp.MyCommon_Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.otrafyc.android.traffic.otrafycapp.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    //generate parameterized SliderAdapter constructor with context
    public SliderAdapter(Context context) {
        this.context = context;
    }

    // define array of items to be used in ViewPager2
    int images[] = {R.drawable.onboarding1, R.drawable.pngwing_1, R.drawable.pngwing, R.drawable.car_sharing_service};

    int headings[] = {R.string.get_fastest_route_to_destination, R.string.get_night_map_view,R.string.restrict_search_to_your_country, R.string.get_real_time_traffic_update};

    int descriptions[] = {R.string.get_fastest_route_to_destination, R.string.get_night_map_view,R.string.restrict_search_to_your_country, R.string.get_real_time_traffic_update};


    @Override
    public int getCount() {
        //set the length or number of the slides
        // headings.length is used to get the length since every slide will have a heading
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // view is from the ConstraintLayout of our slider layout which is the object in this case
        // == is used since we are working with a boolean method
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // inflate slider layout
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        // set the hooks or initiate items in slider layout
        ImageView imageViews = view.findViewById(R.id.slider_imageView);
        TextView titles = view.findViewById(R.id.slider_heading);
        TextView desc = view.findViewById(R.id.slider_description);

        // set view items to their specified arrays and positions of items
        imageViews.setImageResource(images[position]);
        titles.setText(headings[position]);
        desc.setText(descriptions[position]);

        //add view containing slider layout to container which is to contain views
        container.addView(view);



        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ConstraintLayout) object );
    }
}
