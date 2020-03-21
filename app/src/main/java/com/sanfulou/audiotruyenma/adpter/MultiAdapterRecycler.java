package com.sanfulou.audiotruyenma.adpter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanfulou.audiotruyenma.R;
import com.sanfulou.audiotruyenma.model.StoryAudio;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiAdapterRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<List<StoryAudio>> lists = new ArrayList<>();
    private static final int TYPE_TITLE = 474;
    private static final int TYPE_RECYCLERVIEW = 224;
    private int viewType = TYPE_TITLE;
    private List<String> title;
    private List<StoryAudio> storyAudios = new ArrayList<>();

    public List<StoryAudio> getStoryAudios() {
        return storyAudios;
    }

    public MultiAdapterRecycler setStoryAudios(List<StoryAudio> storyAudioss) {
        return this;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public static MultiAdapterRecycler initRecycler() {
        return new MultiAdapterRecycler();
    }

    private int getViewType() {
        return viewType;
    }

    public MultiAdapterRecycler setViewTypeTypeTitle() {
        this.viewType = TYPE_TITLE;
        return this;
    }

    public MultiAdapterRecycler setViewTypeTypeRecyclerView() {
        this.viewType = TYPE_RECYCLERVIEW;
        return this;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            return new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false));
        }

        if (viewType == TYPE_RECYCLERVIEW) {
            return new RecycHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e("PO", position + "");
        if (getViewType() == TYPE_TITLE) {

            ((TitleHolder) holder).tvTitle.setText(title.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return lists.isEmpty() ? 0 : lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType();
    }


    public static class TitleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        TitleHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }


    public static class RecycHolder extends RecyclerView.ViewHolder {
        private LinearLayoutManager linearLayoutManager;
        RecyclerView recyclerView;

        RecycHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerview);
            linearLayoutManager = new LinearLayoutManager(itemView.getContext(), RecyclerView.HORIZONTAL, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
