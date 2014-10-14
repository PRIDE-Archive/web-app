package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.JavaScriptObject;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class SpeckTackleWrapper extends JavaScriptObject {

    protected SpeckTackleWrapper() {
    }

    public static native SpeckTackleWrapper create(String spectrumJson) /*-{

        $wnd.myChart = $wnd.st.chart.ms().xlabel("mz").ylabel("Intensity");

        $wnd.myChart.render("#stgraph");     // render chart to id 'stgraph'

        $wnd.chart_handle = $wnd.st.data          // new handler
            .set()                    // of type set
            .ylimits([0, 1000])       // y-domain limits
            .x("peaks.mz")            // x-accessor
            .y("peaks.intensity");    // y-accessor

        // bind the data handler to the chart
        $wnd.myChart.load($wnd.chart_handle);

        $wnd.chart_handle.add(spectrumJson);

        $wnd.console.info("Spectrum: " + spectrumJson);

//        return chart;
    }-*/;

}
