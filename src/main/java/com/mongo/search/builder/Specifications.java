package com.mongo.search.builder;


import org.springframework.data.mongodb.core.query.Criteria;

public class Specifications {


    public static Criteria includesMatchText(String textToMatch, String path) {
        if (textToMatch == null || textToMatch.equals("")) {
            return null;
        }
        return Criteria.where(path).regex(textToMatch);
    }


    public static Criteria exactMatchText(String textToMatch, String path) {
        if (textToMatch == null || textToMatch.equals("")) {
            return null;
        }
        return Criteria.where(path).is(textToMatch);
    }


    public static Criteria exactMatchWithinDocuments(String textToMatch, String path) {
        if (textToMatch == null || textToMatch.equals("")) {
            return null;
        }
        return Criteria.where(path).in(textToMatch);
    }


}
