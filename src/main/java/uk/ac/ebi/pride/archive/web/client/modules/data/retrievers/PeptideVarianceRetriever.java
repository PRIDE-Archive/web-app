package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import uk.ac.ebi.pride.archive.web.client.datamodel.factory.PeptideList;
import uk.ac.ebi.pride.archive.web.client.utils.Console;

/**
 * This class retrieves the variances of a specified peptide sequence
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 29/10/13
 *         Time: 16:17
 */

public class PeptideVarianceRetriever extends DataRetriever {
    public PeptideVarianceRetriever(String webServiceRoot) {
        super(webServiceRoot);
    }

    @Override
    public void retrieveData(String id, Integer taxonId) {
        String url = root + "/peptide/" + id;
        if (taxonId != null) {
            url += "?species=" + taxonId;
        }
        if (Console.VERBOSE) {
            Console.info("Retrieving peptides for id: " + id);
        }
        new DataRequester(id, url, PeptideList.class, handlers);
    }

}
