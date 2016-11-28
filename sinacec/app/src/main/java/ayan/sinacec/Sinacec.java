package ayan.sinacec;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import modelo.basedatos.Usuario;
import utilerias.WebService;

public class Sinacec extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Area.OnFragmentInteractionListener,
        AreasManagement.OnFragmentInteractionListener,
        Doors.OnFragmentInteractionListener,
        Mall.OnFragmentInteractionListener,
        OffersManagement.OnFragmentInteractionListener,
        Product.OnFragmentInteractionListener,
        ProductsManagement.OnFragmentInteractionListener,
        Profile.OnFragmentInteractionListener,
        RentedShop.OnFragmentInteractionListener,
        RentedShops.OnFragmentInteractionListener,
        Search.OnFragmentInteractionListener,
        SelectionMall.OnFragmentInteractionListener,
        Service.OnFragmentInteractionListener,
        Services.OnFragmentInteractionListener,
        ServicesManagement.OnFragmentInteractionListener,
        Settings.OnFragmentInteractionListener,
        Shop.OnFragmentInteractionListener,
        ShopsManagement.OnFragmentInteractionListener,
        SignIn.OnFragmentInteractionListener,
        SignUpTenant.OnFragmentInteractionListener,
        SignUpTenantValidation.OnFragmentInteractionListener
        {

    private NavigationView navigationView;

    private static SharedPreferences spws;//share preferences web service

    public static SharedPreferences getSpws(){
        return spws;
    }

    public void setWebServiceSettings(){
        WebService.setSettings(spws.getString(Settings.ipHost,"deisacv.dlinkddns.com"), spws.getInt(Settings.ipPort,8080), spws.getString(Settings.ipPath,"/webService/Sinacec?wsdl"), spws.getString(Settings.ipNamespace,"http://service/"));
    }

    public void simulateNavigationItemSelected(int idItem, Fragment fragment){
        setMenuItem(navigationView.getMenu().findItem(idItem), fragment);
    }

    public void setMenuItem(MenuItem item, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL,fragment).commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        managementSession(Profile.getUser(), true);
    }

    public void managementSession(Usuario user, boolean start_end){
        if(user != null) {
            switch (user.getTipo()) {
                case 'C':
                    managementSessionManager(start_end);
                    break;
                case 'L':
                    managementSessionTenant(start_end);
                    break;
            }
            if(start_end == false){
                Profile.closeSession();
                Mall.closeSession();
                RentedShop.closeSession();
            }
        }
    }

    public void managementSessionManager(boolean start_end){
        navigationView.getMenu().findItem(R.id.nav_signOut).setVisible(start_end);
        navigationView.getMenu().findItem(R.id.nav_manager).setVisible(start_end);
        //simulateNavigationItemSelected(R.id.nav_selectionMall);
    }

    public void managementSessionTenant(boolean start_end){
        navigationView.getMenu().findItem(R.id.nav_signOut).setVisible(start_end);
        navigationView.getMenu().findItem(R.id.nav_tenant).setVisible(start_end);
        //simulateNavigationItemSelected(R.id.nav_selectionRentedPlace);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinacec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spws = getSharedPreferences("webService", Context.MODE_PRIVATE);
        setWebServiceSettings();
        simulateNavigationItemSelected(R.id.nav_selectionMall, new SelectionMall());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sinacec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            setMenuItem(item, new Settings());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment f = null;
        switch (id){
            case R.id.nav_mall:
                f = new Mall();
                break;
            case R.id.nav_search:
                f = new Search();
                break;
            case R.id.nav_rentedShops:
                f = new RentedShops();
                break;
            case R.id.nav_services:
                f = new Services();
                break;
            case R.id.nav_selectionMall:
                f = new SelectionMall();
                break;
            case R.id.nav_signIn:
                f = new SignIn();
                break;
            case R.id.nav_signOut:
                managementSession(Profile.getUser(), false);
                f = new SignIn();
                break;
            case R.id.nav_profileManager:
                f = new Profile();
                break;
            case R.id.nav_editionMall:
                f = new Mall();
                break;
            case R.id.nav_areas:
                f = new AreasManagement();
                break;
            case R.id.nav_shop:
                f = new ShopsManagement();
                break;
            case R.id.nav_recordsValidation:
                f = new SignUpTenantValidation();
                break;
            case R.id.nav_profileTenant:
                f = new Profile();
                break;
            case R.id.nav_editionRentedShop:
                f = new RentedShop();
                break;
            case R.id.nav_selectionRentedShop:
                f = new RentedShops();
                break;
            case R.id.nav_productsManagement:
                f = new ProductsManagement();
                break;
            case R.id.nav_servicesManagement:
                f = new ServicesManagement();
                break;
            case R.id.nav_offersManagement:
                f = new OffersManagement();
                break;
        }
        if(f != null){
            setMenuItem(item,f);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
