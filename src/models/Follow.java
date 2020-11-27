package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
        @NamedQuery(
                name = "follow",
                query = "SELECT f FROM Follow AS f WHERE f.followingEmployee = :followingEmployee "
                ),
        @NamedQuery(
                name = "followSearch",
                query = "SELECT f FROM Follow AS f WHERE f.followedEmployee = :followedEmployee AND  f.followingEmployee = :followingEmployee"
                ),
        @NamedQuery(
                name = "followCount",
                query = "SELECT COUNT(f) FROM Follow AS f WHERE f.followingEmployee =:followingEmployee"
                ),
})
@Entity
public class Follow {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name ="followedEmployee_id", nullable = false)
     private Employee followedEmployee;
    @ManyToOne
    @JoinColumn(name ="followingEmployee_id", nullable = false)
    private Employee followingEmployee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollowedEmployee() {
        return followedEmployee;
    }

    public void setFollowedEmployee(Employee followedEmployee) {
        this.followedEmployee = followedEmployee;
    }

    public Employee getFollowingemployee() {
        return followingEmployee;
    }

    public void setFollowingemployee(Employee followingemployee) {
        this.followingEmployee = followingemployee;
    }

}
