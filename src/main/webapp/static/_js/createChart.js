
    var chart = st.chart          // new chart
        .ms()                 // of type MS
        .xlabel("m/z")        // x-axis label
        .ylabel("Abundance"); // y-axis label
    chart.render("#stgraph");     // render chart to id 'stgraph'

    var handle = st.data          // new handler
        .set()                    // of type set
        .ylimits([0, 1000])       // y-domain limits
        .x("peaks.mz")            // x-accessor
        .y("peaks.intensity");    // y-accessor

    // bind the data handler to the chart
    chart.load(handle);
    // load the spectrum and annotations for Uridine
    var chartData = {
        "spectrumId":"MS",
        "mzStart":80.8,
        "mzStop":263.2,
        "peaks":[
            {
                "mz":80.8,
                "intensity":17.0
            },
            {
                "mz":85.2,
                "intensity":3.0
            },
            {
                "mz":92.1,
                "intensity":454.0
            }
        ]
    };
    handle.add(chartData);
