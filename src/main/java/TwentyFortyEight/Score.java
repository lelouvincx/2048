package TwentyFortyEight;

import static java.lang.Math.max;

public class Score {
    public static final int SCORE_WIDTH=100;
    public static final int SCORE_HEIGHT=50;

    private int score;
    private int bestScore;

    public Score(int score){
        this.score = score;
        this.bestScore = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        this.bestScore = max(this.bestScore, score);
    }

    public void addScore(int score) {
        this.score += score;
        this.bestScore = max(this.score,this.bestScore);
    }

    public void draw(App app) {
        app.strokeWeight(0);

        // this score
        app.fill(189, 172, 151);
        app.rect(150,10,100,50);
        app.fill(0,0,0);
        app.text(String.valueOf(this.score), (150+40), (10+45));

        // best score
        app.fill(189, 172, 151);
        app.rect(150,70,100,50);
        app.fill(0,0,0);
        app.text(String.valueOf(this.bestScore),(150+40), (70+45));
    }
}
