package au.com.auspost.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"postcode", "suburb"})
})
public class Postage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min=4, max=4)
    private String postcode;
    @NotNull
    @Size(max=100)
    private String suburb;


    public Postage() {
    }

    public Postage(String postcode, String suburb) {
        this.postcode = postcode;
        this.suburb = suburb;
    }

    public Long getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postcode, suburb);
    }

    @Override
    public boolean equals(Object object){
        if (object == this) {
            return true;
        }
        if (object instanceof Postage) {
            Postage other = (Postage) object;
            return Objects.equals(postcode, other.getPostcode()) && Objects.equals(suburb, other.getSuburb());
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Postcode", postcode)
                .add("Suburb", suburb)
                .toString();
    }
}
