package kavi.com.retrofitwitharray;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.widget.TextView;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import kavi.com.retrofitwitharray.databinding.ActivityMain2Binding;

public class MainActivity extends AppCompatActivity {

ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding=DataBindingUtil.setContentView(this,R.layout.activity_main2);
     binding.setContent(this);

     binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new PricesFragment());
        binding.textView.setText("Prices");

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_price:
                    fragment = new PricesFragment();
                    loadFragment(fragment);
                    binding.textView.setText("Prices");
                    return true;

                case R.id.navigation_star:

                    binding.textView.setText("Favourites");
                    fragment = new Favourites();
                    loadFragment(fragment);

                case R.id.navigation_portfolio:
                    fragment = new Favourites();
                   loadFragment(fragment);
                    binding.textView.setText("Portfolio");

                   return true;

                case R.id.navigation_news:
                    binding.textView.setText("News");
                    fragment = new Favourites();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_invest:
                    binding.textView.setText("Invest");

                    fragment = new Favourites();
                    loadFragment(fragment);
                    return true;


            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}