package uk.ac.ebi.pride.archive.web.client.modules.spectra;


import com.google.web.bindery.event.shared.EventBus;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Peptide;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;
import uk.ac.ebi.pride.archive.web.client.events.updates.SpectrumUpdateEvent;
import uk.ac.ebi.pride.archive.web.client.events.updates.VarianceUpdateEvent;
import uk.ac.ebi.pride.archive.web.client.modules.Presenter;
import uk.ac.ebi.pride.archive.web.client.modules.View;
import uk.ac.ebi.pride.archive.web.client.utils.Console;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 * Note: this presenter receives the whole JSON objectified to be shown. However, so far we haven found a way for the
 * SpeckTackle JS component to be provided with objects to display. Right now it just uses the JSON file as a resource
 * and therefore a URL is passed to the object. the JSON obbject is passed to the viewer anyway, and the event manage
 * ment and server requests are in place for the future improvement of the webapp.
 *
 */
public class SpectrumPresenter extends Presenter<SpectrumPresenter.ThisView> implements VarianceUpdateEvent.Handler {

    public SpectrumPresenter(EventBus eventBus, ThisView view) {
        super(eventBus, view);

        // subscribe to events on PSMs selection changes, we need to react presenting the right spectra
        eventBus.addHandler(VarianceUpdateEvent.getType(), this);
    }

    @Override
    public void onVarianceUpdateEvent(VarianceUpdateEvent event) {
        Console.info("(SpectrumPresenter): presenting Spectrum for event type=" + event.getAssociatedType());
        if (event.getVariances() != null && event.getVariances().size()>0) {
            String peptideVarianceId = event.getVariances().get(0).getId();
            Console.info("(SpectrumPresenter): presenting Spectrum for variance ID=" + peptideVarianceId);

            this.getView().showSpectrum(event.getVariances().get(0));

        }
    }

    public interface ThisView extends View {
        void showSpectrum(Peptide variance);
    }

    // TODO - if we find a way to inject Specktackle with JSON objects instead of URLs, we can use this event type
//    public void onSpectrumUpdateEvent(SpectrumUpdateEvent event) {
//        Console.info("(SpectrumPresenter): presenting Spectrum for event type=" + event.getAssociatedType());
//        if (event.getSpectrum() != null) {
//            String peptideVarianceId = event.getSpectrum().getId();
//            Console.info("(SpectrumPresenter): presenting Spectrum for variance ID=" + peptideVarianceId);
//
//            this.getView().showSpectrum(event.getSpectrum());
//
//        }
//    }
}
