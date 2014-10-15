package uk.ac.ebi.pride.archive.web.client.events.updates;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Peptide;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;

import java.util.List;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 * NOTE: the SpectrumUpdateEvent will not be used until we can do something with the retrieved Spectra
 * right now we don't know how to use the Specktackle JavaScript component injecting objects
 * we just use it passing the JSON file, and for that we just need the variance ID
 */
public class SpectrumUpdateEvent extends GwtEvent<SpectrumUpdateEvent.Handler> {

    public interface Handler extends EventHandler {
        public void onSpectrumUpdateEvent(SpectrumUpdateEvent event);
    }

    private static final Type<Handler> TYPE = new Type<>();

    private final Spectrum spectrum;

    private SpectrumUpdateEvent(Spectrum spectrum, HasHandlers source) {
        super();
        this.spectrum = spectrum;
        setSource(source);
    }

    public static void fire(HasHandlers source, Spectrum spectrum) {
        SpectrumUpdateEvent eventInstance = new SpectrumUpdateEvent(spectrum, source);
        source.fireEvent(eventInstance);
    }

    public Spectrum getSpectrum() {
        return spectrum;
    }

    public static Type<Handler> getType() {
        return TYPE;
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onSpectrumUpdateEvent(this);
    }


}
