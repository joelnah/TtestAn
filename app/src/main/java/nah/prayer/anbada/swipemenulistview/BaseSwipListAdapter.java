package nah.prayer.anbada.swipemenulistview;




import android.widget.BaseAdapter;

/**
 * Created by Nah on 2017-01-14.
 */
public abstract class BaseSwipListAdapter extends BaseAdapter {

    public boolean getSwipEnableByPosition(int position){
        return true;
    }



}
