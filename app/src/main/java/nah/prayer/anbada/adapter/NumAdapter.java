package nah.prayer.anbada.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nah.prayer.anbada.R;

import static nah.prayer.anbada.BaseAct.app;


/**
 * Created by NAH on 2016-04-14.
 */
public class NumAdapter extends ArrayAdapter {
    private Context con;
    private int resource;
    private ArrayList<HashMap> list = new ArrayList<>();
    public NumAdapter(Context con, int resource, ArrayList<HashMap> list){
        super(con,resource,list);
        this.con = con;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            LayoutInflater li = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(resource,null);
            holder = new ViewHolder();
        //    holder.lay = (LinearLayout) convertView.findViewById(R.id.lay);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
/*        final HashMap<String, String> map = new HashMap<>();
        map.put("type","[알림]");
        map.put("title","제목");
        map.put("content","내용\n내용\n내용\n내용\n내용\n내용\n내용\n내용\n내용\n내용\n내용\n내용");
        map.put("date","날자");*/

       final HashMap<String, String> map = list.get(position);
        holder.tv_num.setText(map.get("num"));
        holder.tv_content.setText(map.get("content"));

/*        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               app.ToastMessage("클릭클릭");
            }
        });*/
        return convertView;
    }
    class ViewHolder {
      //  LinearLayout lay;
        TextView tv_num,tv_content;
    }
}
