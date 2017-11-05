package napodev.nprogress.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import napodev.framework.bework.corebase.worker.adapter.BaseRecyclerViewAdapter;
import napodev.framework.bework.corebase.worker.adapter.BaseViewHolder;
import napodev.framework.bework.utils.napoinject.Child;
import napodev.framework.bework.utils.napoinject.ViewInjector;
import napodev.nprogress.R;
import napodev.nprogresslib.NProgressLib;

/**
 * Created by opannapo on 11/17/16.
 */
public class VHContent extends BaseViewHolder {
    @Child(R.id.tLabel) public TextView tLabel;
    @Child(R.id.laySub) public LinearLayout laySub;
    @Child(R.id.pg1) public NProgressLib pg1;
    @Child(R.id.pg2) public NProgressLib pg2;
    @Child(R.id.pg3) public NProgressLib pg3;

    public VHContent(View itemView, BaseRecyclerViewAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void onInit(View itemView) {
        ViewInjector.inject(this, (ViewGroup) itemView);
    }

    @Override
    public void onViewBinding(Object objectEntities, final int position) {
        final UserEntity u = (UserEntity) objectEntities;
        tLabel.setText(u.getName());
        pg1.setProgress(u.getGood());
        pg2.setProgress(u.getCool());
        pg3.setProgress(u.getBad());
        laySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().getOnItemClickListener().onItemsClicked(getItemViewType(), position, laySub);
            }
        });
    }
}


