package TwentyFortyEight;

public class Timer {
    private int timer;
    private long startTime;
    private long endTime;
    private boolean running;
    private boolean stopped;
    public Timer(int timer){
        this.timer=timer;
        this.startTime=System.currentTimeMillis();   
        this.running= true;
        this.stopped=false;
        
    }
    public void start(){
        this.startTime=System.currentTimeMillis();
        this.running=true;
        
    }
    public void stop(){
        this.endTime=System.currentTimeMillis();
        this.running=false;
    }
    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
        this.stopped = false;
    }
    public long getElapsedTimeSeconds() {
        long end = running ? System.currentTimeMillis() : endTime;
        return (end - startTime) / 1_000_000_000;
    }

    // Check if timer is still running
    public boolean isRunning() {
        return running;
    }
    public boolean stopped(){
        return this.stopped();
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
        app.text(String.valueOf(this.getElapsedTimeSeconds()),(260),(10+45));
    }
}
