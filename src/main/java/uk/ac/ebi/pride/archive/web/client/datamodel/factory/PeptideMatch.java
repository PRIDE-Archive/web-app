package uk.ac.ebi.pride.archive.web.client.datamodel.factory;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 12/11/13
 *         Time: 16:52
 */
public interface PeptideMatch extends Peptide {
    public Integer getPosition();
    public Integer getUniqueness();
}
