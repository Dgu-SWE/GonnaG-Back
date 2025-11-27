package swe.gonnag.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Transcript")
public class TranscriptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tid")
    private UUID id; // 수강기록 고유 번호 (PK)

    // N:1 관계 - User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false) // 실제 DB 컬럼명
    private UserEntity user;

    // N:1 관계 - Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_cid", nullable = false) // 실제 DB 컬럼명
    private ClassEntity classInfo;

    // 빌더
    @lombok.Builder
    public TranscriptEntity(UserEntity user, ClassEntity classInfo) {
        this.user = user;
        this.classInfo = classInfo;
    }
}