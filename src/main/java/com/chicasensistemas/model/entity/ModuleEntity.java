package com.chicasensistemas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MODULE")
@Entity
public class ModuleEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "module_name")
    private String name;

    @Column(name = "module_description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private List<VideoEntity> videosCourse; //should be videos

    @Column(name = "video_on_module", nullable = false)
    private Boolean connected = false;

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;


    @Column(name = "course_id")
    private String courseId;

    public boolean isEnabled() {
        return !softDelete;
    }

    @Override
    public String toString() {
        return "ModuleEntity{" +
                "id='" + moduleId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", connected=" + connected + '\'' +
                ", softDelete=" + softDelete +
                '}';
    }
}


