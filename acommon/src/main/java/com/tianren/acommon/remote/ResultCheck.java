package com.tianren.acommon.remote;

/**
 * @author Mr.Qiu
 * @date 2018/4/24
 */
public class ResultCheck {
    public static void checkResult(boolean expression, String msg) {
        if (!expression) {
            throw new ResultException(msg);
        }
    }

    public static class ResultException extends RuntimeException {
        private String msg;

        public ResultException(String msg) {
            super();
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
