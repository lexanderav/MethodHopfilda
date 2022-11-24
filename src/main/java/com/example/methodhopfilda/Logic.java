package com.example.methodhopfilda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logic {
    private Repository repository = Repository.getInstance();
    private Figure[][] cells;
    private int size;
    private ArrayList<Integer[][]> listWeightRepo = new ArrayList<>();
    private List<Integer[][]> list = repository.getListWeight();
    public Logic(Figure[][] cells, int size) {
        this.cells = cells;
        this.size = size;
        List<Integer> arraySearch = getListOfWeightsInFormOfVector(
                repository.getArrayWeights(this.cells));
        Integer [][] W = trainingNeuralNetwork();
        imageSearch(W, arraySearch);
    }

    private Integer[][] sumAllWeightFromListImage() {
        Integer[][] W = new Integer[size*size][size*size];
        for (int i = 0; i < W.length; i++) {
            Arrays.fill(W[i],0);
        }
        for (int i = 0; i < listWeightRepo.size(); i++) {
            Integer [][] arr = listWeightRepo.get(i);
            for (int j = 0; j < arr.length; j++) {
                for (int k = 0; k < arr.length; k++) {
                    W[j][k] += arr[j][k];
                }
            }
        }
        return W;
    }

    public List<Integer> getListOfWeightsInFormOfVector(Integer[][] arr) {
        List<Integer> res = new ArrayList<>();
        for (Integer[] a: arr) {
            res.addAll(Arrays.asList(a));
        }
        return res;
    }

    private Integer[][] multiplixMatrix(Integer[][] figures) {
        Integer[][] weightArr = new Integer[size*size][size*size];
        for (int i = 0; i < weightArr.length; i++) {
            Arrays.fill(weightArr[i], 0);
        }
        List<Integer> listFigure = getListOfWeightsInFormOfVector(figures);
        for (int m = 0; m < size*size; m++) {
            for (int j = 0; j < size*size; j++) {
                weightArr[m][j] = listFigure.get(m) * listFigure.get(j);
            }
        }
        return weightArr;
    }

    private Integer[][] trainingNeuralNetwork() {
        for (int i = 0; i < list.size(); i++) {
            Integer[][] figures = list.get(i);
            listWeightRepo.add(multiplixMatrix(figures));
        }
        Integer[][] sumWeight = sumAllWeightFromListImage();
        return sumWeight;
    }

    private List<Integer> convertToDoubleValue(List<Integer> weight) {
        for (int i = 0; i < weight.size(); i++) {
            if (weight.get(i) >= 0) {
                weight.set(i, 1);
            } else {
                weight.set(i, -1);
            }
        }
        return weight;
    }

    private List<Integer> multiplixMatrixSearch(Integer[][] W, List<Integer> arraySearch) {
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < size*size; i++) {
            res.add(0);
        }

        for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W.length; j++) {
                int temp = res.get(i);
                res.set(i ,temp + W[i][j] * arraySearch.get(j));
            }
        }

        return res;
    }

    private void imageSearch(Integer[][] W , List<Integer> arraySearch) {
        List<Integer> weightSearch = multiplixMatrixSearch(W, arraySearch);
        List<Integer> res = convertToDoubleValue(weightSearch);
        int check = comparisonMatrix(res);
        if (check >= 0) {
            repository.setResultImage(check);
        } else if (check == -1) {
            try {
                imageSearch(W, res);
            } catch (StackOverflowError e) {
                repository.setResultImage(-1);
            }

        }
    }

    private int comparisonMatrix(List<Integer> arr) {
        for (int i = 0; i < list.size(); i++) {
            List<Integer> image = getListOfWeightsInFormOfVector(list.get(i));
            int count = 0;
            for (int j = 0; j < image.size(); j++) {
                if (arr.get(j) == image.get(j)){
                    count++;
                }
            }
            if (count == size*size) {
                return i;
            }
        }
        return -1;
    }
}
