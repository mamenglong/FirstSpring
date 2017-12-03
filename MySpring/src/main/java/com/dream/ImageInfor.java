package com.dream;

public class ImageInfor {
    private String url;
    private String startdate;
    private String copyright;
    public ImageInfor(){

    }
    public ImageInfor(String url, String startdate, String copyright) {
        this.url = url;
        this.startdate = startdate;
        this.copyright = copyright;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
