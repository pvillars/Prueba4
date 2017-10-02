package cl.anpetrus.prueba4.models;

public class WrapperData {
    private int total;
    private int offset;
    private int limit;
    private int count;
    private WrapperDataResults[] results;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WrapperDataResults[] getResults() {
        return this.results;
    }

    public void setResults(WrapperDataResults[] results) {
        this.results = results;
    }
}
