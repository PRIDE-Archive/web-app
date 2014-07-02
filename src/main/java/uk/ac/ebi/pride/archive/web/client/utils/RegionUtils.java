package uk.ac.ebi.pride.archive.web.client.utils;

import uk.ac.ebi.pride.archive.web.client.datamodel.Region;
import uk.ac.ebi.pride.archive.web.client.exceptions.IllegalRegionValueException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 04/11/13
 *         Time: 16:32
 */
public class RegionUtils {
    public static List<Region> tokenize(List<String> regionIds) throws
                                                    IllegalRegionValueException,
                                                    NumberFormatException,
                                                    IndexOutOfBoundsException {
        List<Region> regionList = new ArrayList<>();

        for(String regionId : regionIds) {
            regionList.add(Region.tokenize(regionId));
        }
        return regionList;
    }
}
