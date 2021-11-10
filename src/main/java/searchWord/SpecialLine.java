package searchWord;

public class SpecialLine {

    private int realLine;
    private int wordsInLine;

    public SpecialLine(int realLine, int wordsInLine) {
        this.realLine = realLine;
        this.wordsInLine = wordsInLine;
    }

    public int getRealLine() {
        return realLine;
    }

    public void setRealLine(int realLine) {
        this.realLine = realLine;
    }

    public int getWordsInLine() {
        return wordsInLine;
    }

    public void setWordsInLine(int wordsInLine) {
        this.wordsInLine = wordsInLine;
    }
}
