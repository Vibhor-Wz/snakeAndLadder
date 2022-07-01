package Helpers;

import com.badlogic.gdx.utils.Timer;

public class MyTimeCounter {
    float tstep=1000;
    int startTime;
    private Timer.Task task;
    public MyTimeCounter(){

    }
    public MyTimeCounter startTimer(final long totalTime, final MyTimeUpdate listener){
        startTime=0;
        task= Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                startTime+= tstep;
                float v= totalTime-startTime;
                if (v>0) {
                    listener.MyOnTick((long) v);
                }
                else {
                    listener.MyOnFinish();
                    task= null;
                }

            }
        },0,tstep/1000f, (int) (totalTime/tstep));

        return this;
    }

    public interface MyTimeUpdate {
        void MyOnTick(long remainingTime);
        void MyOnFinish();
    }
}
