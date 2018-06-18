package core.action.characteristic_points;

import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.BasicMatcher;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;

import domain.customimage.CustomImage;
import domain.sift.SiftResult;

public class ApplySiftDetectorAction {

    private static int iterations = 1500;
    private static Double limit = 5.0;
    private static Double percentage = 0.5;
    private static int matchesLimits = 8;

    public SiftResult execute(CustomImage image1, CustomImage image2) {

        //convert CustomImage to MBFImage
        MBFImage queryImage = ImageUtilities.createMBFImage(image1.getBufferedImage(), true);
        MBFImage targetImage = ImageUtilities.createMBFImage(image2.getBufferedImage(), true);

        //calculate SIFT descriptors
        DoGSIFTEngine engine = new DoGSIFTEngine();
        LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(queryImage.flatten());
        LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(targetImage.flatten());

        //apply basic matcher
        LocalFeatureMatcher<Keypoint> matcher = new BasicMatcher<>(80);
        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);

        //filter some matches
        RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(limit, iterations,
                new RANSAC.PercentageInliersStoppingCondition(percentage));
        matcher = new ConsistentLocalFeatureMatcher2d<>(new FastBasicKeypointMatcher<>(matchesLimits), modelFitter);

        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);

        //get consistent matches
        MBFImage consistentMatches = MatchingUtilities.drawMatches(queryImage, targetImage, matcher.getMatches(), RGBColour.BLUE);

        //put all the information in an object that will be used to show the result
        return new SiftResult(queryKeypoints.size(), targetKeypoints.size(), matcher.getMatches().size(), consistentMatches);
    }

}
