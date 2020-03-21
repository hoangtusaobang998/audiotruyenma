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
    private static final String CSS_QUERY_TYPE = "div.category-product > div.col-md-3.col-xs-6 > div.category-product-item";
    private static final String CSS_QUERY_TYPE_ALL = "div.category-product-item";
    private static final int TIME_OUT_CONNECT = 15000;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private static final String A = "a";
    private static final String HREF = "href";
    private static final String IMG = "img";
    private static final String TITLE = "title";
    private static final String ALT = "alt";
    private static final String URL_GOOGLE = "http://www.google.com";
    private static final String URLIMGSTAR = "https://truyenaudio.org/upload/pro/";
    private static final String URLIMGEND = ".jpg?quality=100&mode=crop&anchor=topleft&width=300&height=450";
    private static final String TACGIA = "tacgia";
    private static final String AUDIOINFO = "audio-info";
    private static final String LUOT = "Lượt";
    private static final String IS_EMPTY = "";
    private static final String CHUARO = "Chưa rõ";
    private TaskListen taskListen;
    private static final int TYPE_ALL = 831;
    private static final int TYPE = 204;
    private int type = -1;
    private String CSS;

    public int getType() {
        return type;
    }

    public TaskData setTypeAll() {
        type = TYPE_ALL;
        return this;
    }

    public TaskData setType() {
        type = TYPE;
        return this;
    }

    public TaskData setTaskListen(TaskListen taskListen) {
        this.taskListen = taskListen;
        return this;
    }


    public static TaskData executeData() {
        TaskData taskData = new TaskData();
        return taskData;
    }

    public void connect(String url) {
        execute(url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        taskListen.onStart();
    }

    @Override
    protected List<StoryAudio> doInBackground(String... strings) {
        if (getType() == -1) {
            return null;
        }
        CSS = getType() == TYPE ? CSS_QUERY_TYPE : CSS_QUERY_TYPE_ALL;
        Log.e("CSS", CSS);
        List<StoryAudio> storyAudios = new ArrayList<>();
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
            Elements sub = doc.select(CSS);
            if (sub == null) {
                return null;
            }
            if (sub.isEmpty()) {
                return null;
            }
            for (Element e : UtilsNoNull.isNullorIsEmpty(sub)) {
                String tacgia;
                String title;
                String url;
                String img;
                String audioInfo;
                String time = "";
                String phan = "";
                String luotnghe = "";
                try {
                    title = e.getElementsByTag(UtilsNoNull.isNullorIsEmpty(A)).first().attr(TITLE);
                } catch (Exception ex) {
                    title = CHUARO;
                    ex.printStackTrace();
                }
                try {
                    url = e.getElementsByTag(UtilsNoNull.isNullorIsEmpty(A)).first().attr(HREF);
                } catch (Exception exx) {
                    url = IS_EMPTY;
                    exx.printStackTrace();
                }
                try {
                    img = URLIMGSTAR + stripAccents(e.getElementsByTag(UtilsNoNull.isNullorIsEmpty(A)).first().getElementsByTag(IMG).first().attr(ALT)) + URLIMGEND;
                } catch (Exception eexx) {
                    img = IS_EMPTY;
                    eexx.printStackTrace();
                }
                try {
                    tacgia = e.getElementsByClass(TACGIA).first().getElementsByTag(A).first().text().trim();
                } catch (Exception es) {
                    tacgia = CHUARO;
                    es.printStackTrace();
                }
                try {
                    audioInfo = e.getElementsByClass(AUDIOINFO).first().text().trim();
                } catch (Exception eaudioInfo) {
                    audioInfo = IS_EMPTY;
                    eaudioInfo.printStackTrace();
                }
                if (!audioInfo.equals(IS_EMPTY)) {
                    if (getType() == TYPE) {
                        time = audioInfo.substring(0, 10).trim();
                        phan = audioInfo.substring(10, audioInfo.lastIndexOf(LUOT) - 1).trim();
                        luotnghe = audioInfo.substring(audioInfo.lastIndexOf(LUOT)).trim();
                    }
                    if (getType() == TYPE_ALL) {
                        String[] ss = audioInfo.split("\\s");
                        if (ss.length >= 4) {
                            phan = ss[0] + "\t" + ss[1];
                            time = ss[2];
                            luotnghe = ss[3];
                        }
                    }
                }
                storyAudios.add(new StoryAudio(url, title, img, new StoryAudio.Pname(title, tacgia), new StoryAudio.AudioInfo(time, phan, luotnghe)));
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
            if (getType() == -1) {
                taskListen.onError("Type null ??");
                return;
            }
            taskListen.onError("Null");
            return;
        }
        taskListen.onConnectS(audioList);
    }

    public interface TaskListen {

        void onStart();

        void onConnectS(List<StoryAudio> audioList);

        void onError(String message);
    }

    private static String stripAccents(String s) throws Exception {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        String[] ss = s.split("\\s");
        s = "";
        for (String st : ss) {
            if (st.equals(ss[ss.length - 1])) {
                s += st;
                break;
            }
            s.concat(st + "-");
        }
        return s;
    }
}
