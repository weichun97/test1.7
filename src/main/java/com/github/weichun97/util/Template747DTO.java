package com.github.weichun97.util;

import java.util.Date;
import java.util.List;

/**
 * @author chun
 * @date 2021/9/29 17:48
 */
public class Template747DTO {

    /**
     * 航班号
     */
    private String flightNumber;

    /**
     * 航班日期
     */
    private String flightDate;

    /**
     * 主甲板 ULD 信息
     */
    private List<UldInfoDTO> maindeckUlds;

    /**
     * 副甲板 ULD 信息
     */
    private List<UldInfoDTO> lowerdeckUlds;

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
         * 目的站
        private String dest;

        /**
         * 重量
         */
        private Double weight;

        /**
         *
         */
        private String cntr;

        /**
         *
         */
        private String hgt;

        /**
         * 备注
         */
        private String remarks;

        /**
         *
         */
        private String pos;

        /**
         *
         */
        private String ck;

        public String getUldNo() {
            return uldNo;
        }

        public void setUldNo(String uldNo) {
            this.uldNo = uldNo;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getCntr() {
            return cntr;
        }

        public void setCntr(String cntr) {
            this.cntr = cntr;
        }

        public String getHgt() {
            return hgt;
        }

        public void setHgt(String hgt) {
            this.hgt = hgt;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getCk() {
            return ck;
        }

        public void setCk(String ck) {
            this.ck = ck;
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

    public List<UldInfoDTO> getMaindeckUlds() {
        return maindeckUlds;
    }

    public void setMaindeckUlds(List<UldInfoDTO> maindeckUlds) {
        this.maindeckUlds = maindeckUlds;
    }

    public List<UldInfoDTO> getLowerdeckUlds() {
        return lowerdeckUlds;
    }

    public void setLowerdeckUlds(List<UldInfoDTO> lowerdeckUlds) {
        this.lowerdeckUlds = lowerdeckUlds;
    }

    public List<UldInfoDTO> getBulks() {
        return bulks;
    }

    public void setBulks(List<UldInfoDTO> bulks) {
        this.bulks = bulks;
    }
}
