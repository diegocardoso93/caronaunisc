package br.unisc.pdm.caronauniscapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Diego on 16/10/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int cItem = 0;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        //if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            if (cItem<9) {
                imageView.setLayoutParams(new GridView.LayoutParams(70, 58));
            }else{
                imageView.setLayoutParams(new GridView.LayoutParams(62, 58));
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(0, 0, 0, 0);
        //} else {
        //    imageView = (ImageView) convertView;
        //}
        cItem++;
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.dom, R.drawable.seg,
            R.drawable.ter, R.drawable.qua,
            R.drawable.qui, R.drawable.sex,
            R.drawable.sab,
            R.drawable.agdar, R.drawable.tipo,
            R.drawable.agrec, R.drawable.tipo,
            R.drawable.tipo, R.drawable.tipo,
            R.drawable.tipo,
            R.drawable.agm, R.drawable.turno,
            R.drawable.agt, R.drawable.turno,
            R.drawable.turno, R.drawable.turno,
            R.drawable.turno,
            R.drawable.ag1, R.drawable.qtde,
            R.drawable.agblank, R.drawable.qtde,
            R.drawable.qtde, R.drawable.qtde,
            R.drawable.qtde
    };
}