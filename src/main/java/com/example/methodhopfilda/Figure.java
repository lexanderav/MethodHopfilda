package com.example.methodhopfilda;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Figure extends Rectangle {

    private int mark = -1;

    public Figure(int x, int y, long size) {
        super.setX(x * size);
        super.setY(y * size);
        super.setWidth(size);
        super.setHeight(size);
        super.setFill(Color.WHITE);
        super.setStroke(Color.BLACK);
    }

    public void paint(int mark) {
        this.mark = mark;
    }

    public int hasMark() {
        return this.mark;
    }

}
