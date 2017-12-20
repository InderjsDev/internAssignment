public class ClockTimer extends Thread implements ClockTimerListener
{
    private NumberDisplay hours;
    private NumberDisplay minutes;
    private NumberDisplay seconds;

    public static final int ONE_SECOND = 1000;
    public  static boolean finished = true;
    private String displayString;    // simulates the actual display


    //Constructor for ClockDisplay objects.

    public ClockTimer()
    {

        hours = new NumberDisplay(48);
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);

        updateDisplay();
    }




    public ClockTimer(int hour, int minute, int second)
    {
        hours = new NumberDisplay(48);
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);

        setTimer(hour, minute, second);
    }




    public void timeTick()
    {
            seconds.increment();
        if(seconds.getValue() == 0) {
            minutes.increment();

            if(minutes.getValue() == 0)  // it just rolled over
                hours.increment();
        }
        updateDisplay();
    }


    public void run(){}



    public void countdown()
    {
        if(hours.getDisplayValue().equals("00") && minutes.getDisplayValue().equals("00") && seconds.getDisplayValue().equals("00")) {
            finished = false;
            System.out.println("time is up!");
        }else {

            seconds.decrement();
        if(seconds.getValue() == 0) {
            minutes.decrement();
        if(minutes.getValue() == 0)
            hours.decrement();

        }
        }
        updateDisplay();
    }



    public void setTimer(int hour, int minute, int second)
    {
        hours.setValue(hour);
        minutes.setValue(minute);
        seconds.setValue(second);

        updateDisplay();
    }


    // Return the current time of this display in the format HH:MM.

    public String getTime()
    {
        return displayString;
    }


    //Update the internal string that represents the display.

    private void updateDisplay()
    {
        displayString = hours.getDisplayValue() + ":" +
                        minutes.getDisplayValue() + ":" +
                        seconds.getDisplayValue();
    }


    public void onSecondsUpdate(ClockTimer updatedTimer){

    };

    public void onTimerEnd(){

    };


    public static void main(String args[]){

        ClockTimer timer = new ClockTimer(00,00,20);
        timer.start();

        while(finished){
            System.out.println("Time Is : " + timer.getTime());
            timer.countdown();
            try {
                Thread.sleep(ONE_SECOND);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }



}

interface ClockTimerListener {

    public void onSecondsUpdate(ClockTimer updatedTimer);

    public void onTimerEnd();
}




class NumberDisplay
{
    private int limit;
    private int value;



    public NumberDisplay(int rollOverLimit)
    {
        limit = rollOverLimit;
        value = 0;
    }


    public int getValue()
    {
        return value;
    }


    public String getDisplayValue()
    {
        if(value < 10) {
            return "0" + value;
        }
        else {
            return "" + value;
        }

    }


    public void setValue(int replacementValue)
    {
        if((replacementValue >= 0) && (replacementValue < limit)) {
            value = replacementValue;

             }
    }


    public void increment()
    {
        value = (value + 1) % limit;
    }


    public void decrement()
    {
        if(value>0)
            value = (value - 1) % limit;

    }
}
