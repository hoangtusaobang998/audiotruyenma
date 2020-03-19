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

public class TaskData extends AsyncTask<String, Void, List<StoryAudio>> {
    private static final String CSS_QUERY = "div.category-product > div.col-md-3.col-xs-6 > div.category-product-item";
    private static final int TIME_OUT_CONNECT = 12000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final String A = "a";
    private static final String HREF = "href";
    private static final String IMG = "img";
    private static final String SRC = "src";
    private static final String TITLE = "title";
    private static final String ALT = "alt";
    private static final String URLIMGSTAR = "https://truyenaudio.org/upload/pro/";
    private static final String URLIMGEND = ".jpg?quality=100&mode=crop&anchor=topleft&width=300&height=450";

    private Connection.Response response;
    private TaskListen taskListen;

    public void setTaskListen(TaskListen taskListen) {
        this.taskListen = taskListen;
    }

    public static TaskData executeData(String urlConnect) {
        TaskData taskData = new TaskData();
        taskData.execute(urlConnect);
        return taskData;
    }

    @Override
    protected List<StoryAudio> doInBackground(String... strings) {
        List<StoryAudio> storyAudios = new ArrayList<>();
        try {
            response = Jsoup.connect(strings[0])
                    .ignoreContentType(true)
                    .userAgent(USER_AGENT)
                    .referrer("http://www.google.com")
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
            for (Element e : UtilsNoNull.isNullorIsEmpty(sub)) {
                String title = e.getElementsByTag(UtilsNoNull.isNullorIsEmpty(A)).first().attr(TITLE);
                String url = e.getElementsByTag(UtilsNoNull.isNullorIsEmpty(A)).first().attr(HREF);
                String img = URLIMGSTAR + stripAccents(e.getElementsByTag(UtilsNoNull.isNullorIsEmpty(A)).first().getElementsByTag(IMG).first().attr(ALT)) + URLIMGEND;
                storyAudios.add(new StoryAudio(url, title, img));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return storyAudios;
    }

    @Override
    protected void onPostExecute(List<StoryAudio> audioList) {
        super.onPostExecute(audioList);
        if (audioList == null) {
            taskListen.onError("Null");
            return;
        }
        taskListen.onConnectS(audioList);


    }

    public interface TaskListen {
        void onConnectS(List<StoryAudio> audioList);

        void onError(String message);
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        String[] ss = s.split("\\s");
        s = "";
        for (String st : ss) {
            if (st.equals(ss[ss.length - 1])) {
                s += st;
                break;
            }
            s += st + "-";
        }
        return s;
    }
}
