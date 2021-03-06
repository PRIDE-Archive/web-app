package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Group;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 25/10/13
 *         Time: 15:10
 */
public class GroupRetriever extends DataRetriever {
    public GroupRetriever(String webServiceRoot) {
        super(webServiceRoot);
    }

    @Override
    public void retrieveData(String id, Integer taxonId) {
        // retrieval of groups does not depend on the species (yet),
        // therefore we ignore the provided taxonId
        String url = root + "/group/" + id;
        new DataRequester(id, url, Group.class, handlers);
    }
}
