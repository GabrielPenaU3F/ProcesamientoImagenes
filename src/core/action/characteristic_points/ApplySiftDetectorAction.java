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
import org.openimaj.math.geometry.transforms.HomographyRefinement;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.geometry.transforms.estimation.RobustHomographyEstimator;
import org.openimaj.math.model.fit.RANSAC;

import domain.customimage.CustomImage;
import domain.sift.SiftResult;

public class ApplySiftDetectorAction {

    private static int iterations = 2000;
    private static Double threshold = 5.0;
    private static Double percentage = 0.5;

    public SiftResult execute(CustomImage query, CustomImage target) {

        //convert CustomImage to MBFImage
        boolean alpha = true;
        MBFImage queryImage = ImageUtilities.createMBFImage(query.getBufferedImage(), alpha);
        MBFImage targetImage = ImageUtilities.createMBFImage(target.getBufferedImage(), alpha);

        //calculate SIFT descriptors
        DoGSIFTEngine engine = new DoGSIFTEngine();
        LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(queryImage.flatten());
        LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(targetImage.flatten());

        /*
          Basic keypoint matcher. Matches keypoints by finding closest Two keypoints to
          target and checking whether the distance between the two matches is
          sufficiently large.
          This is the method for determining matches suggested by Lowe in the original
          SIFT papers.
         */

        //apply basic matcher
//        LocalFeatureMatcher<Keypoint> matcher = new BasicMatcher<>(80);
//        matcher.setModelFeatures(queryKeypoints);
//        matcher.findMatches(targetKeypoints);

        FastBasicKeypointMatcher<Keypoint> keypointMatcher = new FastBasicKeypointMatcher<>();
        RANSAC.PercentageInliersStoppingCondition stoppingCondition = new RANSAC.PercentageInliersStoppingCondition(percentage);

        RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(threshold, iterations, stoppingCondition);
        LocalFeatureMatcher<Keypoint> matcher = new ConsistentLocalFeatureMatcher2d<>(keypointMatcher, modelFitter);

        matcher.setModelFeatures(queryKeypoints);
        matcher.findMatches(targetKeypoints);

        //get consistent matches
        MBFImage consistentMatches = MatchingUtilities.drawMatches(queryImage, targetImage, matcher.getMatches(), RGBColour.BLUE);

        //put all the information in an object that will be used to show the result
        return new SiftResult(queryKeypoints.size(), targetKeypoints.size(), matcher.getMatches().size(), consistentMatches);
    }

}
