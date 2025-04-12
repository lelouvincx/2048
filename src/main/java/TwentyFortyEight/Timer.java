package TwentyFortyEight;

public class Timer {
    private int timer;
    private long startTime;
    private long endTime;
    private boolean running;
    public Timer(int timer){
        this.timer=timer;
        this.startTime=System.nanoTime();   
        this.running= true;
    }
    public void start(){
        this.startTime=System.nanoTime();
        this.running=true;
    }
    public void stop(){
        this.endTime=System.nanoTime();
        this.running=false;
    }
    public long getElapsedTimeSeconds() {
        long end = running ? System.nanoTime() : endTime;
        return (end - startTime) / 1_000_000_000;
    }

    // Check if timer is still running
    public boolean isRunning() {
        return running;
    }
    public int getTimer(){
        return this.timer;
    }
    public void setTimer(int timer){
        this.timer=timer;
    }
    public void draw(App app){
        app.strokeWeight(0);
        app.fill(189, 172, 15);
        app.rect(260,10,110,50);
        app.fill(255,255,255);
        app.text(String.valueOf(this.getElapsedTimeSeconds()),(260+10),(10+45));

    }
}
