package bilshort.user.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "business_id")
    private Integer businessId;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "total_rights_used", columnDefinition = "int default 0")
    private int totalRightsUsed;

    @Column(name = "max_rights_available", columnDefinition = "int default 10")
    private int maxRightsAvailable;

    public Integer getBusinessId() {
        return businessId;
    }

    public Business setBusinessId(Integer businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Business setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public Business setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public int getTotalRightsUsed() {
        return totalRightsUsed;
    }

    public Business setTotalRightsUsed(int totalRightsUsed) {
        this.totalRightsUsed = totalRightsUsed;
        return this;
    }

    public int getMaxRightsAvailable() {
        return maxRightsAvailable;
    }

    public Business setMaxRightsAvailable(int maxRightsAvailable) {
        this.maxRightsAvailable = maxRightsAvailable;
        return this;
    }
}

