package com.kalbenutritionals.simantra.ViewModel;

import com.kalbenutritionals.simantra.Database.Common.ClsImages;

import java.util.List;

public class VmAdapterBasicIssue {
    private String issue;
    private String jawaban;
    private String fixReason;
    private List<ClsImages> txtLinkImageIssue;
    private List<ClsImages> txtLinkImageFixed;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getFixReason() {
        return fixReason;
    }

    public void setFixReason(String fixReason) {
        this.fixReason = fixReason;
    }

    public List<ClsImages> getTxtLinkImageIssue() {
        return txtLinkImageIssue;
    }

    public void setTxtLinkImageIssue(List<ClsImages> txtLinkImageIssue) {
        this.txtLinkImageIssue = txtLinkImageIssue;
    }

    public List<ClsImages> getTxtLinkImageFixed() {
        return txtLinkImageFixed;
    }

    public void setTxtLinkImageFixed(List<ClsImages> txtLinkImageFixed) {
        this.txtLinkImageFixed = txtLinkImageFixed;
    }
}
