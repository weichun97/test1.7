package com.github.weichun97.util;

import java.util.List;

/**
 * @author chun
 * @date 2021/9/29 17:48
 */
public class TemplateDDTO {

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 航班日期
     */
    private String flightDate;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * uld 信息
     */
    private List<UldInfoDTO> uldInfoDTOS;

    /**
     * ULD 信息
     */
    public static class UldInfoDTO {
        /**
         * uld 编号
         */
        private String uldNo;

        /**
         * 集装器重量
         */
        private Double uldWeight;

        /**
         * 版型
         */
        private String typeVersion;

        /**
         * 皮重
         */
        private Double tareWeight;

        /**
         * 总运单号
         */
        private Double alwaysTheAwb;

        /**
         * 装载件数
         */
        private Integer loadingNumber;

        /**
         * 装载分净重
         */
        private Double netLoading;

        /**
         * 运单总件数
         */
        private Double totalNumberOfAwb;

        /**
         * 备注
         */
        private String memo;

        public String getUldNo() {
            return uldNo;
        }

        public void setUldNo(String uldNo) {
            this.uldNo = uldNo;
        }

        public Double getUldWeight() {
            return uldWeight;
        }

        public void setUldWeight(Double uldWeight) {
            this.uldWeight = uldWeight;
        }

        public String getTypeVersion() {
            return typeVersion;
        }

        public void setTypeVersion(String typeVersion) {
            this.typeVersion = typeVersion;
        }

        public Double getTareWeight() {
            return tareWeight;
        }

        public void setTareWeight(Double tareWeight) {
            this.tareWeight = tareWeight;
        }

        public Double getAlwaysTheAwb() {
            return alwaysTheAwb;
        }

        public void setAlwaysTheAwb(Double alwaysTheAwb) {
            this.alwaysTheAwb = alwaysTheAwb;
        }

        public Integer getLoadingNumber() {
            return loadingNumber;
        }

        public void setLoadingNumber(Integer loadingNumber) {
            this.loadingNumber = loadingNumber;
        }

        public Double getNetLoading() {
            return netLoading;
        }

        public void setNetLoading(Double netLoading) {
            this.netLoading = netLoading;
        }

        public Double getTotalNumberOfAwb() {
            return totalNumberOfAwb;
        }

        public void setTotalNumberOfAwb(Double totalNumberOfAwb) {
            this.totalNumberOfAwb = totalNumberOfAwb;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<UldInfoDTO> getUldInfoDTOS() {
        return uldInfoDTOS;
    }

    public void setUldInfoDTOS(List<UldInfoDTO> uldInfoDTOS) {
        this.uldInfoDTOS = uldInfoDTOS;
    }
}
