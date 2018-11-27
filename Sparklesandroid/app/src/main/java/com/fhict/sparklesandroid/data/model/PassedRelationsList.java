package com.fhict.sparklesandroid.data.model;

import java.util.List;

public class PassedRelationsList {

    private List<PassedRelation> relation;

    public List<PassedRelation> getMessage() {
        return relation;
    }

    public void setResult(List<PassedRelation> relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Item [result=" + relation + "]";
    }
}