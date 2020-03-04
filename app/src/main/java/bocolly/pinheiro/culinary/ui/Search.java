package bocolly.pinheiro.culinary.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import bocolly.pinheiro.culinary.util.MatchParent;

public class Search extends AppCompatActivity {

    private EditText etName;
    private Button btSearch;
    private RecyclerView rvRecipes;
    private ArrayList<Recipe> recipes;
    private RecipeAdapter adapter;
    private Drawer result = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();



        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseApp.initializeApp(Search.this);
                DatabaseReference banco = FirebaseDatabase.getInstance().getReference();

                if (!MatchParent.validateNameAuthor(etName.getText().toString())){
                    toast("String universalizada");
                    etName.setError("String universalizada");
                    return;
                }

                Query querySearch = banco.child("recipes").orderByChild("name").startAt(etName.getText().toString()).endAt(etName.getText().toString()+"\uf8ff");
                querySearch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        recipes.clear();
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            Recipe r = data.getValue(Recipe.class);
                            r.setKey(data.getKey());
                            recipes.add(r);
                        }
                        adapter.notifyDataSetChanged();

                        if (recipes.isEmpty()){
                            toast("No recipe found!");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        adapter.setOnItemClickListener(new RecipeAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Recipe r = recipes.get(position);
                Intent it = new Intent(Search.this, SelectedRecipe.class);
                it.putExtra("r", r);
                startActivity(it);
            }

            @Override
            public void onItemLongClick(View v, int position) {
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
                                startActivity(new Intent(Search.this,MainActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(Search.this,Register.class));
                                break;
                            case 2:
                                startActivity(new Intent(Search.this, SweetRecipes.class));
                                break;
                            case 3:
                                startActivity(new Intent(Search.this, SaltyRecipes.class));
                                break;
                            case 4:
                                startActivity(new Intent(Search.this, Search.class));
                                break;
                        }
                        return false;
                    }
                }).build();

    }

    public void toast(String msg){
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void init(){
        etName = findViewById(R.id.search_et_name);
        btSearch = findViewById(R.id.search_bt_search);
        rvRecipes = findViewById(R.id.search_rv_recipes);

        recipes = new ArrayList<>();
        adapter = new RecipeAdapter(Search.this, recipes);
        rvRecipes.setAdapter(adapter);

        rvRecipes.setHasFixedSize(true);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }

}
