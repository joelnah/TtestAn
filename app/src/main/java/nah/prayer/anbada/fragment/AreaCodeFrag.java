package nah.prayer.anbada.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import nah.prayer.anbada.R;
import nah.prayer.anbada.data.AllData;

import static nah.prayer.anbada.BaseAct.editor;
import static nah.prayer.anbada.BaseAct.pref;

/**
 * Created by Nah on 2017-01-03.
 */

public class AreaCodeFrag extends Fragment{


    private View v;
    private CheckBox cb_area;
    private LinearLayout area[];
    private String area_num[];
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_area_code, container, false);

        LayoutSet();

        cb_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_area.isChecked()){
                    CheckAll(true);
                }else{
                    CheckAll(false);
                }
            }
        });
        if(pref.getBoolean(AllData.IS_BLOCK_ALL,true)){
            cb_area.performClick();
        }else {
            for (int i = 0; i < area.length; i++) {
                if (pref.getBoolean(area_num[i], false)) {
                    area[i].setSelected(true);
                    count++;
                }
            }
        }

        return v;
    }

    private void CheckAll(boolean stat){
        count = stat ? 18 : 0;

        for(int i = 0 ; i < area.length ; i++){
            area[i].setSelected(stat);
            editor.putBoolean(area_num[i], stat).commit();
        }
        editor.putBoolean(AllData.IS_BLOCK_ALL, stat).commit();
    }

    private void LayoutSet(){

        area_num = getResources().getStringArray(R.array.area_num);
        cb_area = (CheckBox) v.findViewById(R.id.cb_area);
        ((TextView) v.findViewById(R.id.str)).setText(getString(R.string.str_area));

        int areaID[] = {R.id.area01, R.id.area02, R.id.area03, R.id.area04, R.id.area05, R.id.area06, R.id.area07, R.id.area08, R.id.area09, R.id.area10,
                R.id.area11, R.id.area12, R.id.area13, R.id.area14, R.id.area15, R.id.area16, R.id.area17, R.id.area18};
        area = new LinearLayout[areaID.length];

        for(int i = 0 ; i < area.length ; i++){
            final int j = i;

        //   app.Logs(area_num[i] +" : "+pref.getBoolean(area_num[i],false));

            area[i] = (LinearLayout) v.findViewById(areaID[i]);
            area[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    area[j].setSelected( !area[j].isSelected());
                    editor.putBoolean(area_num[j], area[j].isSelected()).commit();
                    count = area[j].isSelected() ? ++count : --count;
                //    app.Logs("count : "+count);
                        if(cb_area.isChecked()){
                            cb_area.setChecked(false);
                            editor.putBoolean(AllData.IS_BLOCK_ALL, false).commit();
                        }else{
                            if(count == area.length){
                                cb_area.setChecked(true);
                                editor.putBoolean(AllData.IS_BLOCK_ALL, true).commit();
                            }
                        }

                }
            });
        }
    }
}
