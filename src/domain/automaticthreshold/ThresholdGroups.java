package domain.automaticthreshold;

import domain.customimage.Pixel;

import java.util.ArrayList;
import java.util.List;

public class ThresholdGroups {

    private int g1;
    private int g2;
    private List<Pixel> g1Pixels;
    private List<Pixel> g2Pixels;

    public ThresholdGroups(){
        this.g1 = 0;
        this.g2 = 0;
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
