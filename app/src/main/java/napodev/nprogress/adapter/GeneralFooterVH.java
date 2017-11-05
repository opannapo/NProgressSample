package napodev.nprogress.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import napodev.framework.bework.corebase.view.BaseActivity;
import napodev.framework.bework.corebase.worker.adapter.BaseRecyclerViewAdapter;
import napodev.framework.bework.corebase.worker.adapter.BaseViewHolder;
import napodev.framework.bework.utils.napoinject.Child;
import napodev.framework.bework.utils.napoinject.FontInjector;
import napodev.framework.bework.utils.napoinject.ViewInjector;
import napodev.nprogress.R;

/**
 * Created by opannapo on 11/17/16.
 */
public class GeneralFooterVH extends BaseViewHolder {
    @Child(R.id.tLabel)
    TextView tLabel;
    @Child(R.id.progressBar)
    ProgressBar progressBar;

    public GeneralFooterVH(View itemView, BaseRecyclerViewAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void onInit(View itemView) {
        ViewInjector.inject(this, (ViewGroup) itemView);
        FontInjector.inject(this, (BaseActivity) getContext(), (ViewGroup) itemView);
    }

    @Override
    public void onViewBinding(Object objectEntities, int position) {
        FooterDataModel footerDataModel = (FooterDataModel) objectEntities;
        if (footerDataModel.isHasnNextPage()) {
            tLabel.setText("Loading");
            tLabel.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            tLabel.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            tLabel.setText("No More");
        }
    }


}
