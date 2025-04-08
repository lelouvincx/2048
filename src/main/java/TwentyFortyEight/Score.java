package TwentyFortyEight;

public class Score {
    private int score;
    public  static final int SCORE_WIDTH=100;
    public static final int SCORE_HEIGHT=50;
    public Score(int score){
        this.score=score;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score=score;
    }
    public void addScore(int score){
        this.score+=score;
    }
    public void draw(App app){
        app.strokeWeight(0);
        app.fill(189, 172, 151);
        app.rect(200,10,100,50);
        app.fill(0,0,0);
        app.text(String.valueOf(this.score), (200+40), (10+45));
        
    }

}
