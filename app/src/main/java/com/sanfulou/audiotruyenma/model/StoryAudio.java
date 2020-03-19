package com.sanfulou.audiotruyenma.model;

public class StoryAudio {

    public String url;
    public String title;
    public String img;

    public StoryAudio(String url, String title, String img) {
        this.url = url;
        this.title = title;
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public class Pname {
        public String title;
        public String tacgia;

        public Pname(String title, String tacgia) {
            this.title = title;
            this.tacgia = tacgia;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTacgia() {
            return tacgia;
        }

        public void setTacgia(String tacgia) {
            this.tacgia = tacgia;
        }
    }

    public class AudioInfo {
        public String time;
        public String phan;
        public String luotnghe;

        public AudioInfo(String time, String phan, String luotnghe) {
            this.time = time;
            this.phan = phan;
            this.luotnghe = luotnghe;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPhan() {
            return phan;
        }

        public void setPhan(String phan) {
            this.phan = phan;
        }

        public String getLuotnghe() {
            return luotnghe;
        }

        public void setLuotnghe(String luotnghe) {
            this.luotnghe = luotnghe;
        }
    }

}
