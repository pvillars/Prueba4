package cl.anpetrus.prueba4.models;

import java.util.ArrayList;
import java.util.List;

public class WrapperData<T> {
    private int total;
    private int offset;
    private int limit;
    private int count;
    private List<T> results = new ArrayList<>();

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

    public List<T> getResults() {
        return this.results;
    }

}
