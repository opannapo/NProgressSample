package napodev.nprogress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import napodev.framework.bework.corebase.worker.adapter.BaseRecyclerViewAdapter;
import napodev.framework.bework.corebase.worker.adapter.BaseViewHolder;
import napodev.nprogress.R;

/**
 * Created by opannapo on 11/17/16.
 */
public class DummyAdapter extends BaseRecyclerViewAdapter {
    private static final int VIEW_TYPE = 1;

    public DummyAdapter(Context context, ArrayList<?> dataModels, Object headerObject, Object footerObject) {
        super(context, (ArrayList<Object>) dataModels, headerObject, footerObject);
    }

    @Override
    public int contentItemsViewType(int position) {
        UserEntity u = (UserEntity) getContentByPosition(position);
        return VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder createItemsViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_item_users, parent, false);
        return new VHContent(v, this);
    }

    @Override
    public BaseViewHolder createHeaderViewHolder(ViewGroup parent, int viewType) {
        return header;
    }

    @Override
    public BaseViewHolder createFooterViewHolder(ViewGroup parent, int viewType) {
        footer = new GeneralFooterVH(LayoutInflater.from(context).inflate(R.layout.vh_general_footer, parent, false), this);
        return footer;
    }
}
