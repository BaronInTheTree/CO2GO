package ca.cmpt276.carbonTracker.Internal_Logic;

/**
 * The Tip class generates one of several tips for the user so that they will be able to
 * learn new ways of reducing their CO2 output and other things.
 *
 * Song Xiao - Team Teal
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
