package napodev.nprogress;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import napodev.framework.bework.corebase.model.view.BaseViewModel;
import napodev.framework.bework.corebase.view.BaseActivity;
import napodev.framework.bework.corebase.worker.adapter.BaseScrollingListener;
import napodev.framework.bework.corebase.worker.adapter.OnAdapterItemsClickListener;
import napodev.framework.bework.corebase.worker.view.BaseActivityControl;
import napodev.nprogress.adapter.DummyAdapter;
import napodev.nprogress.adapter.FooterDataModel;
import napodev.nprogress.adapter.UserEntity;

public class ListActivity extends BaseActivity {
    /**
     * ListActivity Use BEWORK,
     * Easy to manage Adapter
     * Easy to manage Activity
     * <p>
     * More details information visit : https://github.com/opannapo/Bework and https://github.com/opannapo/Bework-Sample
     **/

    private RecyclerView rv;
    private ArrayList<UserEntity> userEntities = new ArrayList<>();
    public LinearLayoutManager sglm;
    public DummyAdapter adapter;
    public FooterDataModel footer;
    private int totalPage = 2;

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }

    @Override
    public BaseActivityControl getWorker() {
        return null;
    }

    @Override
    public void onActivityCreate(Bundle bundle) {
        setContentView(R.layout.activity_list);
        rv = (RecyclerView) findViewById(R.id.rv);

        sglm = new LinearLayoutManager(this);
        rv.setLayoutManager(sglm);
        rv.addOnScrollListener(new BaseScrollingListener(rv, sglm) {
            @Override
            public void onLastVisible(RecyclerView recyclerView, int dx, int dy) {
                // TODO: 6/18/17 handle when last items visible
                if (footer.isHasnNextPage()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setupData(footer.getCurrentPage() + 1);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onTopVisible(RecyclerView recyclerView, int dx, int dy) {
                // TODO: 6/18/17 handle when top items visible
            }
        });


        setupData(1);
    }

    @Override
    protected void onBack() {
        finishWithAnim(ANIM_TYPE.LEFT_TO_RIGHT);
    }

    private void setupData(int page) {
        if (footer == null) {
            footer = new FooterDataModel();
            userEntities.clear();
        }

        for (int i = 0; i < 20; i++) {
            UserEntity u = new UserEntity();
            u.setName("User-" + i);
            u.setBad(new Random().nextInt(100));
            u.setGood(new Random().nextInt(100));
            u.setCool(new Random().nextInt(100));
            userEntities.add(u);
        }

        footer.setCurrentPage(page);
        footer.setHasnNextPage(page < totalPage ? true : false);
        footer.setTotalPage(totalPage);

        if (adapter == null) {
            adapter = new DummyAdapter(this, this.userEntities, null, footer);
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnAdapterItemsClickListener() {
                @Override
                public void onItemsClicked(int viewType, int position, View v) {
                    // TODO: 11/5/17 doing something when items clicked
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }

    }
}
