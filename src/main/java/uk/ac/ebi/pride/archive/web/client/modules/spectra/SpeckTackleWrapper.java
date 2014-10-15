package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.JavaScriptObject;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 * Note: the container SpectrumView receives the whole JSON objectified to be shown. However, so far we haven found a way for the
 * SpeckTackle JS component to be provided with objects to display. Right now it just uses the JSON file as a resource
 * and therefore a URL is passed to the object.
 */
public class SpeckTackleWrapper extends JavaScriptObject {

    protected SpeckTackleWrapper() {

    }

    public static native SpeckTackleWrapper showSpectra(String spectrumJson, String holder) /*-{

        $wnd.myChart = $wnd.st.chart.ms().xlabel("mz").ylabel("Intensity");

        $wnd.myChart.render("#"+holder);     // render chart to id 'stgraph'

        $wnd.chart_handle = $wnd.st.data          // new handler
            .set()                    // of type set
            .ylimits([0, 1000])       // y-domain limits
            .x("peaks.mz")            // x-accessor
            .y("peaks.intensity");    // y-accessor

        // bind the data handler to the chart
        $wnd.myChart.load($wnd.chart_handle);

        $wnd.chart_handle.add(spectrumJson);

        $wnd.console.info("Spectrum: " + spectrumJson);

    }-*/;

}
