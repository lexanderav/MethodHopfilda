package com.example.methodhopfilda;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private ArrayList<Figure[][]> list = new ArrayList<>();
    private ArrayList<Integer[][]> listWeight = new ArrayList<>();
    private int result = -1;
    private static Repository repository;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    public void save(Figure[][] cells) {
        list.add(cells);
        this.listWeight.add(getArrayWeights(cells));
    }

    public int numberOfImages() {
        return list.size();
    }

    public ArrayList<Figure[][]> getAllListImage() {
        return list;
    }

    public Figure[][] getFigureByIndex(int i) {
        return list.get(i);
    }

    public void clearList() {
        list = new ArrayList<>();
        listWeight = new ArrayList<>();
    }

    public Integer[][] getArrayWeights(Figure[][] cells) {
        int size = cells.length;
        Integer[][] arr = new Integer[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = cells[i][j].hasMark();
            }
        }

        return arr;
    }

    public List<Integer[][]> getListWeight() {
        return listWeight;
    }

    public int getResultImage() {
        return result;
    }

    public void setResultImage(int result) {
        this.result = result;
    }

}
