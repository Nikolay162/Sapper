package com.company;

public class Cell {
    private boolean isOpen = false;
    private boolean isBomb = false;
    private boolean isFlag = false;
    private int number = 0;

    public void placeBomb() {
        isBomb = true;
    }

    public boolean isEmpty() {
        return number == 0;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public boolean isOpen() {
        return isOpen;
    }


    public void toggleFlag() {
        isFlag = !isFlag;
    }

    public void open() {
        isOpen = true;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFileName() {
        if (isOpen) {
            if (isBomb) {
                return "bomb.png";
            }
            return "open" + number + ".png";
        }
        if (isFlag) {
            return "flag.png";
        }

        return "closed.png";
    }

    public void setFlag() {
        isFlag = true;
    }
}
