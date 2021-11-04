package com.example.CMS.model;

public class JobAdvertisement {

    private String scope;
    private String forwarder;
    private String content;

    public JobAdvertisement(String scope, String forwarder, String content) {
        this.scope = scope;
        this.forwarder = forwarder;
        this.content = content;
    }

    private JobAdvertisement() {
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getForwarder() {
        return forwarder;
    }

    public void setForwarder(String forwarder) {
        this.forwarder = forwarder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
