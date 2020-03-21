package com.sanfulou.audiotruyenma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sanfulou.audiotruyenma.fragment.DinhSoan;
import com.sanfulou.audiotruyenma.fragment.KiemHiep;
import com.sanfulou.audiotruyenma.fragment.NguyenNgocNgan;
import com.sanfulou.audiotruyenma.fragment.TrangChu;
import com.sanfulou.audiotruyenma.fragment.TrinhTham;
import com.sanfulou.audiotruyenma.fragment.TruyenMa;
import com.sanfulou.audiotruyenma.fragment.TruyenNgan;
import com.sanfulou.audiotruyenma.model.IsLoading;
import com.sanfulou.audiotruyenma.model.StoryAudio;
import com.sanfulou.audiotruyenma.task.TaskData;
import com.sanfulou.audiotruyenma.task.TaskDataGetUrl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.viewpagertab)
    SmartTabLayout tabLayout;

    @BindView(R.id.rf)
    SwipeRefreshLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
        initAnimatorView();
//        TaskData.executeData().setTypeAll()
//                .setTaskListen(new TaskData.TaskListen() {
//                    @Override
//                    public void onStart() {
//                        loading.setRefreshing(true);
//                    }
//
//                    @Override
//                    public void onConnectS(List<StoryAudio> audioList) {
//                        Log.e("List-Size", audioList.size() + "");
//                        loading.setEnabled(false);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        Log.e("message", message);
//                        loading.setEnabled(false);
//                    }
//                }).connect("https://truyenaudio.org/");

//        TaskDataGetUrl.executeData("https://truyenaudio.org/trinh-tham/ven-man-bi-an-truyen-trinh-tham-toi-pham-viet-nam.html").setTaskListen(new TaskDataGetUrl.TaskListen() {
//            @Override
//            public void onConnectGerUrl(List<String> urls) {
//
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
    }

    private void initViewPager() {
        viewPager.setAdapter(initFragmentAdapterItem());
        tabLayout.setViewPager(viewPager);
    }

    private void initAnimatorView() {
        ViewAnimator.animate(viewPager, tabLayout).bounceIn().duration(1500).start();
    }

    private FragmentPagerItemAdapter initFragmentAdapterItem() {
        return new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Trang chủ", TrangChu.class)
                .add("Truyện ma", TruyenMa.class)
                .add("Truyện ngắn", TruyenNgan.class)
                .add("Trinh thám", TrinhTham.class)
                .add("Kiếm hiệp", KiemHiep.class)
                .add("Đình Soạn", DinhSoan.class)
                .add("Ngọc Ngạn", NguyenNgocNgan.class)
                .create());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(IsLoading isLoading) {
        if (isLoading.isLoading()) {
            loading.setRefreshing(true);
        } else {
            loading.setRefreshing(false);
        }
    }
}
