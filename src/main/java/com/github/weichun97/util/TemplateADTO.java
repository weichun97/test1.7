package com.github.weichun97.util;

import java.util.List;

/**
 * @author chun
 * @date 2021/9/29 17:48
 */
public class TemplateADTO {

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 航班日期
     */
    private String flightDate;

    /**
     * 制表人
     */
    private String lister;

    /**
     * 复核人
     */
    private String reviewer;

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
         * 净重（集装袋）
         */
        private Double suttle;

        /**
         * 自重
         */
        private Double selfWeight;

        /**
         * 垫板重
         */
        private Double plateWight;

        /**
         * 理论重量
         */
        private Double theoreticalWeight;

        /**
         * 毛重（复称）
         */
        private Double grossWeight;

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

        public Double getSuttle() {
            return suttle;
        }

        public void setSuttle(Double suttle) {
            this.suttle = suttle;
        }

        public Double getSelfWeight() {
            return selfWeight;
        }

        public void setSelfWeight(Double selfWeight) {
            this.selfWeight = selfWeight;
        }

        public Double getPlateWight() {
            return plateWight;
        }

        public void setPlateWight(Double plateWight) {
            this.plateWight = plateWight;
        }

        public Double getTheoreticalWeight() {
            return theoreticalWeight;
        }

        public void setTheoreticalWeight(Double theoreticalWeight) {
            this.theoreticalWeight = theoreticalWeight;
        }

        public Double getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(Double grossWeight) {
            this.grossWeight = grossWeight;
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

    public String getLister() {
        return lister;
    }

    public void setLister(String lister) {
        this.lister = lister;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public List<UldInfoDTO> getUldInfoDTOS() {
        return uldInfoDTOS;
    }

    public void setUldInfoDTOS(List<UldInfoDTO> uldInfoDTOS) {
        this.uldInfoDTOS = uldInfoDTOS;
    }
}
