package ro.pub.cs.systems.eim.practicaltest02;

/**
 * Created by student on 5/20/16.
 */
public class ClockString {

    private String info;


    public ClockString() {
        this.info = null;

    }

    public ClockString(
            String info) {
        this.info =info;
    }


    public void setInfo(String info) {
        this.info= info;
    }

    public String getInfo() {
        return this.info;
    }

    @Override
    public String toString() {

        return info + "\n";
    }

}
