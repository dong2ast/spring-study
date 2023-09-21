package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String userName;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

//    @OneToOne
//    @JoinColumn(name = "LOCKER_ID")
//    private Locker locker;
//

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID")
    )
    @Column(name = "FOOD_NAME")
    //값타입 컬렉션은 음식 멀티 선책처럼 정말 간단한 것 할 때 사용 (유지보수 필요없는)
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID")
//    )
//    private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID") //Address Entity에서는 멤버 객체를 가지지 않기에 테이블이 매핑할 fk를 여기서 지정 (단방향)
    //그래서 업데이트 쿼리가 나가게 됨 (Adress Entity 테이블 생성 후 fk 추가 등록)
    private List<AddressEntity> addressHistory = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩을 걸어서 연관관계에 있는 객체를 실제 사용할 때 지정함
    @JoinColumn(name = "TEAM_ID")//fk 값 가지고있는 column (연결된 테이블의 fk를 본인 테이블에 저장)
    private Team team; // 연관관계의 주인 (양방향에서 다수쪽이 주인이 됨)

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}

