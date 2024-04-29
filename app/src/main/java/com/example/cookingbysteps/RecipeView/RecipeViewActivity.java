package com.example.cookingbysteps.RecipeView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.cookingbysteps.R;
import com.example.cookingbysteps.RecipeView.RecipeViewFragment.RecipeCommentsFragment;
import com.example.cookingbysteps.RecipeView.RecipeViewFragment.RecipeDescriptionFragment;
import com.example.cookingbysteps.RecipeView.RecipeViewFragment.RecipeStepsFragment;
import com.example.cookingbysteps.VPadapter;
import com.google.android.material.tabs.TabLayout;

public class RecipeViewActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VPadapter vpAdapter;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        id = getIntent().getIntExtra("recipe_id", -1);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager);

        vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        RecipeDescriptionFragment recipeDescriptionFragment = new RecipeDescriptionFragment();
        recipeDescriptionFragment.setId(id);
        vpAdapter.addFragment(recipeDescriptionFragment, "Описание");
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setId(id);
        vpAdapter.addFragment(recipeStepsFragment,"Шаги");
        RecipeCommentsFragment recipeCommentsFragment = new RecipeCommentsFragment();
        recipeCommentsFragment.setId(id);
        vpAdapter.addFragment(recipeCommentsFragment,"Комментарии");
        viewPager.setAdapter(vpAdapter);
    }

    public Integer getId(){
        return id;
    }
}