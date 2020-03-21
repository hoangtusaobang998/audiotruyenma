package com.sanfulou.audiotruyenma.fragment;

import android.app.usage.UsageEvents;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanfulou.audiotruyenma.R;
import com.sanfulou.audiotruyenma.adpter.MultiAdapterRecycler;
import com.sanfulou.audiotruyenma.base.BaseFragment;
import com.sanfulou.audiotruyenma.model.IsLoading;
import com.sanfulou.audiotruyenma.model.StoryAudio;
import com.sanfulou.audiotruyenma.task.TaskData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends BaseFragment {
    private RecyclerView recyclerView;
    private MultiAdapterRecycler multiAdapterRecycler;
    private LinearLayoutManager linearLayoutManager;
    private List<String> listTitle;

    @Override
    protected int initLayout() {
        return R.layout.trangchu;
    }

    @Override
    protected void initView(View view) {
        init();
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(multiAdapterRecycler);

    }

    private void init() {
        listTitle = new ArrayList<>();
        addTitle();
        multiAdapterRecycler = MultiAdapterRecycler.initRecycler();
        multiAdapterRecycler.setTitle(listTitle);
        linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        connectData();
    }

    private void addTitle() {
        listTitle.clear();
        listTitle.add("TRUYỆN MỚI CẬP NHẬT");
        listTitle.add("ĐƯỢC YÊU THÍCH NHẤT");
        listTitle.add("TRUYỆN AUDIO NGẪU NHIÊN");
        listTitle.add("TRUYỆN NGÔN TÌNH");
        listTitle.add("TRUYỆN MA");
        listTitle.add("TRUYỆN AUDIO KIẾM HIỆP");
    }

    private void connectData() {
        TaskData.executeData().setTypeAll()
                .setTaskListen(new TaskData.TaskListen() {
                    @Override
                    public void onStart() {
                        EventBus.getDefault().post(new IsLoading(true));
                    }

                    @Override
                    public void onConnectS(List<StoryAudio> audioList) {
                        EventBus.getDefault().post(new IsLoading(false));
                        multiAdapterRecycler.setStoryAudios(audioList);
                    }

                    @Override
                    public void onError(String message) {
                        Log.e("message", message);
                        EventBus.getDefault().post(new IsLoading(false));

                    }
                }).connect("https://truyenaudio.org/");
    }
}
