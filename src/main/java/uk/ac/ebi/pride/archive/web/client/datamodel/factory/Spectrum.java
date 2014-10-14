package uk.ac.ebi.pride.archive.web.client.datamodel.factory;

import java.util.List;

/**
 * @author Jose A Dianes <jdianes@ebi.ac.uk>
 *
 */
public interface Spectrum {

    String getId();
    double getMzStart();
    double getMzStop();
    List<SpectrumPeak> getPeaks();

}
