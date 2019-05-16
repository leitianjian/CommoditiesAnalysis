package com.group.commditiesAnalysis.model;

public class Word {
    private String word;
    private String type;
//    private int occurCount;

    public Word (String word, String type) {
        this.word = word;
        this.type = type;
//        this.occurCount = occurCount;
    }

//    public int getOccurCount() {
//        return occurCount;
//    }

    public String getType() {
        return type;
    }

    public String getWord() {
        return word;
    }

//    public void incrementOccurCount() {
//        ++ this.occurCount;
////        new Object().
//    }

    @Override
    public boolean equals (Object anoWord) {
        if (anoWord instanceof Word) {
            return this.word.compareTo(((Word) anoWord).word) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.word.hashCode();
    }

    @Override
    public String toString() {
        return word + " " + type;
    }
}
