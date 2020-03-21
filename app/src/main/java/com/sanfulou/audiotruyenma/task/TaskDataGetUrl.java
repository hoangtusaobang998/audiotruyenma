package com.sanfulou.audiotruyenma.task;

import android.os.AsyncTask;
import android.util.Log;

import com.sanfulou.audiotruyenma.check.UtilsNoNull;
import com.sanfulou.audiotruyenma.model.StoryAudio;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class TaskDataGetUrl extends AsyncTask<String, Void, List<String>> {
    private static final String CSS_QUERY = "a.download-item";
    private static final int TIME_OUT_CONNECT = 15000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final String URL_GOOGLE = "http://www.google.com";
    private static final String HREF = "href";
    private TaskListen taskListen;

    public void setTaskListen(TaskListen taskListen) {
        this.taskListen = taskListen;
    }

    public static TaskDataGetUrl executeData(String urlConnect) {
        TaskDataGetUrl taskData = new TaskDataGetUrl();
        taskData.execute(urlConnect);
        return taskData;
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        List<String> list = new ArrayList<>();
        try {
            Connection.Response response = Jsoup.connect(strings[0])
                    .ignoreContentType(true)
                    .userAgent(USER_AGENT)
                    .referrer(URL_GOOGLE)
                    .timeout(TIME_OUT_CONNECT)
                    .followRedirects(true)
                    .execute();
            if (response == null) {
                return null;
            }
            Document doc = response.parse();
            if (doc == null) {
                return null;
            }
            Elements sub = doc.select(CSS_QUERY);
            if (sub == null) {
                return null;
            }
            if (sub.isEmpty()) {
                return null;
            }
            for (Element ss : sub) {
                list.add(ss.attr(HREF).trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<String> s) {
        super.onPostExecute(s);

        if (s==null) {
            taskListen.onError("Lỗi không có url");
            return;
        }
        taskListen.onConnectGerUrl(s);


    }

    public interface TaskListen {
        void onConnectGerUrl(List<String> url);

        void onError(String message);
    }

}
