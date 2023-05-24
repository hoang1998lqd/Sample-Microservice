package com.regalite.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author FISES-HoangVH15
 * @author FISES-HoangVH15
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 65981149772133526L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String accountName;
    @Column(length = 100)
    private String displayName;
    @Column(unique = true, length = 10)
    private String phoneNumber;
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    private String password;
    @Column(length = 2000)
    private String avatar;
    private String provider;
    @Column(nullable = false)
    // Mặc định khi vừa tạo tài khoản cần verify trong Email để xác thực: Chưa xác thực = 0; Đã xác thực = 1;
    private Byte emailVerified = 0;
    // Mặc định có giá trị là 1; xóa là 0
    private Byte validFlg = 1;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Date birthDay;
    private Byte gender; // Nam: 1; Nữ : 0
    private String bio;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return Objects.equals(getId(), user.getId())
                && Objects.equals(getAccountName(), user.getAccountName())
                && Objects.equals(getDisplayName(), user.getDisplayName())
                && Objects.equals(getPhoneNumber(), user.getPhoneNumber())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getAvatar(), user.getAvatar())
                && Objects.equals(getProvider(), user.getProvider())
                && Objects.equals(getEmailVerified(), user.getEmailVerified())
                && Objects.equals(getValidFlg(), user.getValidFlg())
                && Objects.equals(getCreatedAt(), user.getCreatedAt())
                && Objects.equals(getUpdatedAt(), user.getUpdatedAt())
                && Objects.equals(getUpdatedBy(), user.getUpdatedBy())
                && Objects.equals(getBirthDay(), user.getBirthDay())
                && Objects.equals(getGender(), user.getGender())
                && Objects.equals(getBio(), user.getBio())
                && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountName(), getDisplayName(), getPhoneNumber(), getEmail(), getPassword()
                , getAvatar(), getProvider(), getEmailVerified(), getValidFlg(), getCreatedAt(), getUpdatedAt()
                , getUpdatedBy(), getBirthDay(), getGender(), getBio(), getRoles());
    }

}
