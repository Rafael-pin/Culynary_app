package bocolly.pinheiro.culinary.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

import bocolly.pinheiro.culinary.R;
import bocolly.pinheiro.culinary.model.Recipe;

public class SelectedRecipe extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAuthor;
    private TextView tvType;
    private TextView tvIngredients;
    private TextView tvPreparation;
    private Drawer result = null;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_recipe);
        init();

        if(getIntent().hasExtra("r")){
            Recipe r = (Recipe)getIntent().getSerializableExtra("r");

            tvName.setText(r.getName());
            tvAuthor.setText(r.getAuthor());
            tvType.setText(r.getType());
            tvIngredients.setText(r.getIngredients());
            tvPreparation.setText(r.getMethodOfPreparation());
        }
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
                                startActivity(new Intent(SelectedRecipe.this,MainActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(SelectedRecipe.this,Register.class));
                                break;
                            case 2:
                                startActivity(new Intent(SelectedRecipe.this, SweetRecipes.class));
                                break;
                            case 3:
                                startActivity(new Intent(SelectedRecipe.this, SaltyRecipes.class));
                                break;
                            case 4:
                                startActivity(new Intent(SelectedRecipe.this, Search.class));
                                break;
                        }
                        return false;
                    }
                }).build();

    }

    public void init(){
        tvName = findViewById(R.id.select_tv_name);
        tvAuthor = findViewById(R.id.selec_tv_author);
        tvType = findViewById(R.id.selec_tv_type);
        tvIngredients = findViewById(R.id.selec_tv_ingredients);
        tvPreparation = findViewById(R.id.selec_tv_preparation);
    }

}
