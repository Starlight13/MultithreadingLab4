package searchWord;

public class WordOccurrence {

    private int line;
    private String fileName;
    private double percentage;

    public WordOccurrence() {}

    public WordOccurrence(int line, String fileName, double percentage) {
        this.line = line;
        this.fileName = fileName;
        this.percentage = percentage;
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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void addPercentage(double percentage) {
        this.percentage += percentage;
    }

    @Override
    public String toString() {
        return "Occurrence on line " + line +
                " in file " + fileName +
                ". Percentage=" + percentage;
    }
}
