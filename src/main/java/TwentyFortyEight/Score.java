package TwentyFortyEight;

import static java.lang.Math.max;

public class Score {
    public static final int SCORE_WIDTH = 100;
    public static final int SCORE_HEIGHT = 50;

    private int score;
    private int bestScore;

    public Score(int score) {
        this.score = score;
        this.bestScore = score;
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; this.bestScore = max(this.bestScore, this.score); }
    public void addScore(int score) { this.score += score; this.bestScore = max(this.bestScore, this.score); }

    public void draw(App app) {
        app.strokeWeight(0);

        app.fill(189, 172, 151);
        app.rect(100, 50, SCORE_WIDTH, SCORE_HEIGHT);

        app.fill(0, 0, 0); // black text
        app.text(String.valueOf(this.score), 100+SCORE_WIDTH/2, 50+SCORE_HEIGHT/2);
    }
}
