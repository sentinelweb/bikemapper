package co.uk.sentinelweb.bikemapper.net.response.mapquest;

/**
 * Created by robert on 17/06/16.
 */
public class MapQuestDirectionsResponse {
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(final Info info) {
        this.info = info;
    }

    public static class Info {
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(final int status) {
            this.status = status;
        }
    }
}
