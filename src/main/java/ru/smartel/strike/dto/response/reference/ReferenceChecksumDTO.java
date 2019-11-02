package ru.smartel.strike.dto.response.reference;

public class ReferenceChecksumDTO {

    private String checkSum;

    public ReferenceChecksumDTO(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }
}
