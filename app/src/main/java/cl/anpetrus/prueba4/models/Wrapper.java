package cl.anpetrus.prueba4.models;

public class Wrapper {
    private int code;
    private WrapperData data;
    private String etag;
    private String status;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public WrapperData getData() {
        return this.data;
    }

    public void setData(WrapperData data) {
        this.data = data;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
