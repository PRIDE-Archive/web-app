package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;

import java.util.logging.Logger;

/**
 * @author Jose A. Dianes
 *
 */
public class SpectrumRetriever extends DataRetriever {

    private static Logger logger = Logger.getLogger(SpectrumRetriever.class.getName());

    public SpectrumRetriever(String webServiceRoot) {
        super(webServiceRoot);
    }

    @Override
    public void retrieveData(String id, Integer taxonId) {
        String url = root + "/spectrum/" + id;
        logger.info("Retrieving spectra for id: " + id);

        new DataRequester(id, url, Spectrum.class, handlers);
    }

}
