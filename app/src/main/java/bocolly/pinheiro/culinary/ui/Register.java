package bocolly.pinheiro.culinary.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.utilities.Utilities;
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
import bocolly.pinheiro.culinary.util.MatchParent;

public class Register extends AppCompatActivity {

    private EditText etName;
    private EditText etAuthor;
    private Spinner spType;
    private EditText etIngredients;
    private EditText etPreparation;
    private Button btRegister;
    private Drawer result = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        FirebaseApp.initializeApp(Register.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("recipes");

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe r = new Recipe();

                if (etName.getText().toString().isEmpty() & !MatchParent.validateNameAuthor(etName.getText().toString())) {
                    etName.setError("Enter a name!");
                    return;
                }
                r.setName(etName.getText().toString());

               if(etAuthor.getText().toString().isEmpty() & !MatchParent.validateNameAuthor(etAuthor.getText().toString())){
                   etAuthor.setError("Type an author!");
                   return;
               }
               r.setAuthor(etAuthor.getText().toString());

                if (spType.getSelectedItemPosition() == 0){
                    toast("Select a type!");
                    return;
                }
                r.setType(spType.getSelectedItem().toString());

                if (etIngredients.getText().toString().isEmpty() & !MatchParent.validateIgredientsPreparation(etIngredients.getText().toString())){
                    etIngredients.setError("Enter the ingredients!");
                    return;
                }
                r.setIngredients(etIngredients.getText().toString() );

                if (etPreparation.getText().toString().isEmpty() & !MatchParent.validateIgredientsPreparation(etPreparation.getText().toString())){
                    etPreparation.setError("Enter the method!");
                    return;
                }
                r.setMethodOfPreparation(etPreparation.getText().toString());


                banco.push().setValue(r);



                Intent it = new Intent(Register.this, MainActivity.class);
                startActivity(it);

                toast("Recipe successfully registered!");

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
                                startActivity(new Intent(Register.this,MainActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(Register.this,Register.class));
                                break;
                            case 2:
                                startActivity(new Intent(Register.this, SweetRecipes.class));
                                break;
                            case 3:
                                startActivity(new Intent(Register.this, SaltyRecipes.class));
                                break;
                            case 4:
                                startActivity(new Intent(Register.this, Search.class));
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
        etName = findViewById(R.id.re_et_name);
        etAuthor = findViewById(R.id.re_et_author);
        spType = findViewById(R.id.re_sp_type);
        etIngredients = findViewById(R.id.re_et_ingredients);
        etPreparation = findViewById(R.id.re_et_preparation);
        btRegister = findViewById(R.id.re_bt_register);
    }

}
