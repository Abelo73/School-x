package com.act.school_xx.dto;

import com.act.school_xx.models.Mark;
import jakarta.persistence.*;

@Entity
@Table(name = "mark_detail")
public class MarkDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mark_id")
    private Mark mark;

    private Integer markValue;


}
