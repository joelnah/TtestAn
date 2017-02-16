package nah.prayer.anbada.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import nah.prayer.anbada.MainAct;
import nah.prayer.anbada.R;
import nah.prayer.anbada.adapter.NumAdapter;
import nah.prayer.anbada.data.AllData;
import nah.prayer.anbada.dialog.NumDialog;
import nah.prayer.anbada.swipemenulistview.SwipeMenu;
import nah.prayer.anbada.swipemenulistview.SwipeMenuCreator;
import nah.prayer.anbada.swipemenulistview.SwipeMenuItem;
import nah.prayer.anbada.swipemenulistview.SwipeMenuListView;

/**
 * Created by Nah on 2017-01-03.
 */

public class BlockNumberFrag extends Fragment{

    private View v;
    private SwipeMenuListView listView;
    private ArrayAdapter adapter;
    private ImageView addBtn;
    private int pos;
    private NumDialog numDialog;
    private final String db_type = "B";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_block_number, container, false);

        LayoutSet();
        adapter = new NumAdapter(getActivity(), R.layout.list_num, AllData.b);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numDialog = NumDialog.getInstance(0, numLisenter);
                numDialog
                        .bulidPhone("",0)
                        .bulidContent("",0)
                        .bulidButtonOk(getString(R.string.add))
                        .bulidButtonCancel(getString(R.string.cancel))
                        .show(getActivity().getSupportFragmentManager(), "");
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "cancel" item
                SwipeMenuItem cancelItem = new SwipeMenuItem(getActivity().getApplicationContext());
                cancelItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                cancelItem.setWidth(listView.dp2px(90));
                cancelItem.setTitle(R.string.cancel);
                cancelItem.setTitleSize(18);
                cancelItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(cancelItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(listView.dp2px(90));
                //deleteItem.setIcon(R.mipmap.ic_launcher);
                deleteItem.setTitle(R.string.del);
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(18);
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // cancel
                        break;
                    case 1:
                        // delete
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                ((MainAct)getActivity()).dbHelper.delete(db_type, AllData.b.get(position).get("_id").toString());
                                refresh();
                            }
                        }, 500);


                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                numDialog = NumDialog.getInstance(1, numLisenter);
                numDialog
                        .bulidPhone(AllData.b.get(position).get("num").toString(), 0)
                        .bulidContent(AllData.b.get(position).get("content").toString(), 0)
                        .bulidButtonOk(getString(R.string.edit))
                        .bulidButtonCancel(getString(R.string.cancel))
                        .show(getActivity().getSupportFragmentManager(), "");

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listView.smoothOpenMenu(position);
                return true;
            }
        });

        listView.setAdapter(adapter) ;

        return v;
    }

    NumDialog.NumDialogListener numLisenter = new NumDialog.NumDialogListener() {

        @Override
        public void ok(int type, String phone, String content, int resultCode) {
            switch (type){
                case 0: //추가
                    ((MainAct)getActivity()).dbHelper.insert(db_type, phone, content);
                    refresh();
                    break;
                case 1: //수정
                    ((MainAct)getActivity()).dbHelper.update(db_type, phone, content, AllData.b.get(pos).get("_id").toString());
                    refresh();
                    break;
            }
        }

        @Override
        public void cancel(int resultCode) {
        }
    };

    private void refresh(){
        adapter = new NumAdapter(getActivity(), R.layout.list_num, AllData.b);
        listView.setAdapter(adapter) ;
    }

    private void LayoutSet(){
        ((TextView) v.findViewById(R.id.str)).setText(getString(R.string.str_block));
        listView = (SwipeMenuListView) v.findViewById(R.id.listView);
        addBtn = (ImageView) v.findViewById(R.id.addBtn);
    }

}
