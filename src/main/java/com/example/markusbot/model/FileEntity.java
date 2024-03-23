package com.example.markusbot.model;

import com.example.markusbot.enumBot.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
@Table(name = "files", schema = "public")
public class FileEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name")
    private String fileName;
}
