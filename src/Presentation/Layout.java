package Presentation;

import Application.App;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Layout extends Pane {
    private Line lines[] = new Line[24];
    private Rectangle blackNotes[] = new Rectangle[10];
    private int posOfBN[] = {37, 39, 42, 44, 46, 49, 51, 54, 56, 58};

    public Layout() {
        for (int i = 0; i < 24; i++) {
            double yPosition = App.root.getHeight() - (i * 40);
            lines[i] = new Line(0, yPosition, App.root.getWidth(), yPosition);
            for (int j = 0; j < 10; j++) {
                if (posOfBN[j] == 35 + i) {
                    blackNotes[j] = new Rectangle(0, App.root.getHeight() - (i * 40),110,40);
                    this.getChildren().add(blackNotes[j]);
                }
            }
            lines[i].setStroke(Color.WHITE);
            lines[i].setStrokeWidth(2);
            this.getChildren().add(lines[i]);

        }
    }
}
