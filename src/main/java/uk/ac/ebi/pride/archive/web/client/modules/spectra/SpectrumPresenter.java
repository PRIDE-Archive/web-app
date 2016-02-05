package uk.ac.ebi.pride.archive.web.client.modules.spectra;


import com.google.web.bindery.event.shared.EventBus;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Peptide;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;
import uk.ac.ebi.pride.archive.web.client.events.updates.SpectrumUpdateEvent;
import uk.ac.ebi.pride.archive.web.client.events.updates.VarianceUpdateEvent;
import uk.ac.ebi.pride.archive.web.client.modules.Presenter;
import uk.ac.ebi.pride.archive.web.client.modules.View;

import java.util.logging.Logger;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 * @author Noemi del Toro <ntoro@ebi.ac.uk>
 */
public class SpectrumPresenter extends Presenter<SpectrumPresenter.ThisView> implements VarianceUpdateEvent.Handler,
                                                                                        SpectrumUpdateEvent.Handler {

    private static Logger logger = Logger.getLogger(SpectrumPresenter.class.getName());

    public interface ThisView extends View {
        void showSpectrum(Spectrum spectrum, Peptide peptide);
        void hideSpectrum();

    }

    private Peptide peptide;

    public SpectrumPresenter(EventBus eventBus, ThisView view) {
        super(eventBus, view);

        // subscribe to events on PSMs selection changes, we need to react presenting the right spectra
        eventBus.addHandler(VarianceUpdateEvent.getType(), this);
        eventBus.addHandler(SpectrumUpdateEvent.getType(), this);
    }



    @Override
    public void onVarianceUpdateEvent(VarianceUpdateEvent event) {
        if (event.getVariances() != null && event.getVariances().size()>0) {
            peptide = event.getVariances().get(0);
            logger.info("(SpectrumPresenter): stored variance sequence=" + peptide.getSequence());
        }
        else {
            peptide = null;
            this.getView().hideSpectrum();
        }
    }


    // TODO - if we find a way to inject Specktackle with JSON objects instead of URLs, we can use this event type
    @Override
    public void onSpectrumUpdateEvent(SpectrumUpdateEvent event) {
        logger.info("(SpectrumPresenter): presenting Spectrum for event type=" + event.getAssociatedType());
        if (event.getSpectrum() != null) {
            logger.info("(SpectrumPresenter): presenting Spectrum for spectrum ID=" + event.getSpectrum().getId());
            this.getView().showSpectrum(event.getSpectrum(), peptide);

        }
    }
}
