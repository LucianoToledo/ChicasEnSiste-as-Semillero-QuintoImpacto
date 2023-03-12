package com.chicasensistemas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VIDEO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VideoEntity {
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        @Column(name = "video_id",nullable = false, unique = true)
        private String id;

        @Column(name = "video_url",nullable = false, unique = true)
        private String url;

        @Column(name = "video_name")
        private String name;

        @Column(name = "video_duration")
        private String duration;

        @Column(name= "video_on_module", nullable = false)
        private Boolean connected =false;

        @CreationTimestamp
        @Column(name = "start_date_video")
        private LocalDateTime startDate;

        @Column(name = "soft_delete", nullable = false)
        private Boolean softDelete = false;

        public  boolean isEnabled(){ return !softDelete;}

        @Override
        public String toString() {
                return "VideoEntity{" +
                        "id='" + id + '\'' +
                        ", url='" + url + '\'' +
                        ", name='" + name + '\'' +
                        ", duration='" + duration + '\'' +
                        ", connected=" + connected +
                        ", startDate=" + startDate +
                        ", softDelete=" + softDelete +
                        '}';
        }
}
