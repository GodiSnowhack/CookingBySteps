package com.example.cookingbysteps;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.cookingbysteps.fragment.CreateRecipeFragment;
import com.example.cookingbysteps.fragment.DescriptionFragment;
import com.example.cookingbysteps.fragment.IngredientsFragment;
import com.example.cookingbysteps.fragment.StepsFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class CreateRecipePage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VPadapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe_page);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);

        vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new DescriptionFragment(),"Описание");
        vpAdapter.addFragment(new IngredientsFragment(),"Ингредиенты");
        vpAdapter.addFragment(new StepsFragment(),"Шаги");
        vpAdapter.addFragment(new CreateRecipeFragment(),"Создать рецепт");
        viewPager.setAdapter(vpAdapter);

        NavigationView myNavView = findViewById(R.id.nav_view_id);
        View headerView = myNavView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.UsernameText);

        NavigationManager.setupNavigation(myNavView, this, navUsername);
    }

    public VPadapter getVpAdapter() {
        return vpAdapter;
    }
}