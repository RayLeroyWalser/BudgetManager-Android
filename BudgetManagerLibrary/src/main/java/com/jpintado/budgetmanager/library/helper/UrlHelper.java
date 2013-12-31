package com.jpintado.budgetmanager.library.helper;

public class UrlHelper {

    public static final String PROTOCOL_HTTP = "http";
    public static final String PROTOCOL_HTTPS = "https";
    private String host;
    private String protocol;
    private String port;
    private String apiPath;
    private String ofxPath;

    public UrlHelper(UrlHelperBuilder urlHelperBuilder) {
        protocol = urlHelperBuilder.protocol;
        host     = urlHelperBuilder.host;
        port     = urlHelperBuilder.port;
        apiPath  = urlHelperBuilder.apiPath;
        ofxPath  = urlHelperBuilder.ofxPath;
    }

    private String getBaseUrl() {
        return protocol + "://"
                + host
                + (port != null && !port.equals("") ? ":" + port : "");
    }

    public String getChallengeUrl(String email) {
        return getBaseUrl() + apiPath + "/challenge?username=" + email;
    }

    public String getLoginUrl() {
        return getBaseUrl() + apiPath + "/challenge";
    }

    public String getRegistrationUrl() {
        return getBaseUrl() + apiPath + "/register";
    }

    public String getAccountListUrl() {
        return getBaseUrl() + ofxPath + "/account/list";
    }

    public String getInstitutionSearchUrl() {
        return getBaseUrl() + ofxPath + "/institution/search";
    }

    public String getInstitutionInfoUrl() {
        return getBaseUrl() + ofxPath + "/institution/info";
    }

    public String getInstitutionCredentialsUrl() {
        return getBaseUrl() + ofxPath + "/institution/credentials";
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
