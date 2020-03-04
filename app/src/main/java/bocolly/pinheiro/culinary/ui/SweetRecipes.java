package bocolly.pinheiro.culinary.ui;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

import bocolly.pinheiro.culinary.R;
import bocolly.pinheiro.culinary.adapter.RecipeAdapter;
import bocolly.pinheiro.culinary.model.Recipe;

public class SweetRecipes extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private ArrayList<Recipe> recipes;
    private RecipeAdapter adapter;
    private Drawer result = null;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweet_recipes);

        init();

        FirebaseApp.initializeApp(SweetRecipes.this);

        DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

        Query querySweets = banco.child("recipes").orderByChild("type").equalTo("Sweet");
        querySweets.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipes.clear();
                for (final DataSnapshot data: dataSnapshot.getChildren()){
                    Recipe r = data.getValue(Recipe.class);
                    r.setKey(data.getKey());
                    recipes.add(r);

                }
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter.setOnItemClickListener(new RecipeAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Recipe r = recipes.get(position);
                Intent it = new Intent(SweetRecipes.this, SelectedRecipe.class);
                it.putExtra("r", r);
                startActivity(it);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                DatabaseReference banco = FirebaseDatabase.getInstance().getReference();
                Recipe r = recipes.get(position);
                banco.child(r.getKey()).removeValue();
                adapter.notifyDataSetChanged();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.circular_background)
                .addProfiles(
                        //USUARIO_____new ProfileDrawerItem().withName().withEmail("thiagocury@gmail.com").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)),
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener(){
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIdentifier(0).withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Create new recipe").withIdentifier(1),
                        new SecondaryDrawerItem().withName("Sweet recipes").withIdentifier(2),
                        new SecondaryDrawerItem().withName("Salty recipes").withIdentifier(3),
                        new SecondaryDrawerItem().withName("Search").withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch ((int)drawerItem.getIdentifier()){
                            case 0:
                                startActivity(new Intent(SweetRecipes.this,MainActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(SweetRecipes.this,Register.class));
                                break;
                            case 2:
                                startActivity(new Intent(SweetRecipes.this, SweetRecipes.class));
                                break;
                            case 3:
                                startActivity(new Intent(SweetRecipes.this, SaltyRecipes.class));
                                break;
                            case 4:
                                startActivity(new Intent(SweetRecipes.this, Search.class));
                                break;
                        }
                        return false;
                    }
                }).build();


    }

    private void init(){
        rvRecipes = findViewById(R.id.sr_rv_sweets);

        recipes = new ArrayList<>();
        adapter = new RecipeAdapter(SweetRecipes.this, recipes);
        rvRecipes.setAdapter(adapter);

        rvRecipes.setHasFixedSize(true);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

}
