package com.example.cookingbysteps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.cookingbusteps.fragments.AuthFragment;
import com.example.cookingbusteps.fragments.RegistrationFragment;

public class RegistrationActivity extends AppCompatActivity {

    private EditText RegisusernameEditText, RegisemailEditText, RegispasswordEditText, AuthPasswordEditText, AuthEditText;
    private EditText currentEditText; // Добавим переменную для хранения ссылки на текущий EditText
    private Button registrationButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        RegisusernameEditText = findViewById(R.id.RegisUsernameEnter);
        RegisemailEditText = findViewById(R.id.RegisEmailEnter);
        RegispasswordEditText = findViewById(R.id.RegisPasswordEnter);
        registrationButton = findViewById(R.id.RegistrationButton);

        AuthPasswordEditText = findViewById(R.id.AuthPasswordEnter);
        AuthEditText = findViewById(R.id.AuthEmailEnter);
        NavigationView myNavView = findViewById(R.id.nav_view_id); // Проверьте правильность идентификатора



        myNavView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Exit_item) {
                    Toast.makeText(RegistrationActivity.this, "Выход из приложения",
                            Toast.LENGTH_SHORT).show();
                    finish();

                    return true;
                } else if (id == R.id.Enter_item) {
                    Toast.makeText(RegistrationActivity.this, "Выход в меню",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });

        RegistrationPagerAdapter adapter = new RegistrationPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AuthFragment(), "Авторизация");
        adapter.addFragment(new RegistrationFragment(), "Регистрация");

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }





    public class RegistrationPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public RegistrationPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
