package ca.cmpt276.carbonTracker;

/**
 * Created by song on 2017-03-15.
 */

public class Tip {
    private String tipContent;  // the actual content of tip

    public Tip(String content){
        tipContent = content;
    }

    public String getTipContent(){
        return tipContent;
    }

    public void setTipContent(String content){
        tipContent = content;
    }
}
