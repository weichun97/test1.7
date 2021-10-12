package com.github.weichun97.util;

import java.util.Date;
import java.util.List;

/**
 * @author chun
 * @date 2021/9/29 17:48
 */
public class Template737DTO {

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 航班日期
     */
    private Date flightDate;

    /**
     * 填表人
     */
    private String preparer;

    /**
     * 复核人
     */
    private String reviewer;

    /**
     * uld 信息
     */
    private List<UldInfoDTO> uldInfoDTOS;

    /**
     * 空板
     */
    private List<UldInfoDTO> bulks;

    /**
     * ULD 信息
     */
    public static class UldInfoDTO {
        /**
         * uld 编号
         */
        private String uldNo;

        /**
         * 净重（舱单）
         */
        private Double suttle;

        /**
         * 集装器自重
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
         * 板型
         */
        private String typeVersion;

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

        public String getTypeVersion() {
            return typeVersion;
        }

        public void setTypeVersion(String typeVersion) {
            this.typeVersion = typeVersion;
        }
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getPreparer() {
        return preparer;
    }

    public void setPreparer(String preparer) {
        this.preparer = preparer;
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

    public List<UldInfoDTO> getBulks() {
        return bulks;
    }

    public void setBulks(List<UldInfoDTO> bulks) {
        this.bulks = bulks;
    }
}
