package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Protein;
import uk.ac.ebi.pride.archive.web.client.utils.Console;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 25/10/13
 *         Time: 15:10
 */
public class ProteinRetriever extends DataRetriever {
    public ProteinRetriever(String webServiceRoot) {
        super(webServiceRoot);
    }

    @Override
    public void retrieveData(String id, Integer taxonId) {
        // retrieval of proteins does not depend on a explicit species annotation,
        // therefore we ignore the provided taxonId
        String url = root + "/protein/" + id;
        if (Console.VERBOSE) {
            Console.info("Retrieving protein for id: " + id);
        }
        new DataRequester(id, url, Protein.class, handlers);
    }
}
