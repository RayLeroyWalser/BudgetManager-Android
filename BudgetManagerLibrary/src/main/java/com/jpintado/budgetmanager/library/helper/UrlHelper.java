package com.jpintado.budgetmanager.library.helper;

public class UrlHelper {

    public static final String PROTOCOL_HTTP = "http";
    public static final String PROTOCOL_HTTPS = "https";
    private static String API_VERSION = "/api/1.0";
    private static String host;
    private static String protocol;
    private static String port;
    private static String apiPath;
    private static String ofxPath;

    public UrlHelper(UrlHelperBuilder urlHelperBuilder) {
        this.protocol = urlHelperBuilder.protocol;
        this.host     = urlHelperBuilder.host;
        this.port     = urlHelperBuilder.port;
        this.apiPath  = urlHelperBuilder.apiPath;
        this.ofxPath  = urlHelperBuilder.ofxPath;
    }

    private static String getBaseUrl() {
        return protocol + "://"
                + host
                + (port != null && !port.equals("") ? ":" + port : "");
    }

    public static String loginUrl() {
        return getBaseUrl() + apiPath + "/login";
    }

    public static String registrationUrl() {
        return getBaseUrl() + apiPath + "/register";
    }

    public static class UrlHelperBuilder {
        private String protocol = PROTOCOL_HTTP;
        private final String host;
        private String port = "";
        private String apiPath = "";
        private String ofxPath = "";

        public UrlHelperBuilder(String host) {
            this.host = host;
        }

        public UrlHelperBuilder setPort(String port) {
            this.port = port;
            return this;
        }

        public UrlHelperBuilder setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public UrlHelperBuilder setApiPath(String apiPath) {
            this.apiPath = apiPath;
            return this;
        }

        public UrlHelperBuilder setOfxPath(String ofxPath) {
            this.ofxPath = ofxPath;
            return this;
        }

        public UrlHelper build() {
            return new UrlHelper(this);
        }
    }
}
