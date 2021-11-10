package sameWords;

public class Line {
    private String line;
    private int lineNumber;

    public Line(String line, int numberOfLine) {
        this.line = line;
        this.lineNumber = numberOfLine;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
