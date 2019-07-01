package com.crm.work.model.pojo;

import com.crm.common.SearchCondition;

public class CustomSearchCondition extends SearchCondition {
     private String keyword;

     private int assortment;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getAssortment() {
        return assortment;
    }

    public void setAssortment(int assortment) {
        this.assortment = assortment;
    }
}
