package cz.cvut.nss.utils.traversal;

import java.util.Comparator;

/**
 * @author jakubchalupa
 * @since 13.05.15
 */
public class TraversalBranchWrapperDepartureComparator implements Comparator<TraversalBranchWrapper> {

    @Override
    public int compare(TraversalBranchWrapper o1, TraversalBranchWrapper o2) {
        if(!o1.isOverMidnight() && o2.isOverMidnight()) {
            return -1;
        }

        if(o1.isOverMidnight() && !o2.isOverMidnight()) {
            return 1;
        }

        if(o1.getNodeTime() < o2.getNodeTime()) {
            return -1;
        }

        if(o1.getNodeTime() > o2.getNodeTime()) {
            return 1;
        }

        if(o1.getTravelTime() < o2.getTravelTime()) {
            return -1;
        }

        if(o1.getTravelTime() > o2.getTravelTime()) {
            return 1;
        }

        return 0;
    }

}
