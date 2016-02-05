package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.ModifiedLocation;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.SpectrumPeak;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 * @author Noemi del Toro <ntoro@ebi.ac.uk>
 *     Wrapper to acomodate the Lorikeet jquery plugin to GWT.
 */
public class LorikeetWrapper extends JavaScriptObject {

    private static Logger logger = Logger.getLogger(LorikeetWrapper.class.getName());


    protected LorikeetWrapper() {

    }

    public static native LorikeetWrapper showSpectra(Spectrum spectrum,
                                                     String sequence,
                                                     List<ModifiedLocation> mods,
                                                     String holder) /*-{

        var transformPeaks = @uk.ac.ebi.pride.archive.web.client.modules.spectra.LorikeetWrapper::toJsArrayNumber(*)(spectrum.@uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum::getPeaks()());
        var transformMods = @uk.ac.ebi.pride.archive.web.client.modules.spectra.LorikeetWrapper::toJsLorikeetMods(*)(sequence, mods);

        var options = {
            "sequence": sequence,
            "staticMods": [],
            "variableMods": transformMods,
            "ntermMod": 0, // additional mass to be added to the n-term
            "ctermMod": 0, // additional mass to be added to the c-term
            "peaks": transformPeaks,
            "massError": 0.5,
            "charge": spectrum.@uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum::getPrecursorCharge()(),
            "precursorMz": spectrum.@uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum::getPrecursorMz()(),
            "precursorIntensity": spectrum.@uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum::getPrecursorIntensity()(),
            "zoomMs1": false,
            "width": 800, 	  // width of the ms/ms plot
            "height": 600, 	  // height of the ms/ms plot
            "showIonTable": true,
            "showViewingOptions": true,
            "showOptionsTable": true,
            "showSequenceInfo": true,
            "labelImmoniumIons": true,
            "labelPrecursorPeak": true,
            "labelReporters": true,
            "showMassErrorPlot": true,
            "massErrorPlotDefaultUnit": 'Da',
            "minDisplayMz": spectrum.@uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum::getMzStart()(),
            "maxDisplayMz": spectrum.@uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum::getMzStop()()
        };

        $wnd.$("#" + holder).specview(options)
    }-*/;

    public static JsArray toJsArrayNumber(List<SpectrumPeak> peaks) {
        JsArray<JsArrayNumber> outer = JsArray.createArray().cast();

        for (SpectrumPeak peak : peaks) {
            JsArrayNumber inner = JsArrayNumber.createArray().cast();
            inner.push(peak.getMz());
            inner.push(peak.getIntensity());

            outer.push(inner);
        }

        return outer;
    }

    public static JsArray<LorikeetModsWrapper> toJsLorikeetMods(String sequence, List<ModifiedLocation> modifiedLocations) {
        JsArray<LorikeetModsWrapper> outer = JsArray.createArray().cast();

        for (ModifiedLocation modifiedLocation : modifiedLocations) {
            LorikeetModsWrapper modsWrapper = LorikeetModsWrapper.create(modifiedLocation.getPosition(), modifiedLocation.getMass(), sequence.charAt(modifiedLocation.getPosition()));
            outer.push(modsWrapper);
        }

        logger.info("Mods:" + outer.toString());

        return outer;
    }


}
