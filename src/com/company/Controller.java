package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    private static final int SQUARE_SIZE = 40;
    private static final int WIDTH_FIELD = SQUARE_SIZE * WIDTH;
    private static final int HEIGHT_FIELD = SQUARE_SIZE * HEIGHT;
    private static final int QUANTITY_BOMBS = 10;
    private static final File DIRECTORY_FILE = new File("C:\\Users\\User\\Desktop\\pictures");


    private View view;
    private Graphics graphics;
    private Cell[][] cells = new Cell[WIDTH][HEIGHT];
    private boolean isGameOver = false;


    public void start() {
        view.create(WIDTH_FIELD, HEIGHT_FIELD);
        fillField();
        placeBombs();
        placeNumbers();
        renderFrame();
    }

    public void setView(View view) {
        this.view = view;
    }

    private void renderFrame() {
        BufferedImage bufferedImage = new BufferedImage(WIDTH_FIELD, HEIGHT_FIELD, BufferedImage.TYPE_INT_RGB);
        graphics = bufferedImage.getGraphics();
        drawField();
        view.setImage(bufferedImage);
    }

    private void placeBombs() {
        for (int i = 0; i < QUANTITY_BOMBS; i++) {
            placeOneBomb();
        }
    }


    private void placeOneBomb() {
        Cell cell;
        do {
            cell = cells[random(WIDTH)][random(HEIGHT)];
        } while (cell.isBomb());
        cell.placeBomb();
    }


    private int random(int max) {
        return (int) (Math.random() * max);
    }

    private void fillField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    private void placeNumbers() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {

                int quantityBombsNearCell = getQuantityBombsNearCell(x, y);
                cells[x][y].setNumber(quantityBombsNearCell);
            }
        }
    }

    private boolean isCell(int x, int y) {
        return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;
    }

    private int getQuantityBombsNearCell(int centerX, int centerY) {
        int counter = 0;
        for (int x = centerX - 1; x <= centerX + 1; x++) {
            for (int y = centerY - 1; y <= centerY + 1; y++) {
                if (isCell(x, y) && cells[x][y].isBomb()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(DIRECTORY_FILE, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void draw(BufferedImage image, int x, int y) {
        graphics.drawImage(image, x * SQUARE_SIZE, y * SQUARE_SIZE, null);
    }

    private void drawField() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                draw(loadImage(cells[x][y].getFileName()), x, y);
            }
        }
    }

    public void handleMousePress(int mouseX, int mouseY, int buttonCode) {
        if (isGameOver) {
            return;
        }
        int x = mouseX / SQUARE_SIZE;
        int y = mouseY / SQUARE_SIZE;

        if (buttonCode == 1) {
            if (cells[x][y].isFlag()) {
                return;
            }
            open(x, y);
            if (isWin()) {
                isGameOver = true;
                setFlag();
            }

            if (cells[x][y].isBomb()) {
                openBombs();
                isGameOver = true;
            }
        } else if (buttonCode == 3) {
            cells[x][y].toggleFlag();
        }
        renderFrame();
    }

    private void open(int x, int y) {
        if (cells[x][y].isOpen()) {
            return;
        }
        cells[x][y].open();
        if (!cells[x][y].isEmpty()) {
            return;
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (isCell(i, j)) {
                    open(i, j);
                }
            }
        }
    }

    private void openBombs() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (cells[x][y].isBomb()) {
                    cells[x][y].open();
                }
            }
        }
    }

    private boolean isWin() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (!cells[x][y].isOpen() && !cells[x][y].isBomb()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setFlag() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (cells[x][y].isBomb()) {
                    cells[x][y].setFlag();
                }
            }
        }

    }
}

