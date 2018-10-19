package com.maxrocky.gundam.page;

/**
 * Created by Tom on 2016/5/31 13:18.
 * Describe:The page tools.
 */
public class PageTools {

    private static final int PAGE_SIZE = 10;

    private int index;//当前页数
    private int size;//每页数目
    private Integer total;//总数量
    private Integer maxPage;//总页数

    public PageTools(){
        this(1, 10);
    }

    public PageTools(int index, int size){
        this.index = index;
        this.size = size;
        this.total = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMaxPage() {
        return (total + size - 1) / size;
    }

}