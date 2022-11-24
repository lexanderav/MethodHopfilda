package com.example.methodhopfilda;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Group panel;
    @FXML
    private Button clearField;
    @FXML
    private HBox hbox;
    @FXML
    private Button addAnImage;
    @FXML
    private Button clearAllImage;
    @FXML
    private Button showImage;
    @FXML
    private Button findImage;
    @FXML
    private Button exit;

    private static final int SIZE_FIELD = 5;
    private static int countImage = 0;
    private Repository repository = Repository.getInstance();
    private Figure[][] cells;
    private long sizeRectangle;
    private Logic logic;
    private Service service;
    private String title = "Метод Хопфилда";

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean checkState() {
        service = new Service(cells);
        boolean gap = this.service.hasGap();
        if (!gap) {
            this.showAlert("Все поля заполнены!");
        }

        return gap;
    }

    private Group buildMark(Figure rect, boolean paint) {
        Group group = new Group();
        Color color;
        if (paint) {
            color = Color.GREEN;
        } else {
            color = Color.WHITE;
        }
        rect.setFill(color);
        group.getChildren().add(rect);
        return group;
    }

    private EventHandler<MouseEvent> buildMouseEvent(Group panel) {
        return event -> {
          Figure rect = (Figure) event.getTarget();
          if (this.checkState() && event.getButton() == MouseButton.PRIMARY) {
              rect.paint(1);
              panel.getChildren().add(
                      this.buildMark(rect, true)
              );
          } else if (event.getButton() == MouseButton.SECONDARY) {
              rect.paint(-1);
              panel.getChildren().add(
                      this.buildMark(rect, false)
              );
          }
        };
    }

    private Group buildGrid() {
        for (int i = 0; i != SIZE_FIELD; i++){
            for (int j = 0; j != SIZE_FIELD; j++) {
                this.sizeRectangle = Math.round(hbox.getWidth() / SIZE_FIELD);
                Figure rect = new Figure(i, j, sizeRectangle);
                cells[i][j] = rect;
                this.panel.getChildren().add(rect);
                rect.setOnMouseClicked(this.buildMouseEvent(panel));
            }
        }
        return this.panel;
    }

    private Group showGrid(Figure[][] cells) {
        Group group = new Group();
        for (int i = 0; i != SIZE_FIELD; i++) {
            for (int j = 0; j != SIZE_FIELD; j++) {
                group.getChildren().add(cells[i][j]);
            }
        }
        return group;
    }

    public void initialize() {
        clearField.setOnAction(event -> {
            cells = new Figure[SIZE_FIELD][SIZE_FIELD];
            buildGrid();
        });

        addAnImage.setOnAction(event -> {
            repository.save(cells);
        });

        clearAllImage.setOnAction(event -> {
            repository.clearList();
            buildGrid();
        });

        showImage.setOnAction(event -> {
            if (!repository.getAllListImage().isEmpty()) {
                if (countImage < repository.numberOfImages()) {
                    panel.getChildren().add(
                            showGrid(repository.getFigureByIndex(countImage))
                    );
                } else {
                    countImage = 0;
                    panel.getChildren().add(
                            showGrid(repository.getFigureByIndex(countImage))
                    );
                }
                countImage++;
            } else {
                buildGrid();
            }
        });

        findImage.setOnAction(event -> {
            logic = new Logic(cells, SIZE_FIELD);
            int resultImage = repository.getResultImage();
            if (resultImage >= 0) {
                panel.getChildren().add(
                        showGrid(repository.getFigureByIndex(resultImage))
                );
            } else {
                this.showAlert("Образ не найден");
            }

        });

        exit.setOnAction(event -> {
            System.exit(0);
        });

    }

}