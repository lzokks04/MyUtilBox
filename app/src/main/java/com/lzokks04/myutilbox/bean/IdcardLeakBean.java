package com.lzokks04.myutilbox.bean;

/**
 * Created by Liu on 2016/9/14.
 */
public class IdcardLeakBean {

    /**
     * resultcode : 200
     * reason : 成功的返回
     * result : {"cardno":"441825199305070013","res":"1","tips":"安全"}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * cardno : 441825199305070013
     * res : 1
     * tips : 安全
     */

    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String cardno;
        private String res;
        private String tips;

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
