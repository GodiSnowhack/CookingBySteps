package com.example.cookingbysteps;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cookingbysteps.fragment.DescriptionFragment;
import com.example.cookingbysteps.fragment.IngredientsFragment;
import com.example.cookingbysteps.fragment.StepsFragment;
import com.google.android.material.tabs.TabLayout;

public class CreateRecipePage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe_page);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPadapter vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new DescriptionFragment(),"Описание");
        vpAdapter.addFragment(new IngredientsFragment(),"Ингредиенты");
        vpAdapter.addFragment(new StepsFragment(),"Шаги");
        viewPager.setAdapter(vpAdapter);


    }
}