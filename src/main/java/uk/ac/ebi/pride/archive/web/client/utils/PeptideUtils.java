package uk.ac.ebi.pride.archive.web.client.utils;

import uk.ac.ebi.pride.archive.web.client.datamodel.PeptideWithVariances;
import uk.ac.ebi.pride.archive.web.client.datamodel.Region;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.PeptideMatch;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.ModifiedLocation;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Peptide;
import uk.ac.ebi.pride.archive.web.client.exceptions.IllegalRegionValueException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 20/11/13
 *         Time: 15:23
 */
public class PeptideUtils {
    /**
     * Filters the peptide matches outside a region
     * @param peptideMatches the peptide matches to filter
     * @param start the start point of the region
     * @param end the end point of the region
     * @return a new list containing only the peptide matches inside the region
     */
    static public List<PeptideMatch> filterPeptideMatchesNotIn(List<? extends PeptideMatch> peptideMatches, int start, int end) {
        List<PeptideMatch> filteredList = new ArrayList<>();

        if(start == end && start == 0) {
            return (List<PeptideMatch>) peptideMatches;
        }
        for(PeptideMatch peptide : peptideMatches) {
            if(inRange(peptide, start, end)) {
                filteredList.add(peptide);
            }
        }

        return filteredList;
    }
    /**
     * Filters the peptide matches outside a region
     * @param peptideMatches the peptide matches to filter
     * @param start the start point of the region
     * @param end the end point of the region
     * @return a new list containing only the peptide matches inside the region
     */
    static public List<PeptideWithVariances> filterPeptideWithVariancesNotIn(List<PeptideWithVariances> peptideMatches, int start, int end) {
        List<PeptideWithVariances> filteredList = new ArrayList<>();

        if(start == end && start == 0) {
            return peptideMatches;
        }
        for(PeptideWithVariances peptide : peptideMatches) {
            if(inRange(peptide, start, end)) {
                filteredList.add(peptide);
            }
        }

        return filteredList;
    }

    public static List<PeptideMatch> filterPeptidesNotInTissues
            (List<PeptideMatch> peptideMatches, List<String> tissues) {
        List<PeptideMatch> filteredList;
        List<String> properTissues = new ArrayList<>();

        for(String tissue : tissues) {
            if(!tissue.equals("")) {
                properTissues.add(tissue);
            }
        }

        if(properTissues.isEmpty()) {
            return peptideMatches;
        }

        filteredList = new ArrayList<>();

        for(PeptideMatch peptide : peptideMatches) {
            for(String tissue : properTissues) {
                if(peptide.getTissues().contains(tissue)) {
                    filteredList.add(peptide);
                    break;
                }
            }
        }

        return filteredList;
    }

    /**
     * @param peptideMatches List of peptide matches that need to be filtered
     *                       according to the modifications
     * @param modifications List of modification that need to filter the
     *                      peptides
     * @return A list of items that where already contained in the original
     * list
     */
    public static List<PeptideMatch> filterPeptideMatchesWithoutAnyModifications
    (List<? extends PeptideMatch> peptideMatches, List<String> modifications) {
        List<PeptideMatch> filteredList;
        List<String> properModifications = new ArrayList<>();

        for(String modification : modifications) {
            if(!modification.equals("")) {
                boolean isALocation = false;
                try {
                    Integer.parseInt(modification);
                    isALocation = true;
                }
                catch(NumberFormatException ignore) {}

                if(!isALocation) {
                    properModifications.add(modification);
                }
            }
        }

        if(properModifications.isEmpty()) {
            return (List<PeptideMatch>) peptideMatches;
        }

        filteredList = new ArrayList<>();

        for(PeptideMatch peptide : peptideMatches) {
            modLoc:
            for(ModifiedLocation modLoc : peptide.getModifiedLocations()) {
                for(String mod : properModifications) {
                    if(modLoc.getModification().equals(mod)) {
                        filteredList.add(peptide);
                        break modLoc;
                    }
                }
            }
        }

        return filteredList;
    }

    static public int firstIndexWithId(List<? extends Peptide> peptides, String id) {
        int index = -1;

        for(int i = 0; i < peptides.size(); i++) {
            if(peptides.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * This function determines whether the peptide match is "inside" a region,
     * which in practise means whether it's region intersects between the region
     * defined by start and end
     * @param peptide The peptide match that needs to be checked
     * @param start the start point of the region
     * @param end the end point of the region
     * @return whether the region defined by peptide and start + end intersect
     */
    public static boolean inRange(PeptideMatch peptide, int start, int end) {
        if(start == end && start == 0) {
            return true;
        }
        if(end < start) {
            return false;
        }

        int pepStart = peptide.getPosition();
        int pepEnd = peptide.getSequence().length() + peptide.getPosition() - 1;

        return end >= pepStart && start <= pepEnd;
    }

    public static List<PeptideMatch> filterPeptideMatches(List<PeptideMatch> peptides,
                                                          int start, int end,
                                                          List<String> tissues,
                                                          List<String> mods) {

        return filterPeptideMatchesWithoutAnyModifications(
                filterPeptidesNotInTissues(
                        filterPeptideMatchesNotIn(
                                peptides,
                                start, end),
                        tissues),
                mods);
    }

    public static boolean isPeptideMatchNotFiltered(PeptideMatch match,
                                                    List<String> regions,
                                                    List<String> mods,
                                                    List<String> tissues) {
        boolean notFiltered;
        List<String> newRegions, newMods, newTissues;

        // Check if the peptide match is inside any region,
        // any tissue and any modification type. The lack of
        // filters should yield that the peptide is contained.
        notFiltered = regions.isEmpty() && mods.isEmpty() && tissues.isEmpty();

        if(notFiltered) {
            return true;
        }

        // Because the filtering code for peptide lists checks for empty
        // filters we initialize the filter lists accordingly,
        // otherwise we have to use a lot of nested ifs to check for all
        // possible cases where some filter lists are empty.

        if(regions.isEmpty()) {
            newRegions = new ArrayList<>();
            newRegions.add("");
        }
        else {
            newRegions = regions;
        }

        if(mods.isEmpty()) {
            newMods = new ArrayList<>();
            newMods.add("");
        }
        else {
            newMods = mods;
        }

        if(tissues.isEmpty()) {
            newTissues = new ArrayList<>();
            newTissues.add("");
        }
        else {
            newTissues = tissues;
        }

        for(String regionId : newRegions) {
            for(String tissue : newTissues) {
                for(String mod : newMods) {
                    try {
                        //We want to check permutations,
                        // so we  pack the single tissues and modifications
                        // into lists and use the filterPeptideMatches method
                        Region region;
                        if(regionId.equals("")) {
                            region = Region.emptyRegion();
                        }
                        else {
                            region = Region.tokenize(regionId);
                        }
                        List<PeptideMatch> pList = new ArrayList<>();
                        pList.add(match);
                        List<String> tList = new ArrayList<>();
                        tList.add(tissue);
                        List<String> mList = new ArrayList<>();
                        mList.add(mod);

                        if(!filterPeptideMatches(pList,
                                region.getStart(), region.getEnd(),
                                tList, mList).isEmpty()) {
                            return true;
                        }
                    } catch (IllegalRegionValueException ignored) {
                    }
                }
            }
        }
        return false;
    }

    public static boolean peptideCoversRange(PeptideMatch peptide, int start, int end) {
        if (peptide == null) {
            return false;
        }
        int pepStart = peptide.getPosition();
        int pepEnd = pepStart + peptide.getSequence().length() -1;
        return (start >= pepStart && end <= pepEnd);
    }
    public static List<PeptideMatch> filterPeptideMatchesNotCoveringRange(List<? extends PeptideMatch> peptides, int start, int end) {
        List<PeptideMatch> filteredList = new ArrayList<PeptideMatch>();
        for (PeptideMatch peptide : peptides) {
            if (peptideCoversRange(peptide, start, end)) {
                filteredList.add(peptide);
            }
        }
        return filteredList;
    }
}
