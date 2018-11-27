package com.fhict.sparklesandroid.data.model;

import java.util.List;

public class PassedRelationsList {

    private List<Relation> relation;

    public List<Relation> getMessage() {
        return relation;
    }

    public void setResult(List<Relation> relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Item [result=" + relation + "]";
    }
}