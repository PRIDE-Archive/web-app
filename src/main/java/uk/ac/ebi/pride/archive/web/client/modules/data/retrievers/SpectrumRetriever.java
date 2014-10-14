package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;
import uk.ac.ebi.pride.archive.web.client.utils.Console;

/**
 * @author Jose A. Dianes
 *
 */
public class SpectrumRetriever extends DataRetriever {

    public SpectrumRetriever(String webServiceRoot) {
        super(webServiceRoot);
    }

    @Override
    public void retrieveData(String id, Integer taxonId) {
        String url = root + "/spectrum/" + id;
        if (Console.VERBOSE) {
            Console.info("Retrieving spectra for id: " + id);
        }
        new DataRequester(id, url, Spectrum.class, handlers);
    }

}
