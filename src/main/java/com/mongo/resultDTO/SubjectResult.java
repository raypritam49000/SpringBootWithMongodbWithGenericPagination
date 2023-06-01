package com.mongo.resultDTO;

import java.util.List;

public class SubjectResult {


    private String subName;

    private long count; //I need List<Student> here

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
