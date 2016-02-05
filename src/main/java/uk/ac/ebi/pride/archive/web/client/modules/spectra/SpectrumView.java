package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Peptide;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;
import uk.ac.ebi.pride.archive.web.client.modules.ViewWithUiHandlers;
import uk.ac.ebi.pride.archive.web.client.modules.sequence.SequenceUiHandler;
import uk.ac.ebi.pride.archive.web.client.modules.variances.VariancesPresenter;
import uk.ac.ebi.pride.archive.web.client.utils.factories.ModuleContainerFactory;
import uk.ac.ebi.pride.widgets.client.disclosure.client.ModuleContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 * @author Noemi del Toro <ntoro@ebi.ac.uk>
 */
public class SpectrumView extends ViewWithUiHandlers<SequenceUiHandler> implements SpectrumPresenter.ThisView {

    private static Logger logger = Logger.getLogger(SpectrumView.class.getName());

    private ModuleContainer outerBox;
    private Map<VariancesPresenter, LorikeetWrapper> viewersCache; // TODO

    public SpectrumView() {
        viewersCache = new HashMap<>();
        outerBox = ModuleContainerFactory.getModuleContainer("Peak " + "List");


    }

    @Override
    public void showSpectrum(Spectrum spectrum, Peptide peptide) {
        outerBox.setOpen(true);
        SimplePanel simplePanel = new SimplePanel();
        simplePanel.getElement().setId(HTMLPanel.createUniqueId());
        outerBox.setContent(simplePanel);
        logger.info("Spectrum: " + spectrum.getId());
        LorikeetWrapper.showSpectra(spectrum, peptide.getSequence(), peptide.getModifiedLocations(), simplePanel.getElement().getId());
    }

    @Override
    public void hideSpectrum() {
        outerBox.setOpen(false);
        outerBox.setHeight("0px");
    }

    @Override
    public void bindToContainer(AcceptsOneWidget container) {
        container.setWidget(outerBox);
    }

    @Override
    public void setVisible(boolean visible) {
        asWidget().setVisible(visible);
    }

    @Override
    public Widget asWidget() {
        return outerBox;
    }
}
