package bocolly.pinheiro.culinary.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import bocolly.pinheiro.culinary.R;
import bocolly.pinheiro.culinary.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Recipe> recipes;
    private static ClickListener clickListener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.rowrecipes, viewGroup, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder h = (ViewHolder) viewHolder;

        Recipe r = recipes.get(i);

        h.tvName.setText("Name: "+r.getName());
        h.tvAuthor.setText("Author: "+r.getAuthor());

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private final TextView tvName;
        private final TextView tvAuthor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvName = itemView.findViewById(R.id.rs_tv_name);
            tvAuthor = itemView.findViewById(R.id.rs_tv_author);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(v, getAdapterPosition());
            return true;
        }
    } // fecha ViewHolder

    public void setOnItemClickListener(ClickListener clickListener){
        RecipeAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
        void onItemLongClick(View v, int position);
    }

} // fecha classe
