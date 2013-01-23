package com.eldoraludo.ppafadministration.util;

public class EvenOdd {
    private boolean even = true;

    public String getNext() {
        String result = even ? "even" : "odd";
        even = !even;
        return result;
    }

    public boolean isEven() {
        return even;
    }

    public void setEven(boolean value) {
        even = value;
    }
}
