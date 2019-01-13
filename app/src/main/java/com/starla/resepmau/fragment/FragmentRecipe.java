package com.starla.resepmau.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.starla.resepmau.R;
import com.starla.resepmau.adapter.RecipeAdapter;
import com.starla.resepmau.converter.WrappedListResponse;
import com.starla.resepmau.model.Recipe;
import com.starla.resepmau.service.PostService;
import com.starla.resepmau.util.ApiUtil;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRecipe extends Fragment {
    private static RecyclerView recyclerView;
    private static ArrayList<Recipe> mList = new ArrayList<>();
    private static RecipeAdapter recipeAdapter;
    private static PostService postService;
    private static SharedPreferences settings;
    private static Context context;

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        context = c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = getActivity().getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        //Toast.makeText(getActivity(), "Toekn : "+getToken(), Toast.LENGTH_SHORT).show();
        postService = ApiUtil.getPostService();
        recyclerView = view.findViewById(R.id.rv_recipe);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
    }


    @Override
    public void onResume() {
        super.onResume();
        mList.clear();
        loadData();
    }

    private static void loadData(){
        Call<WrappedListResponse<Recipe>> data = postService.getPosts("Bearer "+getToken());
        data.enqueue(new Callback<WrappedListResponse<Recipe>>() {
            @Override
            public void onResponse(Call<WrappedListResponse<Recipe>> call, Response<WrappedListResponse<Recipe>> response) {
                if(response.isSuccessful()){
                    WrappedListResponse<Recipe> body = response.body();
                    if(body.getStatus().equals("1")){
                        mList = (ArrayList<Recipe>) body.getData();
                        System.out.println("ydhnwb : "+mList);
                        for (Recipe r : mList){
                            System.out.println("ydhnwb : "+r.getTitle());
                        }
                        recipeAdapter = new RecipeAdapter(mList, context);
                        recyclerView.setAdapter(recipeAdapter);
                    }else {
                        Toast.makeText(context, "Reponse success with error code 0", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Response is not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WrappedListResponse<Recipe>> call, Throwable t) {
                Toast.makeText(context, "Failed to fetch. Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String getToken () { return settings.getString("TOKEN", "UNDEFINED"); }

    public static void refresh(){
        mList.clear();
        loadData();
    }

}
