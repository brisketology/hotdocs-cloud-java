/* Copyright (c) 2013, HotDocs Limited
   Use, modification and redistribution of this source is subject
   to the New BSD License as set out in LICENSE.TXT. */

package com.hotdocs.cloud;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Encapsulates the parameters for a CreateSession request.
 */
public class CreateSessionRequest extends Request {

    private InterviewFormat interviewFormat = InterviewFormat.JavaScript;
    private OutputFormat outputFormat = OutputFormat.Native;
    private Map<String, String> settings = new HashMap<String, String>();
    private String theme;
    private boolean showDownloadLinks = true;

    // Constructors

    /**
     * Constructor with only the required parameters.
     * 
     * @param packageId
     *            The ID of the package in the HotDocs Cloud Services cache
     * @param packageFile
     *            The local file path of the package
     */
    public CreateSessionRequest(String packageId, String packageFile) {
        super(packageId, packageFile);
    }

    /**
     * Constructor with all parameters.
     * 
     * @param packageId
     *            The ID of the package in the HotDocs Cloud Services cache
     * @param packageFile
     *            The local file path of the package
     * @param billingRef
     *            [Optional] An annotation for this request in the activity log
     * @param answers
     *            [Optional] The initial XML answer set
     * @param interviewFormat
     *            See the InterviewFormat enum for choices
     * @param outputFormat
     *            See the OutputFormat enum for choices
     * @param theme
     *            [Optional] The name of an interview theme
     * @param showDownloadLinks
     *            Indicates whether the download links will be shown
     */
    public CreateSessionRequest(
            String packageId,
            String packageFile,
            String billingRef,
            String answers,
            InterviewFormat interviewFormat,
            OutputFormat outputFormat,
            String theme,
            boolean showDownloadLinks) {
        super(packageId, packageFile, answers, billingRef);
        this.interviewFormat = interviewFormat;
        this.outputFormat = outputFormat;
        this.theme = theme;
        this.showDownloadLinks = showDownloadLinks;
    }

    // Setters

    public void setBillingRef(String billingRef) {
        this.billingRef = billingRef;
    }

    public void setAnswers(String answers) {
        this.contentStreamGetter = new StringInputStreamGetter(answers);
    }

    public void setInterviewFormat(InterviewFormat interviewFormat) {
        this.interviewFormat = interviewFormat;
    }

    public void setOutputFormat(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void setSetting(String name, String value) {
        settings.put(name, value);
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setShowDownloadLinks(boolean showDownloadLinks) {
        this.showDownloadLinks = showDownloadLinks;
    }

    @Override
    String getPathPrefix() {
        return "/embed/newsession";
    }

    @Override
    String getQuery() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("interviewformat=");
        buffer.append(interviewFormat.name());

        buffer.append("&outputformat=");
        buffer.append(outputFormat.name());

        buffer.append("&showdownloadlinks=");
        buffer.append(showDownloadLinks ? "true" : "false");

        if (billingRef != null) {
            buffer.append("&billingref=");
            buffer.append(billingRef);
        }

        if (theme != null) {
            buffer.append("&theme=");
            buffer.append(theme);
        }

        for (Entry<String, String> setting : settings.entrySet()) {
            buffer.append("&");
            buffer.append(setting.getKey());
            buffer.append("=");
            buffer.append(setting.getValue());
        }

        return buffer.toString();
    }

    @Override
    String getMethod() {
        return "POST";
    }

    @Override
    Collection<Object> getHmacParams() {
        return Arrays.asList(packageId, billingRef,
                interviewFormat, outputFormat, settings);
    }
}
