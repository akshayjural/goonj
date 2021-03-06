package com.abhiroj.goonj.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.abhiroj.goonj.R;
import com.abhiroj.goonj.adapter.CardAdapter;
import com.abhiroj.goonj.adapter.ImageAdapter;
import com.abhiroj.goonj.animator.DepthPageTransformer;

import static com.abhiroj.goonj.data.Constants.HANDLER_POST_DELAYED_TIME;
import static com.abhiroj.goonj.data.Constants.IMAGE_COUNT;
import static com.abhiroj.goonj.data.Constants.SPAN_COUNT;
import static com.abhiroj.goonj.utils.Utility.checkNotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ViewPager imagepager;
    private ImageAdapter imageAdapter;
    public static final String TAG=MainFragment.class.getSimpleName();
    private View rootView;
    private int currentImage=0;
    private Handler slide_handler=makeHandler();
    private RecyclerView grid_view;



    public MainFragment() {
        // Required empty public constructor
    }

    Runnable automatic_sliding=new Runnable() {
        @Override
        public void run() {
            Log.d(TAG,"Runnable Called");
            imagepager.setCurrentItem((currentImage%IMAGE_COUNT)<4?currentImage++:setCurrentImage(0),true);
            slide_handler.postDelayed(automatic_sliding,HANDLER_POST_DELAYED_TIME);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_main, container, false);
        setupImageSlider();
        grid_view=(RecyclerView) rootView.findViewById(R.id.grid_view);
        grid_view.setHasFixedSize(true);
        grid_view.setLayoutManager(new GridLayoutManager(getContext(),SPAN_COUNT));
        grid_view.setAdapter(new CardAdapter(getContext()));
        return rootView;
    }

    private void setupImageSlider() {
        imagepager=(ViewPager)rootView.findViewById(R.id.imageslider);
        imagepager.setAdapter(new ImageAdapter(getChildFragmentManager()));
        imagepager.setCurrentItem(0);
        imagepager.setPageTransformer(true,new DepthPageTransformer());
    }

    /**
     * WIll use later for automation purpose, requires improvement
     */
    private void initiateHandler()
    {
        if(checkNotNull(slide_handler))
        {
            slide_handler.post(automatic_sliding);
        }
    }

    private void destroyHandler()
    {
        if(checkNotNull(slide_handler))
        {
         slide_handler.removeCallbacksAndMessages(null);
         slide_handler=null;
        }
    }

    /**
     *
     * @param currentImage current image displayed by image slider
     * @return
     */
    public int setCurrentImage(int currentImage) {
        Log.d(TAG,"Position Updated :"+currentImage);
         this.currentImage = currentImage;
    return currentImage;
    }


    private Handler makeHandler() {
        return new Handler();
    }
}
