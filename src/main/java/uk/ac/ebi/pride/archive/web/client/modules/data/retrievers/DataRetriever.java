package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import uk.ac.ebi.pride.archive.web.client.modules.data.TransactionHandler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 08/01/14
 *         Time: 17:02
 */
public abstract class DataRetriever implements TransactionHandler.DataRetriever {
    final String root;
    Collection<TransactionHandler> handlers = new ArrayList<>();

    DataRetriever(String webServiceRoot) {
        root = webServiceRoot;
    }

    @Override
    public void addHandler(TransactionHandler handler) {
        handlers.add(handler);
    }
}
