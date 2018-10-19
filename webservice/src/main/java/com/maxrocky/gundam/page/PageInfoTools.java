package com.maxrocky.gundam.page;

/**
 * Created by Tom on 2016/5/31 13:18.
 * Describe:The pageInfo tools.
 */
public class PageInfoTools extends PageTools {

    public PageInfoTools(){
        super();
    }
    public PageInfoTools(int index, int size){
        super(index, size);
    }

    private int count;

    public int getCount() {
        return (this.getIndex() - 1 ) * this.getSize();
    }

}
