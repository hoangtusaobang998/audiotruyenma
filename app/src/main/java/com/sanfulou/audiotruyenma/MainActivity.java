package com.sanfulou.audiotruyenma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.sanfulou.audiotruyenma.model.StoryAudio;
import com.sanfulou.audiotruyenma.task.TaskData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TaskData
                .executeData("https://truyenaudio.org/truyen-ma/")
                .setTaskListen(new TaskData.TaskListen() {
                    @Override
                    public void onConnectS(List<StoryAudio> audioList) {
                        Log.e("List-Size", audioList.size() + "");
                        for (StoryAudio storyAudio : audioList) {
                            Log.e("Audio", storyAudio.getTitle() + "-" + storyAudio.getImg() + "-" + storyAudio.getUrl());
                        }
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
    }
}
