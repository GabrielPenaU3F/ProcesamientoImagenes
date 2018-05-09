package domain.automaticthreshold;

import domain.customimage.Pixel;

import java.util.ArrayList;
import java.util.List;

public class GlobalThresholdGroups {

    private List<Pixel> g1Pixels;
    private List<Pixel> g2Pixels;

    public GlobalThresholdGroups(){
        this.g1Pixels = new ArrayList<Pixel>();
        this.g2Pixels = new ArrayList<Pixel>();
    }

    public int getG1(){
        return this.g1Pixels.size();
    }

    public int getG2(){
        return this.g2Pixels.size();
    }

    public void addG1Pixel(Pixel pixel){
        this.g1Pixels.add(pixel);
    }

    public void addG2Pixel(Pixel pixel){
        this.g2Pixels.add(pixel);
    }

    public List<Pixel> getG1Pixels(){
        return this.g1Pixels;
    }

    public List<Pixel> getG2Pixels(){
        return this.g2Pixels;
    }
}
