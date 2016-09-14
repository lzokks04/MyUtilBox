package com.lzokks04.myutilbox.bean;

/**
 * Created by Liu on 2016/9/14.
 */
public class QqNumResultBean {

    /**
     * error_code : 0
     * reason : success
     * result : {"data":{"conclusion":"[中吉]万宝云集，天降幸运，立志奋发，可成大功","analysis":"安稳余庆福禄开，盛大幸福天赐来，内含衰兆应谨慎，注意品行福乐享。名誉良好，信用亦佳，步步高升。"}}
     */

    private int error_code;
    private String reason;
    /**
     * data : {"conclusion":"[中吉]万宝云集，天降幸运，立志奋发，可成大功","analysis":"安稳余庆福禄开，盛大幸福天赐来，内含衰兆应谨慎，注意品行福乐享。名誉良好，信用亦佳，步步高升。"}
     */

    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * conclusion : [中吉]万宝云集，天降幸运，立志奋发，可成大功
         * analysis : 安稳余庆福禄开，盛大幸福天赐来，内含衰兆应谨慎，注意品行福乐享。名誉良好，信用亦佳，步步高升。
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            private String conclusion;
            private String analysis;

            public String getConclusion() {
                return conclusion;
            }

            public void setConclusion(String conclusion) {
                this.conclusion = conclusion;
            }

            public String getAnalysis() {
                return analysis;
            }

            public void setAnalysis(String analysis) {
                this.analysis = analysis;
            }
        }
    }
}
