package com.example.methodhopfilda;

public class Service {
    private final Figure[][] table;

    public Service(Figure[][] table) {
        this.table = table;
    }

    public boolean hasGap() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j].hasMark() != 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
