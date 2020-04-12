package bilshort.user.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "business_id")
    private Integer id;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "apiKey")
    private String apiKey;

    @Column(name = "total_right_used", columnDefinition = "int default 0")
    private int totalRightsUsed;

    @Column(name = "max_rights_usable", columnDefinition = "int default 10")
    private int maxRightsUsable;

    public Integer getId() {
        return id;
    }

    public Business setId(Integer id) {
        this.id = id;
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

    public int getMaxRightsUsable() {
        return maxRightsUsable;
    }

    public Business setMaxRightsUsable(int maxRightsUsable) {
        this.maxRightsUsable = maxRightsUsable;
        return this;
    }
}

