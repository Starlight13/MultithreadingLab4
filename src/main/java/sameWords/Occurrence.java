package sameWords;

public class Occurrence {

    private int line;
    private String fileName;

    public Occurrence(int line, String fileName) {
        this.line = line;
        this.fileName = fileName;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Occurrence: on line " + line +
                " in file " + fileName;
    }
}
