package ru.smartel.strike.dto.response.claim;

public class ClaimCountForTypeDTO {
    private int claimTypeId;
    private long claimsCount;

    public ClaimCountForTypeDTO(int claimTypeId, long claimsCount) {
        this.claimTypeId = claimTypeId;
        this.claimsCount = claimsCount;
    }

    public int getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(int claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public long getClaimsCount() {
        return claimsCount;
    }

    public void setClaimsCount(long claimsCount) {
        this.claimsCount = claimsCount;
    }
}
