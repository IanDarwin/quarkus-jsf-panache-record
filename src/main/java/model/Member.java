package model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;

/**
 * Represents one person in the club's database.
 * CRITICAL: Must extend PanacheEntityBase so the Panache processor automatically 
 * generates the implementation for PersonRepository.
 */
@Entity
public class Member extends PanacheEntity implements Serializable {
    
    public static Member findByLastName(String lastName){
        return Member.find("lastName", lastName).firstResult();
    }

    public static List<Member> listMembers() {
        Sort byLast = Sort.by("lastName");
        return Member.listAll(byLast);
    }
    
    // Panache idiomatic style uses public fields, but still requires JPA annotations.
    // Since we are using PanacheEntityBase, we don't define the @Id field explicitly.
    @Column(name = "firstname")
    public String firstName;

    @Column(name = "lastname")
    public String lastName;

    @Column(name = "postcode")
    public String postcode;

    @Column(name = "address")
    public String address;

    @Column(name = "address2")
    public String address2;

    @Column(name = "city")
    public String city;

    @Column(name = "province")
    public String province;

    @Column(name = "country")
    public String country;

    @Column(name = "email")
    public String email;

    // CONSTRUCTORS

    public Member() {
        // empty, needed for JPA
    }

    // COMMON METHODS
    
    // Panache entity methods (like findById, persist, etc.) are available statically on this class.

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('#').append(id).append(' ');
        sb.append(firstName)
                .append(' ')
                .append(lastName);
        sb.append(' ')
                .append('<')
                .append(email)
                .append('>');
        return sb.toString();
    }

    // Getters and setters remain for JSF compatibility, though fields are public.
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
}
