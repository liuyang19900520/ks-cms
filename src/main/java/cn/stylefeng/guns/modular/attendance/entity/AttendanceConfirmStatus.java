package cn.stylefeng.guns.modular.attendance.entity;

import lombok.Getter;

/**
 * 员工审批的状态
 *
 * @author Helios-Syo
 * @Date 2019年12月31日
 */
@Getter

public enum AttendanceConfirmStatus {


        CONFIRMED("0", "已确认"), UNCONFIRMED("1", "未确认");

        String code;
        String message;

        AttendanceConfirmStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }

/*        public static String getDescription(String value) {
        if (value == null) {
            return "";
        } else {
            for (ManagerStatus ms : ManagerStatus.values()) {
                if (ms.getCode().equals(value)) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }*/

}
