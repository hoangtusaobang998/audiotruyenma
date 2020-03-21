package com.sanfulou.audiotruyenma.model;

public class StoryAudio {

    private String url;
    private String title;
    private String img;
    private Pname pname;
    private AudioInfo audioInfo;

    public StoryAudio(String url, String title, String img, Pname pname, AudioInfo audioInfo) {
        this.url = url;
        this.title = title;
        this.img = img;
        this.pname = pname;
        this.audioInfo = audioInfo;
    }

    public Pname getPname() {
        return pname;
    }

    public void setPname(Pname pname) {
        this.pname = pname;
    }

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
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

    public static class Pname {
        private String title;
        private String tacgia;

        public  Pname(String title, String tacgia) {
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

    public static class AudioInfo {
        private String time;
        private String phan;
        private String luotnghe;

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
