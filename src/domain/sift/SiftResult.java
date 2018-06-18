package domain.sift;

import org.openimaj.image.MBFImage;

public class SiftResult {
    private int queryKeypointsQuantity;
    private int targetKeypointsQuantity;
    private int matchesQuantity;
    private MBFImage consistentMatches;



    public SiftResult(int queryKeypointsQuantity, int targetKeypointsQuantity,
                      int matchesQuantity, MBFImage consistentMatches){
        this.queryKeypointsQuantity = queryKeypointsQuantity;
        this.targetKeypointsQuantity = targetKeypointsQuantity;
        this.matchesQuantity = matchesQuantity;
        this.consistentMatches = consistentMatches;
    }

    public int getQueryKeypointsQuantity() {
        return queryKeypointsQuantity;
    }

    public int getTargetKeypointsQuantity() {
        return targetKeypointsQuantity;
    }

    public int getMatchesQuantity() {
        return matchesQuantity;
    }

    public MBFImage getConsistentMatches() {
        return consistentMatches;
    }
}
