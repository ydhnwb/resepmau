package com.starla.resepmau.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.starla.resepmau.DetailActivity;
import com.starla.resepmau.R;
import com.starla.resepmau.model.Recipe;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<Recipe> listOfRecipe;
    private Context context;

    public RecipeAdapter(ArrayList<Recipe> listOfRecipe, Context context) {
        this.listOfRecipe = listOfRecipe;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup p0, int i) {
        LayoutInflater inflater = LayoutInflater.from(p0.getContext());
        View v = inflater.inflate(R.layout.single_list, p0,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) { viewHolder.bindData(listOfRecipe.get(i), context); }

    @Override
    public int getItemCount(){ return listOfRecipe.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,content;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_list_title);
            content = itemView.findViewById(R.id.tv_list_content);
        }

        void bindData(final Recipe recipe, final Context context){
            title.setText(recipe.getTitle());
            content.setText(recipe.getContent());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("ID", recipe.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

}