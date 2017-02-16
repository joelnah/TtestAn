package nah.prayer.anbada;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import nah.prayer.anbada.fragment.AllowedNumberFrag;
import nah.prayer.anbada.fragment.AreaCodeFrag;
import nah.prayer.anbada.fragment.BlockNumberFrag;
import nah.prayer.anbada.menu.NahMenu;
import nah.prayer.anbada.service.CallingService;

public class MainAct extends BaseAct {



    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getBoolean(allData.SET_PW,false)) {
            if (getApplication() instanceof BaseApp) {
                if (((BaseApp) getApplication()).isReturnedForground()) {
                    Intent i = new Intent(this, PasswordAct.class);
                    i.putExtra("isReturnedForground", true);
                    startActivity(i);
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editor.putBoolean(allData.FRIST, true).commit();


    //    LayoutSet();
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.titleA, AreaCodeFrag.class)
                .add(R.string.titleB, AllowedNumberFrag.class)
                .add(R.string.titleC, BlockNumberFrag.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

      //  startService(new Intent(this, CallingService.class));


    }



    public void mainClick(View v){
        switch (v.getId()){
            case R.id.btn_menu:
                //menu open
                startActivity(new Intent(this,NahMenu.class));
                overridePendingTransition(android.R.anim.slide_in_left, 0);
                break;
        }
    }

    public void onBackPressed() {
            DialogPlus.newDialog(this)
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        }
                    })
                    .setExpanded(false)
                    .setContentHolder(new ViewHolder(R.layout.popup_finish_layout))
                    .setGravity(Gravity.BOTTOM)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            switch (view.getId()) {
                                case R.id.cancel:
                                    dialog.dismiss();
                                    break;
                                case R.id.ok:
                                    dialog.dismiss();
                                    finish();
                                    break;
                            }
                        }
                    })
                    .create().show();

    }

}
